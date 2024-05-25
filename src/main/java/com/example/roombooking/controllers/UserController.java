package com.example.roombooking.controllers;

import com.example.roombooking.dto.RoleDTO;
import com.example.roombooking.dto.UserDTO;
import com.example.roombooking.services.implementations.RoleService;
import com.example.roombooking.services.implementations.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/all")
    public String getAllContractCustomers(Model model) {
        model.addAttribute("allUsers", userService.findAllUsers());
        model.addAttribute("pageTitle", "Användare");
        model.addAttribute("header", "Alla användare");
        model.addAttribute("placeholder", "Sök användare...");
        model.addAttribute("username", "Användarnamn");
        model.addAttribute("update", "Uppdatera");
        model.addAttribute("delete", "Ta Bort");

        return "all-users";
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

        return "user-info";
    }

    @GetMapping("/all/sort")
    public String sort(Model model,
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

        if (query.isEmpty()) {
            model.addAttribute("allUsers", userService.findAllUsersSorted(sortOrder, sortColumn));
        } else {
            model.addAttribute("query", query);
            model.addAttribute("allUsers", userService.findAllUsersSortAndQuery(query, sortOrder, sortColumn));
        }

        return "all-users";
    }

    @GetMapping("/new")
    public String openNewCustomerPage(Model model) {
        model.addAttribute("pageTitle", "Användare");
        model.addAttribute("header", "Skapa en ny användare");
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("usernameText", "Ange användarnamn");
        model.addAttribute("passwordText", "Ange lösenord");
        model.addAttribute("rolesText", "Välj en eller flera roller");
        model.addAttribute("allRoles", roleService.findAllRoles());
        model.addAttribute("submitText", "Lägg till");

        return "new-user";
    }

    @PostMapping("/add")
    public String addUser(Model model, @RequestParam MultiValueMap<String, String> formData) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(formData.getFirst("username"));
        userDTO.setPassword(formData.getFirst("password"));
        userDTO.setEnabled(true);


        List<RoleDTO> roles = new ArrayList<>();
        formData.get("roles").forEach(r -> roles.add(roleService.findRoleByName(r)));
        userDTO.setRoles(roles);

        model.addAttribute("message", userService.addUser(userDTO));

        return "redirect:/user/all";
    }



    @PostMapping("/update")
    public String updateUser(Model model, UserDTO user) {
        model.addAttribute("message", userService.updateUser(user));
        return "redirect:/user/all";
    }


    @RequestMapping("/delete/{username}")
    public String deleteUser(Model model, @PathVariable String username) {
        model.addAttribute("message", userService.deleteUser(username));

        return "redirect:/user/all";
    }

    @GetMapping("/updateForm/{id}")
    public String updateByForm(@PathVariable UUID id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("pageTitle", "Kund");
        model.addAttribute("header", "Uppdatera kund");
        model.addAttribute("usernameText", "Ändra användarnamn");
        model.addAttribute("enabled", "Aktiverad");
        model.addAttribute("buttonText", "Uppdatera");
        return "update-user";
    }

}
