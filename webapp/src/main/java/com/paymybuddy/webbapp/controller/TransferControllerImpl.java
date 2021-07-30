package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.dto.ContactDto;
import com.paymybuddy.webbapp.dto.GetTransferDto;
import com.paymybuddy.webbapp.dto.NewTransferDto;
import com.paymybuddy.webbapp.service.TransferService;
import com.paymybuddy.webbapp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

/**
 * Implementation for TransferController.
 *
 * <p>Contains method to add/remove currency from user balance. Contains method to create a new
 * transaction and get all transfer for the authenticated user.
 */
@Controller
@RequestMapping("/home")
public class TransferControllerImpl implements TransferController {

  @Autowired TransferService transferService;
  @Autowired UserService userService;
  private final Logger log = LogManager.getLogger(getClass().getName());

  /**
   * this method will add cash to the current user balance.
   *
   * @param amount of money to add
   * @param principal the current user
   * @return success page
   */
  @Override
  @PostMapping(value = "/balance/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @ResponseStatus(HttpStatus.ACCEPTED)
  public String addCash(@RequestParam int amount, Principal principal) {

    String userEmail = principal.getName();

    // call service
    transferService.addCash(amount, userEmail);

    log.info("Successfully added : " + amount + " for user : " + userEmail);

    // return view
    return "success";
  }

  /**
   * this method will remove cash from the current user balance.
   *
   * @param amount of money to remove
   * @param principal the current user
   * @return success page
   */
  @Override
  @PostMapping(value = "/balance/remove", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @ResponseStatus(HttpStatus.ACCEPTED)
  public String removeCash(@RequestParam String amount, Principal principal) {

    String userEmail = principal.getName();

    // call service
    transferService.removeCash(amount, userEmail);
    log.info("Successfully removed : " + amount + " for user : " + userEmail);
    // return view
    return "success";
  }

  /**
   * This method will create a new transfer for the current user and the given contact.
   *
   * @param newTransfer Dto with creditorEmail, amount, description
   * @param principal the current user (debtor)
   * @return success page
   */
  @Override
  @PostMapping(value = "/transfer", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public String createTransfer(
      @Valid @ModelAttribute("newTransfer") NewTransferDto newTransfer, Principal principal) {

    // add principal to dto
    newTransfer.setDebtorEmail(principal.getName());

    log.info(
        "Creating new transfer - amount : "
            + newTransfer.getAmount()
            + " - debtor : "
            + newTransfer.getDebtorEmail()
            + " - creditor : "
            + newTransfer.getCreditorEmail());
    // call service
    transferService.createTransfer(newTransfer);
    // redirect to getTransfer page
    log.info("Transfer accepted");
    return "success";
  }

  /**
   * This method will get all the transfer of the current user.
   *
   * @param principal the current user
   * @return the view for transfer
   */
  @Override
  @GetMapping("/transfer")
  public String getTransfers(Model model, Principal principal) {
    log.info("Getting all transfer for user : " + principal.getName());
    List<GetTransferDto> transfers = transferService.getTransfers(principal.getName());
    model.addAttribute("transfers", transfers);
    Collection<ContactDto> contacts = userService.getAllContact(principal.getName());
    model.addAttribute("contacts", contacts);
    log.trace("user : " + principal.getName() + " have : " + transfers.size() + " transfers.");
    return "transfer-home";
  }
}
