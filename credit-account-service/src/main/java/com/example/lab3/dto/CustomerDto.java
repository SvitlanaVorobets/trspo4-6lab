package com.example.lab3.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDto {

    private Long id;

    private String customerName;

    private String email;

    private String phone;

    private String address;

    @Override
    public String toString() {
        return "CustomerDto{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}