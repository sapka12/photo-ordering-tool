package hu.arnoldfarkas.pot.controller;

import hu.arnoldfarkas.pot.domain.model.Gallery;
import hu.arnoldfarkas.pot.domain.model.User;
import hu.arnoldfarkas.pot.service.OrderService;
import hu.arnoldfarkas.pot.service.PhotoService;
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
@RequestMapping("/gallery")
public class GalleryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GalleryController.class);
    private static final int PAGE_SIZE = 48;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView list() {
        LOGGER.debug("GalleryListController.list");
        ModelAndView mav = new ModelAndView("gallerylist");
        mav.addObject("username", getLoggedInUser().getEmail());
        mav.addObject("list", photoService.findAll());
        return mav;
    }

    @RequestMapping(value = "/{id}/page/{page}", method = RequestMethod.GET)
    public ModelAndView findGalleryByIdAndPage(@PathVariable("id") String galleryId, @PathVariable("page") int pageNumber) {
        Gallery gallery = photoService.findGallery(galleryId);

        ModelAndView mav = new ModelAndView("gallery");

        mav.addObject("username", getLoggedInUser().getEmail());
        mav.addObject("photos", orderService.findAllByGallery(gallery.getId(), PAGE_SIZE, pageNumber, getLoggedInUser().getId()));
        mav.addObject("actualpage", pageNumber);
        mav.addObject("pages", countPages(galleryId));
        mav.addObject("galleryId", gallery.getId());
        mav.addObject("galleryName", gallery.getTitle());

        return mav;
    }

    @RequestMapping(value = "/icon/{id}", method = RequestMethod.GET, produces = "image/jpg")
    public @ResponseBody
    byte[] getImage(@PathVariable("id") String id) {
        String pictureId = photoService.findGallery(id).getDefaultPictureId();
        return photoService.getImage(pictureId, PhotoService.PhotoSize.MEDIUM);
    }

    private User getLoggedInUser() {
        return userService.findLoggedInUser();
    }

    private int countPages(String galleryId) {
        return new Double(Math.ceil(new Double(1) * photoService.countPhotosInGallery(galleryId) / PAGE_SIZE)).intValue();
    }

}
