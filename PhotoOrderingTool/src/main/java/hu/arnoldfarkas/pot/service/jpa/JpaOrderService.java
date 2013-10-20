package hu.arnoldfarkas.pot.service.jpa;

import hu.arnoldfarkas.pot.controller.form.FormPhoto;
import hu.arnoldfarkas.pot.domain.Item;
import hu.arnoldfarkas.pot.domain.Order;
import hu.arnoldfarkas.pot.domain.Photo;
import hu.arnoldfarkas.pot.domain.PhotoType;
import hu.arnoldfarkas.pot.domain.PhotoTypeCounter;
import hu.arnoldfarkas.pot.repository.ItemRepository;
import hu.arnoldfarkas.pot.repository.OrderRepository;
import hu.arnoldfarkas.pot.repository.PhotoTypeCounterRepository;
import hu.arnoldfarkas.pot.repository.UserRepository;
import hu.arnoldfarkas.pot.service.OrderService;
import hu.arnoldfarkas.pot.service.PhotoService;
import hu.arnoldfarkas.pot.service.jpa.specification.ItemSpecificationBuilder;
import hu.arnoldfarkas.pot.service.jpa.specification.OrderSpecificationBuilder;
import hu.arnoldfarkas.pot.service.jpa.specification.PhotoTypeCounterSpecificationBuilder;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class JpaOrderService implements OrderService {

    @Autowired
    private PhotoService photoService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private PhotoTypeCounterRepository photoTypeCounterRepository;
    @Autowired
    private UserRepository userRepository;
    private ItemSpecificationBuilder itemSpec = new ItemSpecificationBuilder();
    private OrderSpecificationBuilder orderSpec = new OrderSpecificationBuilder();
    private PhotoTypeCounterSpecificationBuilder typeSpecBuilder = new PhotoTypeCounterSpecificationBuilder();

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
    @Transactional
    public synchronized int increasePhotoCount(long userId, String photoId, PhotoType photoType, int incBy) {
        Assert.notNull(photoId);
        Assert.notNull(photoType);

        Order order = findOneByUser(userId);
        orderRepository.save(order);

        Item item = getOrCreateItem(order, photoId);
        itemRepository.save(item);
        Assert.notNull(item);
        PhotoTypeCounter typeCounter = getOrCreateTypeCounter(item, photoType);
        incQuantity(typeCounter, incBy);

        validateTypeCounter(typeCounter);
        validateItem(item);

        return typeCounter.getCounter();
    }

    private Item getOrCreateItem(Order order, String photoId) {
        Item item = findOne(order.getId(), photoId);
        if (item != null) {
            return item;
        }
        return createItem(order, photoId);
    }

    private void incQuantity(PhotoTypeCounter photoTypeCounter, int incBy) {
        int q = photoTypeCounter.getCounter();
        if (q + incBy > Integer.MAX_VALUE) {
            q = Integer.MAX_VALUE;
        } else if (q + incBy < 0) {
            q = 0;
        } else {
            q += incBy;
        }
        photoTypeCounter.setCounter(q);
        photoTypeCounterRepository.save(photoTypeCounter);
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
        if (!isEmpty(item)) {
            return;
        }
        itemRepository.delete(item);
    }

    private boolean isEmpty(Item item) {
        return photoTypeCounterRepository.count(typeSpecBuilder.buildByItem(item.getId())) < 1;
    }

    private void validateTypeCounter(PhotoTypeCounter typeCounter) {
        Assert.notNull(typeCounter);
        if (typeCounter.getCounter() > 0) {
            return;
        }
        photoTypeCounterRepository.delete(typeCounter);
    }

    @Override
    public List<PhotoTypeCounter> findAllPhotoTypeCounterByItem(long id) {
        List<PhotoTypeCounter>  counters = photoTypeCounterRepository.findAll(typeSpecBuilder.buildByItem(id));
        validate(counters);
        return counters;
    }

    private PhotoTypeCounter getOrCreateTypeCounter(Item item, PhotoType photoType) {
        PhotoTypeCounter c = photoTypeCounterRepository.findOne(
          typeSpecBuilder.buildByItemAndPhotoType(item.getId(), photoType));
        if (c != null) {
            return c;
        }
        return createPhotoTypeCounter(item, photoType);
    }

    private PhotoTypeCounter createPhotoTypeCounter(Item item, PhotoType photoType) {
        PhotoTypeCounter c = new PhotoTypeCounter();
        c.setItem(item);
        c.setType(photoType);
        return c;
    }

    @Override
    public List<FormPhoto> findAllByGallery(String galleryId, long userId) {
        final List<Item> ownedItems = findAllByUser(userId);
        List<Photo> photos = photoService.findAll(galleryId);
        List<FormPhoto> formPhotos = new ArrayList<FormPhoto>();
        for (Photo photo : photos) {
            formPhotos.add(createformPhoto(photo, ownedItems));
        }
        return formPhotos;
    }

    private FormPhoto createformPhoto(Photo photo, List<Item> ownedItems) {
        FormPhoto fp = new FormPhoto();
        fp.setPhoto(photo);
        fp.setCounters(initCounters(photo.getId(), ownedItems));
        return fp;
    }

    private List<PhotoTypeCounter> initCounters(String photoId, List<Item> ownedItems) {
        List<PhotoTypeCounter> counters = new ArrayList<PhotoTypeCounter>();
        for (Item item : ownedItems) {
            if (item.getPhotoId().equals(photoId)) {
                counters.addAll(findAllPhotoTypeCounterByItem(item.getId()));
            }
        }
        validate(counters);
        return counters;
    }

    private void validate(List<PhotoTypeCounter> counters) {
        for (PhotoType type : PhotoType.values()) {
            boolean contains = false;
            for (PhotoTypeCounter photoTypeCounter : counters) {
                if (photoTypeCounter.getType().equals(type)) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                counters.add(createEmpty(type));
            }
        }
    }

    private PhotoTypeCounter createEmpty(PhotoType type) {
        PhotoTypeCounter ptc = new PhotoTypeCounter();
        ptc.setType(type);
        return ptc;
    }

    @Override
    public List<FormPhoto> findAllActualOrderByUser(long userId) {
        List<FormPhoto> formPhotos = new ArrayList<FormPhoto>();
        for (Item item : findAllByUser(userId)) {
            formPhotos.add(convert(item));
        }
        return formPhotos;

    }

    private FormPhoto convert(Item item) {
        FormPhoto p = new FormPhoto();
        p.setPhoto(photoService.findPhoto(item.getPhotoId()));
        p.setCounters(findAllPhotoTypeCounterByItem(item.getId()));
        return p;
    }
}
