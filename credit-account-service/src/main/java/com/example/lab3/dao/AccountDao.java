package com.example.lab3.dao;

import com.example.lab3.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountDao extends CrudRepository<Account, Long> {
    @Query("select t from Account t where t.creditCard.id = :id")
    Account findByCardId(@Param("id") Long id);

}
