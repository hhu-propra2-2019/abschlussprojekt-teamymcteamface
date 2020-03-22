package mops.foren.infrastructure.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.security.RolesAllowed;

@Controller
@SessionScope
@RequestMapping("/foren")
@RolesAllowed({"ROLE_studentin", "ROLE_orga"})
public class DashboardController {

    @GetMapping
    public String main() {
        return "index";
    }
}
