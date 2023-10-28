package com.picpaysimple.picpaysimple.services;

import com.picpaysimple.picpaysimple.domain.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public class AuthorizationService {

    private RestTemplate restTemplate;

    public boolean authorizeTransaction(User sender, BigDecimal value) {

        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", Map.class);

        if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
            String message = (String) Objects.requireNonNull(authorizationResponse.getBody()).get("message");
            return "Autorizado".equalsIgnoreCase(message);
        } else return false;
    }
}
