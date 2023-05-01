package com.example.lab3.dao;

import com.example.lab3.models.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardDao extends CrudRepository<CreditCard, Long> {
}
