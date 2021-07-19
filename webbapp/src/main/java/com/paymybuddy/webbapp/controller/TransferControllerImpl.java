package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.dto.ContactDto;
import com.paymybuddy.webbapp.dto.GetTransferDto;
import com.paymybuddy.webbapp.dto.NewTransferDto;
import com.paymybuddy.webbapp.exception.UserNotAuthenticatedException;
import com.paymybuddy.webbapp.service.TransferService;
import com.paymybuddy.webbapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/home")
public class TransferControllerImpl implements TransferController {

  @Autowired TransferService transferService;
  @Autowired UserService userService;

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

    // check if there is an user connected
    if (principal == null || principal.getName() == null) {
      throw new UserNotAuthenticatedException("KO - User must be authenticated");
    }
    String userEmail = principal.getName();

    // call service
    transferService.addCash(amount, userEmail);
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

    // check if there is an user connected
    if (principal == null || principal.getName() == null) {
      throw new UserNotAuthenticatedException("KO - User must be authenticated");
    }
    String userEmail = principal.getName();

    // call service
    transferService.removeCash(amount, userEmail);
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

    // check principal
    if (principal == null || principal.getName() == null) {
      throw new UserNotAuthenticatedException("KO - User must be authenticated");
    }
    // add principal to dto
    newTransfer.setDebtorEmail(principal.getName());
    // call service
    transferService.createTransfer(newTransfer);
    // redirect to getTransfer page

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


    List<GetTransferDto> transfers = transferService.getTransfers(principal.getName());
    model.addAttribute("transfers", transfers);
    Collection<ContactDto> contacts = userService.getAllContact(principal.getName());
    model.addAttribute("contacts", contacts);

    return "transfer-home";
  }
}
