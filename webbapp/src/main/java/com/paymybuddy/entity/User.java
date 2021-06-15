package com.paymybuddy.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "password")
    private String password;

    @Column(name = "balance")
    private int balance;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address_prefix")
    private String addressPrefix;

    @Column(name = "address_number")
    private String addressNumber;

    @Column(name = "address_street")
    private String addressStreet;

    @Column(name = "zip")
    private String zip;

    @Column(name = "city")
    private String city;


}
