package com.example.lab3.service;

import com.example.lab3.dao.PaymentDao;
import com.example.lab3.models.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentDao paymentDao;

    @Cacheable(value="Payment", key="#root.method.name")
    public Iterable<Payment> findAll() {
        return paymentDao.findAll();
    }

    @Cacheable(value="Payment", key="#id")
    public Optional<Payment> findById(long id) {
        return paymentDao.findById(id);
    }

    @CacheEvict(value ="Payment", allEntries = true)
    @CachePut(value="Payment", key="#card")
    public Payment save(Payment payment, String card) {
        return paymentDao.save(payment);
    }
}
