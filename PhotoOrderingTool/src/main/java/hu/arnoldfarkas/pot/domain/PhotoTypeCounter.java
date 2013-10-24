package hu.arnoldfarkas.pot.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "pot_phototype_counter")
public class PhotoTypeCounter implements Serializable, Comparable<PhotoTypeCounter> {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Item item;
    @Enumerated(EnumType.STRING)
    private PhotoType type;
    private int counter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public PhotoType getType() {
        return type;
    }

    public void setType(PhotoType type) {
        this.type = type;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        return "PhotoTypeCounter{" + "id=" + id + ", item=" + item + ", type=" + type + ", counter=" + counter + '}';
    }

    @Override
    public int compareTo(PhotoTypeCounter o) {
        return getType().getX() * getType().getY() - o.getType().getX() * o.getType().getY();
    }
}
