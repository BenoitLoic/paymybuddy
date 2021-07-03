package com.paymybuddy.webbapp.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_debtor_id")
    private int debtorId;

    @Column(name = "user_creditor_id")
    private int creditorId;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private int amount;

    @Column(name = "date")
    private Timestamp date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_debtor_id", updatable = false,insertable = false)
    private User debtor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_creditor_id", updatable = false,insertable = false)
    private User creditor;

    public Transfer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDebtorId() {
        return debtorId;
    }

    public void setDebtorId(int debtorId) {
        this.debtorId = debtorId;
    }

    public int getCreditorId() {
        return creditorId;
    }

    public void setCreditorId(int creditorId) {
        this.creditorId = creditorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Timestamp getDate() {
        return date;
    }
@PrePersist
    public void setDate() {
        date = Timestamp.valueOf(LocalDateTime.now());
    }

    public User getDebtor() {
        return debtor;
    }

    public void setDebtor(User debtor) {
        this.debtor = debtor;
    }

    public User getCreditor() {
        return creditor;
    }

    public void setCreditor(User creditor) {
        this.creditor = creditor;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", debtorId=" + debtorId +
                ", creditorId=" + creditorId +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", debtor=" + debtor +
                ", creditor=" + creditor +
                '}';
    }
}
