package hu.arnoldfarkas.pot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    
    @RequestMapping("/")
    public ModelAndView hello() {
        LOGGER.debug("UserController.HELLO");
        return new ModelAndView("index", "paragraph", "something from controller");
    }
}
