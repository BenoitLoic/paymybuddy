package com.paymybuddy.webbapp.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class NewTransferDto {

    @NotBlank
    private String creditorEmail;

    private String debtorEmail;

    @Min(50)
    private int amount;

    private String description;

    public NewTransferDto(String creditorEmail, int amount, String description) {
        this.creditorEmail = creditorEmail;
        this.amount = amount;
        this.description = description;
    }

    public NewTransferDto() {
    }

    public String getCreditorEmail() {
        return creditorEmail;
    }

    public void setCreditorEmail(String creditorEmail) {
        this.creditorEmail = creditorEmail;
    }

    public String getDebtorEmail() {
        return debtorEmail;
    }

    public void setDebtorEmail(String debtorEmail) {
        this.debtorEmail = debtorEmail;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "NewTransferDto{" +
                "creditorEmail='" + creditorEmail + '\'' +
                ", debtorEmail='" + debtorEmail + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
