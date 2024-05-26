package com.example.roombooking.controllers;

import com.example.roombooking.dto.RoleDTO;
import com.example.roombooking.services.implementations.RoleService;
import com.example.roombooking.services.implementations.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/all")
    public String getAllRoles(Model model) {
        model.addAttribute("allRoles", roleService.findAllRolesDto());
        model.addAttribute("pageTitle", "Roll");
        model.addAttribute("header", "Alla roller");
        model.addAttribute("placeholder", "Sök roll...");
        model.addAttribute("nameText", "Roll");
        model.addAttribute("deleteText", "Ta Bort");

        return "role/roles.html";
    }

    @GetMapping({"/{name}"})
    public String getRole(@PathVariable String name, Model model) {
        model.addAttribute("role", roleService.findRoleByName(name));
        model.addAttribute("pageTitle", "Roll");
        model.addAttribute("header", "Roll");
        model.addAttribute("idText", "ID");
        model.addAttribute("nameText", "Roll");
        model.addAttribute("usersText", "Roller");

        return "role/role-info.html";
    }

    @GetMapping("/all/sort")
    public String sortAndSearchRoles(Model model,
                                     @RequestParam(defaultValue = "name") String sortColumn,
                                     @RequestParam(defaultValue = "ASC") String sortOrder,
                                     @RequestParam String query) {

        model.addAttribute("allRoles", roleService.findAllRolesDto());
        model.addAttribute("pageTitle", "Roll");
        model.addAttribute("header", "Alla roller");
        model.addAttribute("placeholder", "Sök roll...");
        model.addAttribute("nameText", "Roll");
        model.addAttribute("deleteText", "Ta Bort");

        query = query.trim();
        var roles = query.isEmpty()
                ? roleService.findAllRolesSorted(sortOrder, sortColumn)
                : roleService.findAllRolesSortAndQuery(query, sortOrder, sortColumn);

        model.addAttribute("query", query);
        model.addAttribute("allUsers", roles);

        return "role/roles.html";
    }

    @GetMapping("/new")
    public String createNewRole(Model model) {
        model.addAttribute("pageTitle", "Roll");
        model.addAttribute("header", "Skapa roll");
        model.addAttribute("roleDTO", new RoleDTO());
        model.addAttribute("nameText", "Ange roll");
        model.addAttribute("nameTitle", "Använd endast mellanrum och svenska bokstäver");
        model.addAttribute("usersText", "Lägg till roll till användare");
        model.addAttribute("allUsers", userService.findAllUsers());
        model.addAttribute("submitText", "Skapa");

        return "role/new-role.html";
    }

    @PostMapping( "/add")
    public String addRole(RedirectAttributes redirectAttributes, RoleDTO role) {
        redirectAttributes.addFlashAttribute("message", roleService.addRole(role));
        return "redirect:/role/all";
    }

    @RequestMapping("/delete/{name}")
    public String deleteRole(RedirectAttributes redirectAttributes, @PathVariable String name) {
        redirectAttributes.addFlashAttribute("message", roleService.deleteRole(name));
        return "redirect:/role/all";
    }

}
