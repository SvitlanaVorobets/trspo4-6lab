package com.example.lab3.service;

import com.example.lab3.client.CustomerClient;
import com.example.lab3.dao.CreditCardDao;
import com.example.lab3.dto.CustomerDto;
import com.example.lab3.dto.NewCreditCardDto;
import com.example.lab3.models.CreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class CreditCardService {
    @Autowired
    private CreditCardDao creditCardDao;

    @Autowired
    CustomerClient customerClient;

    @Cacheable(value="CreditCard", key="#root.method.name")
    public Iterable<CreditCard> findAll() {
        return creditCardDao.findAll();
    }

    @Cacheable(value="CreditCard", key="#id")
    public Optional<CreditCard> findById(long id) {
        return creditCardDao.findById(id);
    }

    @CachePut(value="CreditCard", key="#card")
    public CreditCard save(NewCreditCardDto newCreditCard, String card) {
        CreditCard updatedCreditCard = createCreditCard(newCreditCard, customerClient.findById(newCreditCard.getCustomer_id()));
        return creditCardDao.save(updatedCreditCard);
    }

    public CreditCard update(CreditCard creditCard){
        return creditCardDao.save(creditCard);
    }

    public void deleteById(long id) {
        creditCardDao.deleteById(id);
    }

    private static CreditCard createCreditCard(NewCreditCardDto newCreditCard, CustomerDto customer) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 5);

        return CreditCard.builder()
                .customerId(customer.getId())
                .cardNumber(newCreditCard.getCardNumber())
                .expirationDate(String.valueOf(cal.getTime()))
                .cardholderName(newCreditCard.getCardholderName())
                .billingAddress(newCreditCard.getBillingAddress())
                .paymentMethodType(newCreditCard.getPaymentMethodType())
                .build();
    }
}
