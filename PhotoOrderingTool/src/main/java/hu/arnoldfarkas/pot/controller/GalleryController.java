package hu.arnoldfarkas.pot.controller;

import hu.arnoldfarkas.pot.domain.Gallery;
import hu.arnoldfarkas.pot.domain.Photo;
import hu.arnoldfarkas.pot.service.PhotoService;
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
    private PhotoService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView list() {
        LOGGER.debug("GalleryListController.list");
        ModelAndView mav = new ModelAndView("gallerylist");
        mav.addObject("list", service.findAll());
        return mav;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView findGalleryById(@PathVariable("id") String id) {
        LOGGER.debug("findGalleryById({})", id);

        List<Photo> photos = service.findAll(id);
        LOGGER.debug("find all photos");
        Gallery gallery = service.findOne(id);
        LOGGER.debug("get gallery info");

        ModelAndView mav = new ModelAndView("gallery");
        mav.addObject("galleryName", gallery.getTitle());
        mav.addObject("photos", photos);
        LOGGER.debug("ModelAndView created");
        return mav;
    }

    @RequestMapping(value = "/icon/{id}", method = RequestMethod.GET, produces = "image/jpg")
    public @ResponseBody
    byte[] getImage(@PathVariable("id") String id) {
        String pictureId = service.findOne(id).getDefaultPictureId();
        return service.getImage(pictureId, PhotoService.PhotoSize.SMALL_SQ);
    }
}
