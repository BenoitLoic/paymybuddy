package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.dto.GetTransferDto;
import com.paymybuddy.webbapp.dto.NewTransferDto;
import com.paymybuddy.webbapp.exception.UserNotAuthenticatedException;
import com.paymybuddy.webbapp.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/home")
public class TransferControllerImpl implements TransferController {

    @Autowired
    TransferService transferService;

    private final String loginPageUrl = "/showLoginPage";
    private final String userHomePageUrl = "/home";

    /**
     * this method will add cash to the current user balance.
     *
     * @param amount    of money to add
     * @param principal the current user
     * @return should redirect to user's home page
     */
    @Override
    @PostMapping("/balance/add")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RedirectView addCash(@RequestParam int amount, Principal principal) {


        // check if there is an user connected
        if (principal.getName() == null) {
            return new RedirectView(loginPageUrl);
        }
        String userEmail = principal.getName();


        // call service
        transferService.addCash(amount, userEmail);
        // return view
        return new RedirectView(userHomePageUrl);
    }

    /**
     * this method will remove cash from the current user balance.
     *
     * @param amount    of money to remove
     * @param principal the current user
     * @return should redirect to user's home page
     */
    @Override
    @PostMapping("/balance/remove")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RedirectView removeCash(@RequestParam int amount, Principal principal) {


        // check if there is an user connected
        if (principal.getName() == null) {
            return new RedirectView(loginPageUrl);
        }
        String userEmail = principal.getName();


        // call service
        transferService.removeCash(amount, userEmail);
        // return view
        return new RedirectView(userHomePageUrl);
    }

    /**
     * This method will create a new transfer for the current user and the given contact.
     *
     * @param newTransfer Dto with creditorEmail, amount, description
     * @param principal   the current user (debtor)
     * @return previous page
     */
    @Override
    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public RedirectView createTransfer(@Valid @RequestBody NewTransferDto newTransfer, Principal principal) {

//        if (bindingResult.hasErrors()){
//            throw new BadArgumentException("KO - error detected within form");
//        }

        // check principal
        if (principal.getName() == null) {
            throw new UserNotAuthenticatedException("KO - User must be authenticated");
        }
        // add principal to dto
        newTransfer.setDebtorEmail(principal.getName());
        // call service
        transferService.createTransfer(newTransfer);
        // redirect to getTransfer page

        return new RedirectView("/home/transfer");
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
        return "transfer-page";
    }

}
