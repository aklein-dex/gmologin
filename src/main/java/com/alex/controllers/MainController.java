package com.alex.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.alex.tools.*;
import com.alex.models.*;

@Controller
public class MainController {

    private final static Logger logger = LoggerFactory.getLogger(MainController.class);

    /**
     * Simply redirect to /admin.
     */
    @GetMapping("/")
    public String loginForm() {
        return "redirect:/admin";
    }
    
    /**
     * Page displaying the login form. 
     */
    @GetMapping("/login")
    public String loginFormReal() {
        logger.info("--> Login Get.");
        return "login";
    }
    
    /**
     * Page only accessible for authentified users.
     * If not authentified, then redirected to /login by spring security.
     */
    @GetMapping("/admin")
    public String adminOnly() {
        logger.info("--> Admin page:");
        return "admin";
    }
    
}
