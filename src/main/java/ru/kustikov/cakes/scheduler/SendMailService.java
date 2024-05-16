package ru.kustikov.cakes.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SendMailService {

    private final String URL = "http://localhost:4302/api/v1/mail/send-msg";

    public boolean send(String email, String message) {
        Map<String, String> mailData = Map.of(
                "email", email,
                "text", message
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> responseEntity = new RestTemplate()
                .postForEntity(URL, new HttpEntity<>(mailData, headers), String.class);

        return responseEntity.getStatusCode().is2xxSuccessful();
    }
}
