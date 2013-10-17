package hu.arnoldfarkas.pot.service;

import hu.arnoldfarkas.pot.domain.Gallery;
import hu.arnoldfarkas.pot.domain.Photo;
import java.util.List;

public interface PhotoService {

    public enum PhotoSize {
        ORGIGINAL,
        SMALL,
        SMALL_SQ
    }
    
    Gallery findOne(String id);
    List<Gallery> findAll();

    List<Photo> findAll(String galleryId);
    byte[] getImage(String photoId);
    byte[] getImage(String photoId, PhotoSize size);
}
