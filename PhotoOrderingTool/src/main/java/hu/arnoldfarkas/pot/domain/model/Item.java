package hu.arnoldfarkas.pot.domain.model;

import java.io.Serializable;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Item implements Serializable {

    private Long id;
    private Order order;
    private String photoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", order=" + order + ", photoId=" + photoId + '}';
    }
}
