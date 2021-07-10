package com.paymybuddy.webbapp.dto;

import java.util.Objects;

public class GetTransferDto {

    private String contactName;
    private String description;
    private int amount;

    public GetTransferDto() {
    }

    public GetTransferDto(String contactName, String description, int amount) {
        this.contactName = contactName;
        this.description = description;
        this.amount = amount;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetTransferDto that = (GetTransferDto) o;
        return amount == that.amount && Objects.equals(contactName, that.contactName) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactName, description, amount);
    }

    @Override
    public String toString() {
        return "GetTransferDto{" +
                "contactName='" + contactName + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                '}';
    }
}
