package hu.arnoldfarkas.pot.service.jpa;

import hu.arnoldfarkas.pot.domain.Item;
import hu.arnoldfarkas.pot.domain.Order;
import hu.arnoldfarkas.pot.repository.ItemRepository;
import hu.arnoldfarkas.pot.repository.OrderRepository;
import hu.arnoldfarkas.pot.repository.UserRepository;
import hu.arnoldfarkas.pot.service.OrderService;
import hu.arnoldfarkas.pot.service.jpa.specification.ItemSpecificationBuilder;
import hu.arnoldfarkas.pot.service.jpa.specification.OrderSpecificationBuilder;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class JpaOrderService implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    private ItemSpecificationBuilder itemSpec = new ItemSpecificationBuilder();
    private OrderSpecificationBuilder orderSpec = new OrderSpecificationBuilder();

    @Override
    public List<Item> findAllByUser(long userId) {
        return itemRepository.findAll(itemSpec.buildActiveByUser(userId));
    }

    private Order findOneByUser(long userId) {
        Order order = orderRepository.findOne(orderSpec.buildActiveByUser(userId));
        if (order == null) {
            return createOrder(userId);
        }
        return order;
    }

    private Order createOrder(long userId) {
        Order o = new Order();
        o.setUser(userRepository.findOne(userId));
        return o;
    }

    @Override
    public synchronized int countPhotos(long userId, String photoId) {
        Item i = itemRepository.findOne(itemSpec.buildActiveByUserAndPhotoId(userId, photoId));
        if (i == null) {
            return 0;
        }
        return i.getQuantity();
    }

    @Override
    @Transactional
    public synchronized int increasePhotoCount(long userId, String photoId, int incBy) {
        Order order = findOneByUser(userId);
        orderRepository.save(order);

        Item item = getOrCreateItem(order, photoId);
        incQuantity(item, incBy);

        validateItem(item);

        return item.getQuantity();
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
