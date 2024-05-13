package ru.kustikov.cakes.consumables;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class ConsumableService {
    @Autowired
    private RestTemplate restTemplate;
    private final String URL = "http://localhost:4300/api/v1/consumable";

    public ResponseEntity<List<Consumable>> get(String username) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("username", username);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Consumable>> response = restTemplate.exchange(
                URL + "/get?username={username}",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                },
                username
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            List<Consumable> consumables = response.getBody();
            return ResponseEntity.ok(consumables);
        } else {
            log.error("Запрос завершился неудачно. Код ответа: " + response.getStatusCodeValue());
            return ResponseEntity.status(response.getStatusCode()).build();
        }
    }

    public ResponseEntity<Consumable> update(Consumable consumable) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Consumable> requestEntity = new HttpEntity<>(consumable, headers);

        ResponseEntity<Consumable> response = restTemplate.exchange(
                URL + "/save",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                },
                consumable
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        } else {
            log.error("Failed to save consumable. Response code: " + response.getStatusCodeValue());
            return ResponseEntity.status(response.getStatusCode()).build();
        }
    }

    public ResponseEntity<String> delete(Long consumableId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                URL + "/delete/{consumableId}",
                HttpMethod.DELETE,
                requestEntity,
                Void.class,
                consumableId
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok("Consumable deleted successfully!");
        } else {
            log.error("Failed to delete consumable. Response code: " + response.getStatusCodeValue());
            return ResponseEntity.status(response.getStatusCode()).build();
        }
    }
}
