package hu.arnoldfarkas.pot.service.flickr;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.Transport;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.AuthInterface;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photosets.Photoset;
import com.flickr4java.flickr.photosets.Photosets;
import com.flickr4java.flickr.photosets.PhotosetsInterface;
import java.io.InputStream;
import java.util.List;
import org.scribe.model.Token;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FlickrRequestHandler implements FlickrApi, InitializingBean {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FlickrRequestHandler.class);
    private static final int REQUEST_REPEAT_COUNTER = 10;
    private static final Transport TRANSPORT = new REST();
    @Value("#{flickrProperties['flickr.apisecret']}")
    private String apiSecret;
    @Value("#{flickrProperties['flickr.apikey']}")
    private String apiKey;
    @Value("#{flickrProperties['flickr.requesttoken.secret']}")
    private String requesttokenSecret;
    @Value("#{flickrProperties['flickr.requesttoken.token']}")
    private String requesttokenToken;
    private Token requestToken;
    private PhotosetsInterface photosetsInterface;
    private PhotosInterface photosInterface;
    private AuthInterface authInterface;

    @Override
    public void afterPropertiesSet() throws Exception {
        Flickr flickrApi = new Flickr(apiKey, apiSecret, TRANSPORT);
        requestToken = new Token(requesttokenToken, requesttokenSecret);
        photosetsInterface = flickrApi.getPhotosetsInterface();
        photosInterface = flickrApi.getPhotosInterface();
        authInterface = flickrApi.getAuthInterface();
    }

    private Auth getAuth() {
        int tryCounter = 0;
        while (tryCounter < REQUEST_REPEAT_COUNTER) {
            tryCounter++;
            try {
                return authInterface.checkToken(requestToken);
            } catch (FlickrException ex) {
                LOGGER.debug("{}th try.", tryCounter, ex);
            }
        }
        throw new RuntimeException();
    }

    private String getUserId() {
        return getAuth().getUser().getId();
    }

    @Override
    public Photosets getPhotosets() {
        getAuth();
        return getPhotosetsWithException();
    }

    private Photosets getPhotosetsWithException() {
        int tryCounter = 0;
        while (tryCounter < REQUEST_REPEAT_COUNTER) {
            tryCounter++;
            try {
                return photosetsInterface.getList(getUserId());
            } catch (FlickrException ex) {
                LOGGER.trace("{}th try.", tryCounter);
            }
        }
        throw new RuntimeException();
    }

    @Override
    public List<Photo> findAllByPhotoset(String photosetId, int pageSize, int page) {
        getAuth();
        int tryCounter = 0;
        FlickrException flickrException = null;
        while (tryCounter < REQUEST_REPEAT_COUNTER) {
            tryCounter++;
            try {
                PhotoList list = photosetsInterface.getPhotos(photosetId, pageSize, page);
                return list.subList(0, list.size());
            } catch (FlickrException ex) {
                LOGGER.trace("{}th try.", tryCounter);
                flickrException = ex;
            }
        }
        throw new RuntimeException(flickrException);
    }

    @Override
    public Photoset findOnePhotoset(String id) {
        int tryCounter = 0;
        while (tryCounter < REQUEST_REPEAT_COUNTER) {
            tryCounter++;
            try {
                return photosetsInterface.getInfo(id);
            } catch (FlickrException ex) {
                LOGGER.trace("{}th try.", tryCounter);
            }
        }
        throw new RuntimeException();
    }

    @Override
    public Photo findOnePhoto(String id) {
        int tryCounter = 0;
        while (tryCounter < REQUEST_REPEAT_COUNTER) {
            tryCounter++;
            try {
                return photosInterface.getPhoto(id);
            } catch (FlickrException ex) {
                LOGGER.trace("{}th try.", tryCounter);
            }
        }
        throw new RuntimeException();
    }

    @Override
    public InputStream getImage(String id, int size) {
        getAuth();
        Photo photo = findOnePhoto(id);
        int tryCounter = 0;
        while (tryCounter < REQUEST_REPEAT_COUNTER) {
            tryCounter++;
            try {
                return photosInterface.getImageAsStream(photo, size);
            } catch (FlickrException ex) {
                LOGGER.trace("{}th try.", tryCounter);
            }
        }
        throw new RuntimeException();
    }

    @Override
    public int countPhotosInPhotoset(String galleryId) {
        int tryCounter = 0;
        FlickrException flickrException = null;
        while (tryCounter < REQUEST_REPEAT_COUNTER) {
            tryCounter++;
            try {
                return photosetsInterface.getInfo(galleryId).getPhotoCount();
            } catch (FlickrException ex) {
                LOGGER.trace("{}th try.", tryCounter);
                flickrException = ex;
            }
        }
        throw new RuntimeException(flickrException);
    }
}
