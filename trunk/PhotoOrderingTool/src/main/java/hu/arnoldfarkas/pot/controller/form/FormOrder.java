package hu.arnoldfarkas.pot.controller.form;

import hu.arnoldfarkas.pot.domain.PhotoTypeCounter;
import hu.arnoldfarkas.pot.domain.User;
import java.util.List;

public class FormOrder {

    private User user;
    private List<FormPhoto> photos;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<FormPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<FormPhoto> photos) {
        this.photos = photos;
    }

    public int getSum() {
        int counter = 0;
        for (FormPhoto formPhoto : photos) {
            for (PhotoTypeCounter photoTypeCounter : formPhoto.getCounters()) {
                counter += photoTypeCounter.getCounter();
            }
        }
        return counter;
    }
}
