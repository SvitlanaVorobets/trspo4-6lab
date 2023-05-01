package com.example.lab3.contollers;

import com.example.lab3.client.AccountClient;
import com.example.lab3.client.CreditCardClient;
import com.example.lab3.dto.NewPaymentDto;
import com.example.lab3.models.Account;
import com.example.lab3.models.CreditCard;
import com.example.lab3.models.Payment;
import com.example.lab3.service.PaymentService;
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
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @Autowired
    CreditCardClient creditCardClient;

    @Autowired
    AccountClient accountClient;

    @GetMapping("")
    public ResponseEntity<Iterable<Payment>> findAll() {
        Iterable<Payment> payments = paymentService.findAll();
        if (Objects.equals(paymentService.findAll().toString(), "")) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> findById(@PathVariable("id") long id) {
        Optional<Payment> payment = paymentService.findById(id);
        return payment.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Payment> createPayment(@RequestBody NewPaymentDto newPayment){
        Payment payment = new Payment();

        DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Date date = new Date();
        System.out.println(newPayment);
        CreditCard creditCard = creditCardClient.findById(newPayment.getCredit_card_id());
        Account account = accountClient.findByCardId(newPayment.getCredit_card_id());

        if(!account.isBlocked()){
            payment.setAmount(newPayment.getAmount());
            payment.setType(newPayment.getType());
            payment.setCreditCard(creditCard);
            payment.setDate(dateFormat.format(date));

            Double currentAmount = account.getBalance();
            if(currentAmount > newPayment.getAmount()){
                account.setBalance(currentAmount - newPayment.getAmount());
                account.setLastTopUpDate(dateFormat.format(date));
                accountClient.update(account, account.getId());
                payment.setStatus("Success");
            } else payment.setStatus("Failure");

        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(paymentService.save(payment, payment.getDate()), HttpStatus.CREATED);
    }
}
