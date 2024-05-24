package com.example.roombooking.controllers;

import com.example.roombooking.services.implementations.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

        private final UserService userService;

        @GetMapping("/all")
        public String getAllContractCustomers(Model model) {
            model.addAttribute("allUsers", userService.findAllUsers());
            model.addAttribute("pageTitle", "Användare");
            model.addAttribute("header", "Alla användare");
            model.addAttribute("placeholder", "Sök användare...");
            model.addAttribute("username", "Användarnamn");
            model.addAttribute("password", "Lösenord");
            model.addAttribute("enabled", "Aktiverad");
            model.addAttribute("update", "Uppdatera");
            model.addAttribute("delete ", "Ta Bort");
//            model.addAttribute("roles", "Roller");

            return "all-users";
        }

        @GetMapping({"/{username}"})
        public String getContractCustomer(@PathVariable String username, Model model) {
            model.addAttribute("user", userService.findUserByUsername(username));
            model.addAttribute("pageHeader", "Företagskunder");
            model.addAttribute("header", "Företagskund");
            model.addAttribute("companyName", "Företag");
            model.addAttribute("contactName", "Namn");
            model.addAttribute("contactTitle", "Titel");
            model.addAttribute("streetAddress", "Adress");
            model.addAttribute("city", "Stad");
            model.addAttribute("postalCode", "Postnummer");
            model.addAttribute("country", "Land");
            model.addAttribute("phone", "Mobilnummer");
            model.addAttribute("fax", "Fax");

            return "show-contract-customer-info";
        }
//
//        @GetMapping("/all/sort")
//        public String sort(Model model,
//                           @RequestParam(defaultValue = "companyName") String sortColumn,
//                           @RequestParam(defaultValue = "ASC") String sortOrder,
//                           @RequestParam String query) {
//
//            model.addAttribute("pageHeader", "Företagskunder");
//            model.addAttribute("header", "Alla företagskunder");
//            model.addAttribute("companyName", "Företag");
//            model.addAttribute("contactName", "Namn");
//            model.addAttribute("country", "Land");
//            model.addAttribute("placeholder", "Sök företag...");
//
//            query = query.trim();
//
//            if (query.isEmpty()) {
//                model.addAttribute("allContractCustomers", contractCustomerService.findAllSorted(sortOrder, sortColumn));
//            } else {
//                model.addAttribute("query", query);
//                model.addAttribute("allContractCustomers", contractCustomerService.findAllByCompanyNameStartingWith(query, sortOrder, sortColumn));
//            }
//
//            return "all-contract-customers";
//        }

}
