package hu.arnoldfarkas.pot.controller;

import hu.arnoldfarkas.pot.controller.form.FormOrder;
import hu.arnoldfarkas.pot.controller.form.FormPhoto;
import hu.arnoldfarkas.pot.domain.User;
import hu.arnoldfarkas.pot.service.OrderService;
import hu.arnoldfarkas.pot.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/order")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/allactual", method = RequestMethod.GET)
    public ModelAndView findAllOpenOrder() {
        ModelAndView mav = new ModelAndView("orders-actual");
        mav.addObject("username", getLoggedInUser().getUsername());
        mav.addObject("orders", findAllOpenOrders());
        return mav;
    }

    private List<FormOrder> findAllOpenOrders() {
        List<FormOrder> orders = new ArrayList<FormOrder>();
        for (User user : userService.findAll()) {
            FormOrder formOrder = getFormOrderByUser(user);
            if (formOrder.getSum() > 0) {
                orders.add(formOrder);
            }
        }
        return orders;
    }

    private FormOrder getFormOrderByUser(User user) {
        FormOrder formOrder = new FormOrder();
        formOrder.setUser(user);
        formOrder.setPhotos(findAll(user.getId()));
        return formOrder;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView findOrderList() {
        ModelAndView mav = new ModelAndView("gallery");
        User user = getLoggedInUser();
        mav.addObject("username", user.getUsername());
        mav.addObject("galleryName", "Order");
        mav.addObject("photos", findAll(user.getId()));
        return mav;
    }

    private List<FormPhoto> findAll(long userId) {
        return orderService.findAllActualOrderByUser(userId);
    }

    private User getLoggedInUser() {
        return userService.findLoggedInUser();
    }
}
