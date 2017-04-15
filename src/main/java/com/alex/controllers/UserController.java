package com.alex.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.ObjectError;

import com.alex.tools.*;
import com.alex.models.*;

@Controller
public class UserController {

	private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserManagement userManagement;
    
    /**
     * Page displaying a form for the user to register an account.
     */
    @GetMapping("/register")
    public String registerForm(Model model) {
		logger.info("--> Register Get");
		model.addAttribute("user", new User());
		return "register";
	}
	
    /**
     * When a user submit the form to register. 
     * Verify the parameters are valid and create an account.
     */
	@PostMapping("/register")
    public String registerSubmit(@Valid User user, BindingResult bindingResult, Model model) {
		logger.info("--> Register Post");
        
        if (user.getPassword() == null)
            bindingResult.addError(new ObjectError("password", "Password can't be empty"));
            
        if (bindingResult.hasErrors()) {
            return "register";
        }
        
        try {
            // save the user
            User saved = userManagement.register(user.getUsername(), user.getPassword());
            if (saved == null) {
                logger.info("  Unable to register the account.");
                model.addAttribute("error", "Internal error");
                return "register";
            }
        } catch (IllegalArgumentException e) {
            logger.info("  exception: " + e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "register";
        } catch (DataAccessException e) {
            logger.info("  exception: " + e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "register";
        }

        // success!
        return "registered";
    }
}
