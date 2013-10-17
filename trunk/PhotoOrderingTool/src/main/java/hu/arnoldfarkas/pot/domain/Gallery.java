package hu.arnoldfarkas.pot.domain;

import java.util.List;

public class Gallery {

    private String id;
    private String title;
    private String defaultPictureId;
    private List<String> photoIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getPhotoIds() {
        return photoIds;
    }

    public void setPhotoIds(List<String> photoIds) {
        this.photoIds = photoIds;
    }

    public String getDefaultPictureId() {
        return defaultPictureId;
    }

    public void setDefaultPictureId(String defaultPictureId) {
        this.defaultPictureId = defaultPictureId;
    }
}
