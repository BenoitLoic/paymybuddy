package com.paymybuddy.webbapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
@Disabled
@WebMvcTest
@AutoConfigureMockMvc(addFilters = false) // desactivation de Spring security pour ces test
public class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    private String debtorTest = "John";
    private String creditorTest = "Doe";
    private String descriptionTest = "paiement test";
    private String amountTest = "1";
    private String dateTest = "dataTest";

    @Test
    public void createTransactionValid() throws Exception {

//        GIVEN
        ObjectMapper obm = new ObjectMapper();
        ObjectNode objectNode = obm.createObjectNode();
        objectNode.set("debtor", TextNode.valueOf(debtorTest));
        objectNode.set("creditor", TextNode.valueOf(creditorTest));
        objectNode.set("description", TextNode.valueOf(descriptionTest));
        objectNode.set("amount", TextNode.valueOf(amountTest));
        objectNode.set("date", TextNode.valueOf(dateTest));

//        WHEN

//        THEN
        mockMvc.perform(
                MockMvcRequestBuilders.post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    public void createTransactionInvalid() {
    }

    public void deleteTransactionValid() {
    }

    public void updateTransactionValid() {
    }

}
