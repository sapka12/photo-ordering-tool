package hu.arnoldfarkas.pot.service;

import hu.arnoldfarkas.pot.domain.model.Gallery;
import hu.arnoldfarkas.pot.domain.model.Photo;
import java.util.List;

public interface PhotoService {

    public int countPhotosInGallery(String galleryId);

    public enum PhotoSize {
        ORGIGINAL,
        LARGE,
        MEDIUM,
        SMALL,
        SMALL_SQ
    }

    Gallery findGallery(String id);

    Photo findPhoto(String id);

    List<Gallery> findAll();

    List<Photo> findAll(String galleryId, int pageSize, int page);

    byte[] getImage(String photoId);

    byte[] getImage(String photoId, PhotoSize size);

}
