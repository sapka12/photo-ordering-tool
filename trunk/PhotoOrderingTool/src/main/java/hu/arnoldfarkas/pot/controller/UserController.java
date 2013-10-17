package hu.arnoldfarkas.pot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = {"/login", "/"}, method = RequestMethod.GET)
    public ModelAndView loginPage() {
        LOGGER.debug("loginPage");
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login() {
        LOGGER.debug("login");
        return "redirect:/gallery";
    }
}
