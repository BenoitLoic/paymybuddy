package com.paymybuddy.webbapp.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class GetUserAccountDto {

  private int id;

  private String email;

  private String lastName;

  private String firstName;

  private BigDecimal balance;

  private String phone;

  private String addressPrefix;

  private String addressNumber;

  private String addressStreet;

  private String zip;

  private String city;

  private LocalDateTime lastUpdate;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance.setScale(2, RoundingMode.HALF_DOWN);
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddressPrefix() {
    return addressPrefix;
  }

  public void setAddressPrefix(String addressPrefix) {
    this.addressPrefix = addressPrefix;
  }

  public String getAddressNumber() {
    return addressNumber;
  }

  public void setAddressNumber(String addressNumber) {
    this.addressNumber = addressNumber;
  }

  public String getAddressStreet() {
    return addressStreet;
  }

  public void setAddressStreet(String addressStreet) {
    this.addressStreet = addressStreet;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public LocalDateTime getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(LocalDateTime lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  @Override
  public String toString() {
    return "GetUserAccountDto{"
        + "id="
        + id
        + ", email='"
        + email
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", firstName='"
        + firstName
        + '\''
        + ", balance="
        + balance
        + ", phone='"
        + phone
        + '\''
        + ", addressPrefix='"
        + addressPrefix
        + '\''
        + ", addressNumber='"
        + addressNumber
        + '\''
        + ", addressStreet='"
        + addressStreet
        + '\''
        + ", zip='"
        + zip
        + '\''
        + ", city='"
        + city
        + '\''
        + ", lastUpdate="
        + lastUpdate
        + '}';
  }
}
