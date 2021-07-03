package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class TransferControllerImpl implements TransferController {

    @Autowired
    TransferService transferService;

    private final String loginPageUrl = "/showLoginPage";
    private final String userHomePageUrl = "/home";

    /**
     * this method will add cash to the current user balance.
     *
     * @param amount of money to add
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
     * @param amount of money to remove
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
}
