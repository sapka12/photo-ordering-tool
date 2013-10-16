package hu.arnoldfarkas.pot.service;

import hu.arnoldfarkas.pot.domain.Gallery;
import hu.arnoldfarkas.pot.domain.Photo;
import java.util.List;

public interface PhotoService {

    Gallery findOne(String id);
    List<Gallery> findAll();

    List<Photo> findAll(String galleryId);
    byte[] getImage(String photoId);
}
