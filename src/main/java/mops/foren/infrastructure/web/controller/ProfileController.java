package mops.foren.infrastructure.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.security.RolesAllowed;

@Controller
@SessionScope
@RolesAllowed({"ROLE_studentin", "ROLE_orga"})
@RequestMapping("/foren/profile")
public class ProfileController {

    @GetMapping
    public String profile() {
        return "profile";
    }
}
