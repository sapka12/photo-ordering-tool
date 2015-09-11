package hu.arnoldfarkas.pot.domain.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Id;
import javax.persistence.Transient;

public class Order implements Serializable {

    private Long id;
    private Calendar closingDate;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Calendar closingDate) {
        this.closingDate = closingDate;
    }

    @Transient
    public boolean isActive() {
        return getClosingDate() == null;
    }
}
