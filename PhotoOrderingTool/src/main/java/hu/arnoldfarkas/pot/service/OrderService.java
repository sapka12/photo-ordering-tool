package hu.arnoldfarkas.pot.service;

import hu.arnoldfarkas.pot.controller.form.FormPhoto;
import hu.arnoldfarkas.pot.domain.Item;
import hu.arnoldfarkas.pot.domain.PhotoType;
import hu.arnoldfarkas.pot.domain.PhotoTypeCounter;
import java.util.List;

public interface OrderService {

    /**
     * all items in the active order of the user
     *
     * @param userId
     * @return
     */
    public List<Item> findAllByUser(long userId);

    /**
     * increase by incBy the count of the type of photo in the active order of
     * the user
     *
     * @param userId
     * @param photoId
     * @param incBy
     * @param photoType
     * @return
     */
    public int increasePhotoCount(long userId, String photoId, PhotoType photoType, int incBy);

    public List<PhotoTypeCounter> findAllPhotoTypeCounterByItem(long itemId);

    public List<FormPhoto> findAllByGallery(String galleryId, long userId);

    public List<FormPhoto> findAllActualOrderByUser(long userId);

    public void closeActualOrders();
}
