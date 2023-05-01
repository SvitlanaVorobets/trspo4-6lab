package com.example.lab3.contollers;

import com.example.lab3.dto.NewCreditCardDto;
import com.example.lab3.models.Account;
import com.example.lab3.models.CreditCard;
import com.example.lab3.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("")
    public ResponseEntity<Iterable<Account>> findAll() {
        Iterable<Account> accounts = accountService.findAll();
        if (Objects.equals(accountService.findAll().toString(), "")) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> findById(@PathVariable("id") long id) {
        Optional<Account> account = accountService.findById(id);
        return account.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/credit-card/{id}")
    public ResponseEntity<Account> findByCreditCardId(@PathVariable("id") long id) {
        Account account = accountService.findByCardId(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("/block/{id}")
    public ResponseEntity<Account> blockById(@PathVariable("id") long id) {
        DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Date date = new Date();

        Optional<Account> account = accountService.findById(id);
        account.get().setBlocked(true);
        account.get().setLastTopUpDate(dateFormat.format(date));
        return new ResponseEntity<>(accountService.save(account.get(), account.get().getCreditCard().getCardNumber()), HttpStatus.CREATED);
    }

    @GetMapping("/admin/unblock/{id}")
    public ResponseEntity<Account> unblockById(@PathVariable("id") long id) {
        DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Date date = new Date();

        Optional<Account> account = accountService.findById(id);
        account.get().setBlocked(false);
        account.get().setLastTopUpDate(dateFormat.format(date));
        return new ResponseEntity<>(accountService.save(account.get(), account.get().getCreditCard().getCardNumber()), HttpStatus.CREATED);
    }

    @PutMapping("/add/{id}")
    public ResponseEntity<Account> addMoney(@PathVariable("id") long id, @RequestBody String money) {
        DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Date date = new Date();

        Optional<Account> foundAccount = accountService.findById(id);

        if (foundAccount.isPresent() && !foundAccount.get().isBlocked()) {
            foundAccount.get().setLastTopUpDate(dateFormat.format(date));
            foundAccount.get().setBalance(foundAccount.get().getBalance() + Double.parseDouble(money));
            return new ResponseEntity<>(accountService.save(foundAccount.get(), foundAccount.get().getCreditCard().getCardNumber()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable("id") long id, @RequestBody Account newAccount) {
        Optional<Account> foundAccount = accountService.findById(id);

        if (foundAccount.isPresent()) {
            Account account = foundAccount.get();
            account.setBalance(newAccount.getBalance());
            account.setLastTopUpDate(newAccount.getLastTopUpDate());
            return new ResponseEntity<>(accountService.update(account), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
