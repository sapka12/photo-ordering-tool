package hu.arnoldfarkas.pot.service.flickr;

import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photosets.Photoset;
import com.flickr4java.flickr.photosets.Photosets;
import java.io.InputStream;
import java.util.List;

public interface FlickrApi {

    Photosets getPhotosets();
    List<Photo> findAllByPhotoset(String photosetId);
    Photoset findOnePhotoset(String id);
    Photo findOnePhoto(String id);
    InputStream getImage(String id, int size);
}
