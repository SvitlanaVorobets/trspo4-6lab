package com.example.lab3.client;

import com.example.lab3.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomerClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${customer.customer-url}")
    private String customerServiceUrl;

    public CustomerDto findById(Long customerId) {

        ResponseEntity<CustomerDto> response = restTemplate.getForEntity(customerServiceUrl + customerId, CustomerDto.class, customerId);
        if(response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            log.error("Subject with id {} doesn't exist", customerId);
            throw new IllegalStateException("Something went wrong");
        }

        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            log.error("Something went wrong");
            throw new IllegalStateException("Something went wrong");
        }
        System.out.println(response.getBody());
        return response.getBody();
    }
}