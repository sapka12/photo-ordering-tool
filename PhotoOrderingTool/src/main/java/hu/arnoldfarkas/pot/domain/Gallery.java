package hu.arnoldfarkas.pot.domain;

import java.util.List;

public class Gallery {

    private String id;
    private String title;
    private String defaultPictureId;

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

    public String getDefaultPictureId() {
        return defaultPictureId;
    }

    public void setDefaultPictureId(String defaultPictureId) {
        this.defaultPictureId = defaultPictureId;
    }
}
