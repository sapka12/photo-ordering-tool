package hu.arnoldfarkas.pot.service.jpa;

import hu.arnoldfarkas.pot.domain.Item;
import hu.arnoldfarkas.pot.domain.Order;
import hu.arnoldfarkas.pot.repository.ItemRepository;
import hu.arnoldfarkas.pot.repository.OrderRepository;
import hu.arnoldfarkas.pot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class JpaOrderService implements OrderService {

    @Autowired
    private OrderRepository repository;
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Order findActiveByUser(Long userId) {
        for (Order order : repository.findAll()) {
            if (order.getUser().getId().equals(userId) && isActive(order)) {
                return order;
            }
        }
        return new Order();
    }

    private boolean isActive(Order order) {
        Assert.notNull(order);
        if (order.getClosingDate() == null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public synchronized int increasePhotoCount(long orderId, String photoId) {
        Order order = getOrCreateOrder(orderId);
        Item item = getOrCreateItem(order, photoId);
        incQuantity(item);
        return item.getQuantity();
    }

    private Order getOrCreateOrder(long orderId) {
        Order order = repository.findOne(orderId);
        if (order == null) {
            order = new Order();
            
        }
        return order;
    }

    private Item getOrCreateItem(Order order, String photoId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void incQuantity(Item item) {
        item.setQuantity(item.getQuantity()+1);
        itemRepository.save(item);
    }

}
