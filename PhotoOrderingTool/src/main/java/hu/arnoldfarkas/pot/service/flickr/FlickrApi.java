package hu.arnoldfarkas.pot.service.flickr;

import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photosets.Photoset;
import com.flickr4java.flickr.photosets.Photosets;
import java.io.InputStream;
import java.util.List;

public interface FlickrApi {

    Photosets getPhotosets();
    List<Photo> findAllByPhotoset(String photosetId, int pageSize, int page);
    Photoset findOnePhotoset(String id);
    Photo findOnePhoto(String id);
    InputStream getImage(String id, int size);

    public int countPhotosInPhotoset(String galleryId);
}
