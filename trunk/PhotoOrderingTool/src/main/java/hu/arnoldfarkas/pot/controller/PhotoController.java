package hu.arnoldfarkas.pot.controller;

import hu.arnoldfarkas.pot.service.PhotoService;
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "image/jpg")
    public @ResponseBody
    byte[] getImage(@PathVariable("id") String id) {
        return service.getImage(id, PhotoService.PhotoSize.SMALL);
    }
}
