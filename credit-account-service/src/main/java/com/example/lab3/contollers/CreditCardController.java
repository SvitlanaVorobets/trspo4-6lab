package com.example.lab3.contollers;

import com.example.lab3.dto.NewCreditCardDto;
import com.example.lab3.models.Account;
import com.example.lab3.models.CreditCard;
import com.example.lab3.service.AccountService;
import com.example.lab3.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/credit-card")
public class CreditCardController {
    @Autowired
    CreditCardService creditCardService;

    @Autowired
    AccountService accountService;

    @GetMapping("")
    public ResponseEntity<Iterable<CreditCard>> findAll() {
        Iterable<CreditCard> creditCard = creditCardService.findAll();
        if (Objects.equals(creditCardService.findAll().toString(), "")) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        System.out.println(creditCard);
        return new ResponseEntity<>(creditCard, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditCard> findById(@PathVariable("id") long id) {
        Optional<CreditCard> creditCard = creditCardService.findById(id);
        return creditCard.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<CreditCard> createCreditCard(@RequestBody NewCreditCardDto newCreditCard){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 5);

        System.out.println(newCreditCard);
        CreditCard created = creditCardService.save(newCreditCard, newCreditCard.getCardNumber());

        DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Date date = new Date();
        Account account = new Account();
        account.setBalance(0.0);
        account.setBlocked(false);
        account.setLastTopUpDate(dateFormat.format(date));
        account.setCreditCard(created);
        Account createdAccount = accountService.save(account, account.getCreditCard().getCardNumber());

        Optional<CreditCard> updatedCard = creditCardService.findById(created.getId());
        updatedCard.get().setAccount(createdAccount);
        System.out.println(updatedCard);
        return new ResponseEntity<>(updatedCard.get(), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CreditCard> updateCreditCard(@PathVariable("id") long id, @RequestBody NewCreditCardDto newCreditCard) {
        Optional<CreditCard> foundCreditCard = creditCardService.findById(id);

        if (foundCreditCard.isPresent()) {
            CreditCard creditCard = foundCreditCard.get();
            creditCard.setCardholderName(newCreditCard.getCardholderName());
            creditCard.setBillingAddress(newCreditCard.getBillingAddress());
            creditCard.setPaymentMethodType(newCreditCard.getPaymentMethodType());
            return new ResponseEntity<>(creditCardService.update(creditCard), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCreditCard(@PathVariable("id") long id) {
        try {
            creditCardService.deleteById(id);
            accountService.delete(accountService.findByCardId(id));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
