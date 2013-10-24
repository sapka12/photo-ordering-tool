package hu.arnoldfarkas.pot.controller;

import hu.arnoldfarkas.pot.domain.PhotoType;
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
        return service.getImage(id, PhotoService.PhotoSize.MEDIUM);
    }

    @RequestMapping(value = "/medium/{id}", method = RequestMethod.GET, produces = "image/jpg")
    public @ResponseBody
    byte[] getMediumImage(@PathVariable("id") String id) {
        return service.getImage(id, PhotoService.PhotoSize.MEDIUM);
    }

    @RequestMapping(value = "/inc/{type}/{id}", method = RequestMethod.POST)
    public @ResponseBody
    int increasePhotoInOrder(@PathVariable("id") String id, @PathVariable("type") String type) {
        return inc(id, true, parseType(type));
    }

    @RequestMapping(value = "/dec/{type}/{id}", method = RequestMethod.POST)
    public @ResponseBody
    int decreasePhotoInOrder(@PathVariable("id") String id, @PathVariable("type") String type) {
        return inc(id, false, parseType(type));
    }

    private int inc(String photoId, boolean increasing, PhotoType type) {
        int incVal = increasing ? 1 : -1;
        return orderService.increasePhotoCount(getLoggedInUserId(), photoId, type, incVal);
    }

    private long getLoggedInUserId() {
        return userService.findLoggedInUser().getId();
    }

    private PhotoType parseType(String type) {
        return PhotoType.valueOf(type);
    }
}
