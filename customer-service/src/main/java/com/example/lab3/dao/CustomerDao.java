package com.example.lab3.dao;

import com.example.lab3.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao extends CrudRepository<Customer, Long> {
}
