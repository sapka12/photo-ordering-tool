package hu.arnoldfarkas.pot.controller;

import hu.arnoldfarkas.pot.service.OrderService;
import hu.arnoldfarkas.pot.service.PhotoService;
import hu.arnoldfarkas.pot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    private PhotoService service;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "image/jpg")
    public @ResponseBody
    byte[] getImage(@PathVariable("id") String id) {
        return service.getImage(id, PhotoService.PhotoSize.SMALL);
    }

    @RequestMapping(value = "/inc/{id}", method = RequestMethod.POST)
    public @ResponseBody
    int increasePhotoInOrder(@PathVariable("id") String id) {
        return inc(id, true);
    }

    @RequestMapping(value = "/dec/{id}", method = RequestMethod.POST)
    public @ResponseBody
    int decreasePhotoInOrder(@PathVariable("id") String id) {
        return inc(id, false);
    }

    private int inc(String photoId, boolean increasing) {
        return orderService.increasePhotoCount(getLoggedInUserId(), photoId, increasing ? 1 : -1);
    }

    private long getLoggedInUserId() {
        return userService.findLoggedInUser().getId();
    }
}
