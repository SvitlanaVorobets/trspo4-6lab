package com.example.lab3.models;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisHash;


@Entity
@Table(name = "customer")
public class Customer implements Serializable {
    private static final long serialVersionUID = -4439114469417994311L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;


    public Customer(Long id, String name, String email, String phone, String address) {
        this.id = id;
        this.customerName = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Customer() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + customerName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
