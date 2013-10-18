package hu.arnoldfarkas.pot.controller;

import hu.arnoldfarkas.pot.controller.form.FormPhoto;
import hu.arnoldfarkas.pot.domain.Gallery;
import hu.arnoldfarkas.pot.domain.Photo;
import hu.arnoldfarkas.pot.domain.User;
import hu.arnoldfarkas.pot.service.OrderService;
import hu.arnoldfarkas.pot.service.PhotoService;
import hu.arnoldfarkas.pot.service.UserService;
import java.util.ArrayList;
import java.util.List;
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
        mav.addObject("username", getLoggedInUser().getUsername());
        mav.addObject("list", photoService.findAll());
        return mav;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView findGalleryById(@PathVariable("id") String id) {
        LOGGER.debug("findGalleryById({})", id);

        Gallery gallery = photoService.findGallery(id);
        LOGGER.debug("get gallery info");

        ModelAndView mav = new ModelAndView("gallery");
        mav.addObject("username", getLoggedInUser().getUsername());
        mav.addObject("galleryName", gallery.getTitle());
        mav.addObject("photos", findAll(gallery.getId()));
        LOGGER.debug("ModelAndView created");
        return mav;
    }

    @RequestMapping(value = "/icon/{id}", method = RequestMethod.GET, produces = "image/jpg")
    public @ResponseBody
    byte[] getImage(@PathVariable("id") String id) {
        String pictureId = photoService.findGallery(id).getDefaultPictureId();
        return photoService.getImage(pictureId, PhotoService.PhotoSize.SMALL_SQ);
    }

    private List<FormPhoto> findAll(String galleryId) {
        List<Photo> photos = photoService.findAll(galleryId);
        List<FormPhoto> formPhotos = new ArrayList<FormPhoto>();
        for (Photo photo : photos) {
            formPhotos.add(createformPhoto(photo));
        }
        return formPhotos;
    }

    private FormPhoto createformPhoto(Photo photo) {
        FormPhoto fp = new FormPhoto();
        fp.setPhoto(photo);
        fp.setCounter(getCounter(photo.getId()));
        return fp;
    }

    private User getLoggedInUser() {
        return userService.findLoggedInUser();
    }

    private int getCounter(String photoId) {
        return orderService.countPhotos(getLoggedInUser().getId(), photoId);
    }
}
