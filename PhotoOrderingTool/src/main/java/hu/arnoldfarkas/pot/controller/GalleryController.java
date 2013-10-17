package hu.arnoldfarkas.pot.controller;

import hu.arnoldfarkas.pot.controller.form.FormPhoto;
import hu.arnoldfarkas.pot.domain.Gallery;
import hu.arnoldfarkas.pot.domain.Order;
import hu.arnoldfarkas.pot.domain.Photo;
import hu.arnoldfarkas.pot.domain.User;
import hu.arnoldfarkas.pot.service.ItemService;
import hu.arnoldfarkas.pot.service.OrderService;
import hu.arnoldfarkas.pot.service.PhotoService;
import hu.arnoldfarkas.pot.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView list() {
        LOGGER.debug("GalleryListController.list");
        ModelAndView mav = new ModelAndView("gallerylist");
        mav.addObject("list", photoService.findAll());
        return mav;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView findGalleryById(@PathVariable("id") String id) {
        LOGGER.debug("findGalleryById({})", id);

        Gallery gallery = photoService.findOne(id);
        LOGGER.debug("get gallery info");

        ModelAndView mav = new ModelAndView("gallery");
        mav.addObject("galleryName", gallery.getTitle());
        mav.addObject("photos", findPhotos(gallery));
        LOGGER.debug("ModelAndView created");
        return mav;
    }

    @RequestMapping(value = "/icon/{id}", method = RequestMethod.GET, produces = "image/jpg")
    public @ResponseBody
    byte[] getImage(@PathVariable("id") String id) {
        String pictureId = photoService.findOne(id).getDefaultPictureId();
        return photoService.getImage(pictureId, PhotoService.PhotoSize.SMALL_SQ);
    }

    private List<FormPhoto> findPhotos(Gallery gallery) {
        List<Photo> photos = photoService.findAll(gallery.getId());
        Order order = getOrder();
        List<FormPhoto> formPhotos = new ArrayList<FormPhoto>();
        for (Photo photo : photos) {
            formPhotos.add(createformPhoto(photo, order));
        }

        return formPhotos;
    }

    private FormPhoto createformPhoto(Photo photo, Order order) {
        FormPhoto fp = new FormPhoto();
        fp.setPhoto(photo);
        fp.setCounter(getCounter(order, photo));
        return fp;
    }

    private Order getOrder() {
        Long userId = getUserIdFromSession();
        return orderService.findActiveByUser(userId);
    }

    private Long getUserIdFromSession() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(auth.getName());
        return user.getId();
    }

    private int getCounter(Order order, Photo photo) {
        return itemService.countPhotos(photo.getId(), order.getId());
    }
}
