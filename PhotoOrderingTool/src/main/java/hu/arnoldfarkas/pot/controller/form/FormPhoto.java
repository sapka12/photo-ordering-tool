package hu.arnoldfarkas.pot.controller.form;

import hu.arnoldfarkas.pot.domain.Photo;

public class FormPhoto {

    private Photo photo;
    private int counter;

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
    
}
