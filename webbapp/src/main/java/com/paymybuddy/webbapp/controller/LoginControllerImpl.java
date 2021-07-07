package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class LoginControllerImpl implements LoginController {


    @Autowired
    private UserService userService;
    //TODO : à retirer
//    @Autowired
//    TransferService transferService;
//    @Autowired
//    TransferRepository transferRepository;

    /**
     * This method will return the welcome page of the app.
     * This page doesn't need authentication.
     *
     * @return the html page
     */
    @Override
    @GetMapping("/")
    public String welcomePage() {
        return "welcome-page";
    }

    /**
     * This method will return the login page of the app where user can sign-up/sign-in.
     *
     * @return the html page.
     */
    @Override
    @GetMapping("/showLoginPage")
    public String showLoginPage() {
        return "plain-login";
    }

    //TODO : à retirer avant 1.0
//    @GetMapping("/test")
//    @ResponseBody
//    public void testUserAccount() {
//
//        Optional<User> id = userService.findById(27);
//        User user = id.get();
//
//        Set<Transfer> transfers1 = user.getTransfersAsDebtor();
//        System.out.println(transfers1.size());
//        for (Transfer transfer : transfers1) {
//            System.out.println(transfer.getCreditor());
//        }
//        Set<Transfer> transfers = user.getTransfersAsCreditor();
//        System.out.println(transfers.size());
//        for (Transfer transfer : transfers) {
//            System.out.println(transfer.getDebtor());
//        }
//        System.out.println(transferService.getTransfers("loic@mail.com"));
//        int userId = userService.findByEmail("loic@mail.com").get().getId();
//        System.out.println(userId);
//        System.out.println(transferRepository.findAllByDebtorId(userId).size() + " ------------------- " + transferRepository.findAllByDebtorId(userId));
//        System.out.println(transferRepository.findAllByCreditorId(userId));
//
//    }
}
