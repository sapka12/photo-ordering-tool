package hu.arnoldfarkas.pot.controller.form;

import hu.arnoldfarkas.pot.domain.model.User;
import java.util.List;
import java.util.Map;

public class FormClosedOrder {

    private User user;
    private Map<String, List<FormPhoto>> orders;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<String, List<FormPhoto>> getOrders() {
        return orders;
    }

    public void setOrders(Map<String, List<FormPhoto>> orders) {
        this.orders = orders;
    }
}
