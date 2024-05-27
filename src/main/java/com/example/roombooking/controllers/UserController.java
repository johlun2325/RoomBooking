package com.example.roombooking.controllers;

import com.example.roombooking.dto.UserDTO;
import com.example.roombooking.services.implementations.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.UUID;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public String getAllUsers(Model model) {
        model.addAttribute("allUsers", userService.findAllUsers());
        model.addAttribute("pageTitle", "Användare");
        model.addAttribute("header", "Alla användare");
        model.addAttribute("placeholder", "Sök användare...");
        model.addAttribute("username", "Användarnamn");
        model.addAttribute("update", "Uppdatera");
        model.addAttribute("delete", "Ta Bort");

        return "user/users.html";
    }

    @GetMapping({"/{username}"})
    public String getUser(@PathVariable String username, Model model) {
        model.addAttribute("user", userService.findUserByUsername(username));
        model.addAttribute("pageTitle", "Användare");
        model.addAttribute("header", "Användare");
        model.addAttribute("id", "ID");
        model.addAttribute("username", "Användarnamn");
        model.addAttribute("enabled", "Aktiverad");
        model.addAttribute("yes", "Ja");
        model.addAttribute("no", "Nej");
        model.addAttribute("roles", "Roller");
        model.addAttribute("update", "Uppdatera");
        model.addAttribute("delete", "Ta Bort");

        return "user/user-info.html";
    }

    @GetMapping("/all/sort")
    public String sortAndSearchUsers(Model model,
                       @RequestParam(defaultValue = "username") String sortColumn,
                       @RequestParam(defaultValue = "ASC") String sortOrder,
                       @RequestParam String query) {
        model.addAttribute("allUsers", userService.findAllUsers());
        model.addAttribute("pageTitle", "Användare");
        model.addAttribute("header", "Alla användare");
        model.addAttribute("placeholder", "Sök användare...");
        model.addAttribute("username", "Användarnamn");
        model.addAttribute("password", "Lösenord");
        model.addAttribute("enabled", "Aktiverad");
        model.addAttribute("update", "Uppdatera");
        model.addAttribute("delete", "Ta Bort");

        query = query.trim();
        var users = query.isEmpty()
                ? userService.findAllUsersSorted(sortOrder, sortColumn)
                : userService.findAllUsersSortAndQuery(query, sortOrder, sortColumn);

        model.addAttribute("query", query);
        model.addAttribute("allUsers", users);

        return "user/users.html";
    }

    @GetMapping("/new")
    public String createNewUser(Model model) {
        model.addAttribute("pageTitle", "Användare");
        model.addAttribute("header", "Skapa användare");
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("usernameText", "Ange användarnamn");
        model.addAttribute("passwordText", "Ange lösenord");
        model.addAttribute("rolesText", "Välj en eller flera roller");
        model.addAttribute("allRoles", userService.findAllRolesDto());
        model.addAttribute("submitText", "Skapa");

        return "user/new-user.html";
    }

    @PostMapping( "/add")
    public String addUser(RedirectAttributes redirectAttributes, UserDTO user) {
        String message = userService.addUser(user);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/user/all";
    }

    @PostMapping("/update")
    public String updateUser(RedirectAttributes redirectAttributes, UserDTO user) {
        redirectAttributes.addFlashAttribute("message", userService.updateUser(user));
        return "redirect:/user/all";
    }

    @RequestMapping("/delete/{username}")
    public String deleteUser(RedirectAttributes redirectAttributes, @PathVariable String username) {
        redirectAttributes.addFlashAttribute("message", userService.deleteUser(username));
        return "redirect:/user/all";
    }

    @GetMapping("/updateForm/{id}")
    public String updateByForm(@PathVariable UUID id, Model model) {
        model.addAttribute("userDto", userService.findUserById(id));
        model.addAttribute("pageTitle", "Kund");
        model.addAttribute("header", "Uppdatera kund");
        model.addAttribute("usernameText", "Ändra användarnamn");
        model.addAttribute("enabled", "Aktiverad");
        model.addAttribute("rolesText", "Uppdatera roll");
        model.addAttribute("buttonText", "Uppdatera");
        model.addAttribute("allRoles", userService.findAllRolesDto());

        return "user/update-user.html";
    }
}
