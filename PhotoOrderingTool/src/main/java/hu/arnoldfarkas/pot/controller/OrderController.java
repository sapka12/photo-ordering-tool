package hu.arnoldfarkas.pot.controller;

import hu.arnoldfarkas.pot.controller.form.FormClosedOrder;
import hu.arnoldfarkas.pot.controller.form.FormOrder;
import hu.arnoldfarkas.pot.controller.form.FormPhoto;
import hu.arnoldfarkas.pot.domain.Item;
import hu.arnoldfarkas.pot.domain.Order;
import hu.arnoldfarkas.pot.domain.Photo;
import hu.arnoldfarkas.pot.domain.User;
import hu.arnoldfarkas.pot.service.OrderService;
import hu.arnoldfarkas.pot.service.PhotoService;
import hu.arnoldfarkas.pot.service.UserService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/order")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PhotoService photoService;

    @RequestMapping(value = "/myclosed", method = RequestMethod.GET)
    public ModelAndView findAllClosedOrdersByUser() {
        User u = getLoggedInUser();
        ModelAndView mav = new ModelAndView("closed-orders-by-user");
        mav.addObject("username", u.getEmail());
        mav.addObject("orders", findAllClosedOrders(u));
        return mav;
    }

    private FormClosedOrder findAllClosedOrders(User user) {
        FormClosedOrder fo = new FormClosedOrder();
        fo.setUser(user);
        Map<String, List<FormPhoto>> orders = new HashMap<String, List<FormPhoto>>();
        for (Order order : orderService.findAllClosedOrderByUser(user.getId())) {
            orders.put(time(order.getClosingDate()), findAllByOrder(order));
        }
        fo.setOrders(orders);
        return fo;
    }

    private List<FormPhoto> findAllByOrder(Order order) {
        List<FormPhoto> formPhotos = new ArrayList<FormPhoto>();
        List<Item> items = orderService.findAllByUser(order.getUser().getId(), false);
        for (Item item : items) {
            if (item.getOrder().getId().equals(order.getId())) {
                FormPhoto formPhoto = new FormPhoto();
                formPhoto.setPhoto(getPhotoById(item.getPhotoId()));
                formPhoto.setCounters(orderService.findAllPhotoTypeCounterByItem(item.getId()));
                formPhotos.add(formPhoto);
            }
        }
        return formPhotos;
    }

    @RequestMapping(value = "/allactual", method = RequestMethod.GET)
    public ModelAndView findAllOpenOrder() {
        ModelAndView mav = new ModelAndView("orders-actual");
        mav.addObject("username", getLoggedInUser().getEmail());
        mav.addObject("orders", findAllOpenOrders());
        return mav;
    }

    @RequestMapping(value = "/close", method = RequestMethod.POST)
    public @ResponseBody
    void closeOrders() {
        LOGGER.debug("closing orders.");
        orderService.closeActualOrders();
        LOGGER.debug("orders closed.");
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
        mav.addObject("username", user.getEmail());
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

    private Photo getPhotoById(String photoId) {
        return photoService.findPhoto(photoId);
    }

    private String time(Calendar c) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
    }
}
