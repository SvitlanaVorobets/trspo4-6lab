package com.example.lab3.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "credit_card")
public class CreditCard implements Serializable {
    private static final long serialVersionUID = -4439114469417994311L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    @OneToOne(mappedBy = "creditCard", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Account account;

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "creditCard", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Payment> payments = new ArrayList<>();

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "expiration_date")
    private String expirationDate;

    @Column(name = "cardholder_name")
    private String cardholderName;

    @Column(name = "billing_address")
    private String billingAddress;

    @Column(name = "payment_method_type")
    private String paymentMethodType;

    public CreditCard() {
    }

    public CreditCard(Long id, Long customerId, Account account, List<Payment> payments, String cardNumber, String expirationDate, String cardholderName, String billingAddress, String paymentMethodType) {
        this.id = id;
        this.customerId = customerId;
        this.account = account;
        this.payments = payments;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cardholderName = cardholderName;
        this.billingAddress = billingAddress;
        this.paymentMethodType = paymentMethodType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomer() {
        return customerId;
    }

    public void setCustomer(Long customerId) {
        this.customerId = customerId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(String paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", customer=" + customerId +
                ", account=" + account +
                ", cardNumber='" + cardNumber + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", cardholderName='" + cardholderName + '\'' +
                ", billingAddress='" + billingAddress + '\'' +
                ", paymentMethodType='" + paymentMethodType + '\'' +
                '}';
    }
}
