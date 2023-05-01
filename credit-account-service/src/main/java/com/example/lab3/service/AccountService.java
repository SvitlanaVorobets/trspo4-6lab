package com.example.lab3.service;

import com.example.lab3.dao.AccountDao;
import com.example.lab3.models.Account;
import com.example.lab3.models.CreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountDao accountDao;

    @Cacheable(value="Account", key="#root.method.name")
    public Iterable<Account> findAll() {
        return accountDao.findAll();
    }

    @Cacheable(value="Account", key="#id")
    public Optional<Account> findById(long id) {
        return accountDao.findById(id);
    }


    @Cacheable(value="Account", key="#id")
    public Account findByCardId(long id) {
        return accountDao.findByCardId(id);
    }

    @CacheEvict(value ="Account", allEntries = true)
    @CachePut(value="Account", key="#card")
    public Account save(Account account, String card) {
        card += " account";
        return accountDao.save(account);
    }

    public Account update(Account account){
        return accountDao.save(account);
    }

    public void delete(Account account) {
        accountDao.delete(account);
    }
}
