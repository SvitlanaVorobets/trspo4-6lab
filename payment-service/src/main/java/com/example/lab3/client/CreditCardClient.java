package com.example.lab3.client;

import com.example.lab3.models.CreditCard;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLOutput;

@Slf4j
@RequiredArgsConstructor
@Component
public class CreditCardClient {
    @Autowired
    private final RestTemplate restTemplate;

    @Value("${credit_card.credit_card-url}")
    private String creditCardServiceUrl;

    public CreditCard findById(Long creditCardId) {

        ResponseEntity<CreditCard> response = restTemplate.getForEntity(creditCardServiceUrl + creditCardId, CreditCard.class, creditCardId);

        System.out.println(response.getBody());
        if(response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            log.error("Subject with id {} doesn't exist", creditCardId);
            throw new IllegalStateException("Something went wrong");
        }

        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            log.error("Something went wrong");
            throw new IllegalStateException("Something went wrong");
        }

        return response.getBody();
    }
}
