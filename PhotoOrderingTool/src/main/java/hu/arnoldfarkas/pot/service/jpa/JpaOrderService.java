package hu.arnoldfarkas.pot.service.jpa;

import hu.arnoldfarkas.pot.domain.Item;
import hu.arnoldfarkas.pot.domain.Order;
import hu.arnoldfarkas.pot.repository.ItemRepository;
import hu.arnoldfarkas.pot.repository.OrderRepository;
import hu.arnoldfarkas.pot.repository.UserRepository;
import hu.arnoldfarkas.pot.service.OrderService;
import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public List<Item> findAllByUser(long userId) {
        List<Item> items = new ArrayList<Item>();
        for (Item item : itemRepository.findAll()) {
            Order order = item.getOrder();
            if (order.getUser().getId() == userId && isActive(order)) {
                items.add(item);
            }
        }
        return items;
    }
    
    private Order findOneByUser(long userId) {
        for (Order order : repository.findAll()) {
            if (order.getUser().getId().equals(userId) && isActive(order)) {
                return order;
            }
        }
        return createOrder(userId);
    }
    
    private Order createOrder(long userId) {
        Order o = new Order();
        o.setUser(userRepository.findOne(userId));
        return o;
    }
    
    @Override
    @Transactional
    public synchronized int countPhotos(long userId, String photoId) {
        
        Order order = findOneByUser(userId);
        if (!isSaved(order)) {
            return 0;
        }
        
        for (Item item : itemRepository.findAll()) {
            if (item.getOrder().getId().equals(order.getId()) && item.getPhotoId().equals(photoId)) {
                return item.getQuantity();
            }
        }
        return 0;
    }
    
    private boolean isSaved(Order order) {
        Assert.notNull(order);
        return order.getId() != null;
    }
    
    @Override
    @Transactional
    public synchronized int increasePhotoCount(long userId, String photoId, int incBy) {
        Order order = findOneByUser(userId);
        repository.save(order);
        
        Item item = getOrCreateItem(order, photoId);
        incQuantity(item, incBy);
        
        validateItem(item);
        
        return item.getQuantity();
    }
    
    private boolean isActive(Order order) {
        Assert.notNull(order);
        if (order.getClosingDate() == null) {
            return true;
        }
        return false;
    }
    
    private Item getOrCreateItem(Order order, String photoId) {
        Item item = findOne(order.getId(), photoId);
        if (item != null) {
            return item;
        }
        return createItem(order, photoId);
    }
    
    private void incQuantity(Item item, int incBy) {
        int q = item.getQuantity();
        if (q + incBy > Integer.MAX_VALUE) {
            q = Integer.MAX_VALUE;
        } else if (q + incBy < 0) {
            q = 0;
        } else {
            q += incBy;
        }
        item.setQuantity(q);
        itemRepository.save(item);
    }
    
    private Item findOne(Long orderId, String photoId) {
        for (Item item : itemRepository.findAll()) {
            if (item.getOrder().getId().equals(orderId) && item.getPhotoId().equals(photoId)) {
                return item;
            }
        }
        return null;
    }
    
    private Item createItem(Order order, String photoId) {
        Item item = new Item();
        item.setOrder(order);
        item.setPhotoId(photoId);
        return item;
    }
    
    private void validateItem(Item item) {
        Assert.notNull(item);
        if (item.getQuantity() > 0) {
            return;
        }
        itemRepository.delete(item);
    }
}
