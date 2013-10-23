package hu.arnoldfarkas.pot.domain;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.Transient;

@Entity(name = "pot_orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar closingDate;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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
