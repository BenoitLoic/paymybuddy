package com.paymybuddy.webbapp.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_debtor_id")
    private String debtor;

    @Column(name = "user_creditor_id")
    private String creditor;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private int amount;

    @Column(name = "date")
    private Timestamp date;

}
