package hu.arnoldfarkas.pot.controller;

import hu.arnoldfarkas.pot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;
    
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
    
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView listUsers() {
        LOGGER.debug("users");
        ModelAndView mav = new ModelAndView("users");
        mav.addObject("users", service.findAll());
        return mav;
    }
    
    @RequestMapping(value = "/user/changeadminrole/{userId}", method = RequestMethod.POST)
    public @ResponseBody boolean changeAdminRoleOfUser(@PathVariable("userId") String userId) {
        return service.changeAdminRole(userId);
    }
    
    
}
