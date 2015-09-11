package hu.arnoldfarkas.pot.service.flickr;

import com.flickr4java.flickr.photos.Size;
import com.flickr4java.flickr.photosets.Photoset;
import com.flickr4java.flickr.photosets.Photosets;
import hu.arnoldfarkas.pot.domain.model.Gallery;
import hu.arnoldfarkas.pot.domain.model.Photo;
import hu.arnoldfarkas.pot.service.PhotoService;
import static hu.arnoldfarkas.pot.service.PhotoService.PhotoSize.ORGIGINAL;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class FlickrPhotoService implements PhotoService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FlickrPhotoService.class);
    @Autowired
    private FlickrApi flickrApi;
    
    public FlickrPhotoService() {
    }
    
    @Override
    public List<Gallery> findAll() {
        LOGGER.debug("FindAll");
        Photosets photosets = flickrApi.getPhotosets();
        List<Gallery> list = new ArrayList<Gallery>();
        for (Photoset photoset : photosets.getPhotosets()) {
            list.add(convert(photoset));
        }
        sort(list);
        LOGGER.debug("findAll: {}", list);
        return list;
    }
    
    @Override
    public List<Photo> findAll(String galleryId, int pageSize, int page) {
        Assert.isTrue(pageSize <= 500);
        List<Photo> photos = new ArrayList<Photo>();
        for (com.flickr4java.flickr.photos.Photo flickrPhoto : flickrApi.findAllByPhotoset(galleryId, pageSize, page)) {
            photos.add(convert(flickrPhoto));
        }
        return photos;
    }
    
    @Override
    public byte[] getImage(String photoId) {
        return getImage(photoId, PhotoSize.SMALL_SQ);
    }
    
    private Gallery convert(Photoset photoset) {
        Gallery gallery = new Gallery();
        gallery.setId(photoset.getId());
        gallery.setTitle(photoset.getTitle());
        gallery.setDefaultPictureId(photoset.getPrimaryPhoto().getId());
        return gallery;
    }
    
    private Photo convert(com.flickr4java.flickr.photos.Photo flickrPhoto) {
        Photo photo = new Photo();
        photo.setId(flickrPhoto.getId());
        photo.setTitle(flickrPhoto.getTitle());
        return photo;
    }
    
    @Override
    public Gallery findGallery(String id) {
        Photoset photoset = flickrApi.findOnePhotoset(id);
        return convert(photoset);
    }
    
    public byte[] getImageWithException(String photoId, PhotoSize size) throws IOException {
        InputStream imageInputStream = flickrApi.getImage(photoId, convertSize(size));
        return IOUtils.toByteArray(imageInputStream);
    }
    
    @Override
    public byte[] getImage(String photoId, PhotoSize size) {
        try {
            return getImageWithException(photoId, size);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private int convertSize(PhotoSize size) {
        switch (size) {
            case ORGIGINAL:
                return Size.ORIGINAL;
            case SMALL:
                return Size.THUMB;
            case MEDIUM:
                return Size.MEDIUM;
            case LARGE:
                return Size.LARGE;
            case SMALL_SQ:
            default:
                return Size.SQUARE;
        }
    }
    
    @Override
    public Photo findPhoto(String id) {
        return convert(flickrApi.findOnePhoto(id));
    }
    
    private void sort(List<Gallery> list) {
        Collections.sort(list, new Comparator<Gallery>() {
            @Override
            public int compare(Gallery o1, Gallery o2) {
                return o2.getTitle().compareTo(o1.getTitle());
            }
        });
    }

    @Override
    public int countPhotosInGallery(String galleryId) {
        return flickrApi.countPhotosInPhotoset(galleryId);
    }
}
