package hu.arnoldfarkas.pot.service.flickr;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.Transport;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.Size;
import com.flickr4java.flickr.photosets.Photoset;
import com.flickr4java.flickr.photosets.Photosets;
import hu.arnoldfarkas.pot.domain.Gallery;
import hu.arnoldfarkas.pot.domain.Photo;
import hu.arnoldfarkas.pot.service.PhotoService;
import static hu.arnoldfarkas.pot.service.PhotoService.PhotoSize.ORGIGINAL;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.scribe.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FlickrPhotoService implements PhotoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlickrPhotoService.class);
    private static final Transport TRANSPORT = new REST();
//    @Value("${flickr.apisecret}")
    private String apiSecret = "d0fccf78386ca7ed";
//    @Value("${flickr.apikey}")
    private String apiKey = "17b52840f55eba355a4c3d20c128430d";
//    @Value("${flickr.requesttoken.secret}")
    private String requesttokenSecret = "8673eaf899068027";
//    @Value("${flickr.requesttoken.token}")
    private String requesttokenToken = "72157636348827875-e0a58fc0d7500692";
    private Flickr flickrApi;
    private Token requestToken;

    public FlickrPhotoService() {
        flickrApi = new Flickr(apiKey, apiSecret, TRANSPORT);
        requestToken = new Token(requesttokenToken, requesttokenSecret);
    }

    @Override
    public List<Gallery> findAll() {
        LOGGER.debug("FindAll");
        getAuth();
        try {
            return findAllWithException();
        } catch (FlickrException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Photo> findAll(String galleryId) {
        getAuth();
        try {
            List<Photo> photos = new ArrayList<Photo>();
            for (com.flickr4java.flickr.photos.Photo flickrPhoto : findAllWithException(galleryId)) {
                photos.add(convert(flickrPhoto));
            }
            return photos;
        } catch (FlickrException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public byte[] getImage(String photoId) {
        return getImage(photoId, PhotoSize.SMALL_SQ);
    }

    private String getUserId() {
        return getAuth().getUser().getId();
    }

    private Auth getAuth() {
        try {
            return flickrApi.getAuthInterface().checkToken(requestToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Gallery> findAllWithException() throws FlickrException {
        Photosets photosets = flickrApi.getPhotosetsInterface().getList(getUserId());

        List<Gallery> list = new ArrayList<Gallery>();
        for (Photoset photoset : photosets.getPhotosets()) {
            list.add(convert(photoset));
        }
        LOGGER.debug("findAll: {}", list);
        return list;
    }

    private Gallery convert(Photoset photoset) throws FlickrException {
        Gallery gallery = new Gallery();
        gallery.setId(photoset.getId());
        gallery.setTitle(photoset.getTitle());
        gallery.setDefaultPictureId(photoset.getPrimaryPhoto().getId());

        List<String> photos = new ArrayList<String>();
        for (com.flickr4java.flickr.photos.Photo photo : findAllWithException(photoset.getId())) {
            photos.add(photo.getId());
        }
        gallery.setPhotoIds(photos);
        return gallery;
    }

    private Iterable<com.flickr4java.flickr.photos.Photo> findAllWithException(String photosetId) throws FlickrException {
        PhotoList list = flickrApi.getPhotosetsInterface().getPhotos(photosetId, Integer.MAX_VALUE, 0);
        return list.subList(0, list.size());
    }

    private Photo convert(com.flickr4java.flickr.photos.Photo flickrPhoto) {
        Photo photo = new Photo();
        photo.setId(flickrPhoto.getId());
        photo.setTitle(flickrPhoto.getTitle());
        return photo;
    }

    @Override
    public Gallery findOne(String id) {
        try {
            Photoset photoset = flickrApi.getPhotosetsInterface().getInfo(id);
            return convert(photoset);
        } catch (FlickrException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public byte[] getImage(String photoId, PhotoSize size) {
        getAuth();
        try {
            PhotosInterface pi = flickrApi.getPhotosInterface();
            com.flickr4java.flickr.photos.Photo photo = pi.getPhoto(photoId);
            InputStream imageInputStream = pi.getImageAsStream(photo, convertSize(size));
            return IOUtils.toByteArray(imageInputStream);
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    private int convertSize(PhotoSize size) {
        switch (size) {
            case ORGIGINAL:
                return Size.ORIGINAL;
            case SMALL:
                return Size.THUMB;
            case SMALL_SQ:
            default:
                return Size.SQUARE;
        }
    }
}
