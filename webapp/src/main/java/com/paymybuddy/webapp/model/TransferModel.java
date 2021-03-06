package com.paymybuddy.webapp.model;

import com.paymybuddy.webapp.entity.User;
import java.sql.Timestamp;

/** Model for Transfer entity. */
public class TransferModel {

  private int id;

  private int debtorId;

  private int creditorId;

  private String description;

  private int amount;

  private Timestamp date;

  private User debtor;

  private User creditor;

  public TransferModel() {}

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
    return new Timestamp(date.getTime());
  }

  public void setDate(Timestamp date) {
    this.date = new Timestamp(date.getTime());
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
    return "TransferModel{"
        + "id="
        + id
        + ", debtorId="
        + debtorId
        + ", creditorId="
        + creditorId
        + ", description='"
        + description
        + '\''
        + ", amount="
        + amount
        + ", date="
        + date
        + '}';
  }
}
