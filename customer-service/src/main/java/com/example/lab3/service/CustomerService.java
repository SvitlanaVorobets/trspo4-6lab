package com.example.lab3.service;

import com.example.lab3.dao.CustomerDao;
import com.example.lab3.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Cacheable(value="Customer", key="#root.method.name")
    public Iterable<Customer> findAll() {
        return customerDao.findAll();
    }

    @Cacheable(value="Customer", key="#id")
    public Optional<Customer> findById(long id) {
        return customerDao.findById(id);
    }

    @CachePut(value="Customer", key="#p0")
    public Customer save(Customer customer, String name) {
        return customerDao.save(customer);
    }


    public void deleteById(long id) {
        customerDao.deleteById(id);
    }
}
