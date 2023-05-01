package com.example.lab3.client;

import com.example.lab3.models.Account;
import com.example.lab3.models.CreditCard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Component
public class AccountClient {
    @Autowired
    private final RestTemplate restTemplate;

    @Value("${account.account-url}")
    private String accountServiceUrl;

    public Account findByCardId(Long accountId) {

        ResponseEntity<Account> response = restTemplate.getForEntity(accountServiceUrl + "credit-card/" + accountId, Account.class, accountId);

        if(response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            log.error("Subject with id {} doesn't exist", accountId);
            throw new IllegalStateException("Something went wrong");
        }

        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            log.error("Something went wrong");
            throw new IllegalStateException("Something went wrong");
        }

        return response.getBody();
    }

    public void update(Account account, Long accountId){
        restTemplate.put(accountServiceUrl + "update/" + accountId, account);
    }
}
