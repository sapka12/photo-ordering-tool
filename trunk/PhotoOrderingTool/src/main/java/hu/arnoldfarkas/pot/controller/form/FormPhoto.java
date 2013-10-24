package hu.arnoldfarkas.pot.controller.form;

import hu.arnoldfarkas.pot.domain.Photo;
import hu.arnoldfarkas.pot.domain.PhotoTypeCounter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FormPhoto {

    private Photo photo;
    private List<PhotoTypeCounter> counters = new ArrayList<PhotoTypeCounter>();

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public List<PhotoTypeCounter> getCounters() {
        return counters;
    }

    public void setCounters(List<PhotoTypeCounter> counters) {
        Collections.sort(counters);
        this.counters = counters;
    }

    @Override
    public String toString() {
        return "FormPhoto{" + "photo=" + photo + ", counters=" + counters + '}';
    }
    
    
}
