package ru.kustikov.cakes.statistics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class StatisticService {
    @Autowired
    private RestTemplate restTemplate;
    private final String URL = "http://localhost:4300/api/v1/statistic";

    public ResponseEntity<List<Statistic>> get(Integer year, Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("userId", String.valueOf(userId));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Statistic>> response = restTemplate.exchange(
                URL + "/get?year={year}&userId={userId}",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                },
                year,
                userId
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            List<Statistic> statistics = response.getBody();
            return ResponseEntity.ok(statistics);
        } else {
            log.error("Запрос завершился неудачно. Код ответа: " + response.getStatusCodeValue());
            return ResponseEntity.status(response.getStatusCode()).build();
        }
    }

}
