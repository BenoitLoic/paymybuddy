package com.paymybuddy.webbapp.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
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


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "contact",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id"))
    private final Set<User> contacts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "contact",
            joinColumns = @JoinColumn(name = "contact_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private final Set<User> asContacts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "transaction",
    joinColumns = @JoinColumn(name = "user_creditor_id"),
    inverseJoinColumns =  @JoinColumn(name = "user_debtor_id"))
    private final Set<Transfer> transfersAsCreditor = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "transaction",
            joinColumns = @JoinColumn(name = "user_debtor_id"),
            inverseJoinColumns =  @JoinColumn(name = "user_creditor_id"))
    private final Set<Transfer> transfersAsDebtor = new HashSet<>();

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "transaction",
//            joinColumns = @JoinColumn(name = "user_debtor_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_creditor_id"))
//    private final Set<Transfer> transfersAsDebtor = new HashSet<>();
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "transaction",
//            joinColumns = @JoinColumn(name = "user_creditor_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_debtor_id"))
//    private final Set<Transfer> transfersAsCreditor = new HashSet<>();



    public User() {
    }

    public User(String email, String lastName, String firstName, String password) {
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.password = password;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
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

    public Set<User> getContacts() {
        return contacts;
    }

    public Set<User> getAsContacts() {
        return asContacts;
    }

    public Set<Transfer> getTransfersAsCreditor() {
        return transfersAsCreditor;
    }

    public Set<Transfer> getTransfersAsDebtor() {
        return transfersAsDebtor;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", phone='" + phone + '\'' +
                ", addressPrefix='" + addressPrefix + '\'' +
                ", addressNumber='" + addressNumber + '\'' +
                ", addressStreet='" + addressStreet + '\'' +
                ", zip='" + zip + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
