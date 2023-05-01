package com.example.lab3.contollers;

import com.example.lab3.dao.CustomerDao;
import com.example.lab3.models.Customer;
import com.example.lab3.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("")
    public ResponseEntity<Iterable<Customer>> findAll() {
        Iterable<Customer> customers = customerService.findAll();
        if (Objects.equals(customerService.findAll().toString(), "")) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findById(@PathVariable("id") long id) {
        Optional<Customer> customer = customerService.findById(id);
        return customer.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer newCustomer){
        Customer customer = new Customer();
        customer.setCustomerName(newCustomer.getCustomerName());
        customer.setEmail(newCustomer.getEmail());
        customer.setPhone(newCustomer.getPhone());
        customer.setAddress(newCustomer.getAddress());
        return new ResponseEntity<>(customerService.save(customer, customer.getCustomerName()), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long id, @RequestBody Customer bodyCustomer) {
        Optional<Customer> publisher = customerService.findById(id);

        if (publisher.isPresent()) {
            Customer newCustomer = publisher.get();
            newCustomer.setCustomerName(bodyCustomer.getCustomerName());
            newCustomer.setEmail(bodyCustomer.getEmail());
            newCustomer.setPhone(bodyCustomer.getPhone());
            newCustomer.setAddress(bodyCustomer.getAddress());
            return new ResponseEntity<>(customerService.save(newCustomer , newCustomer.getCustomerName()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") long id) {
        try {
            customerService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
