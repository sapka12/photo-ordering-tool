package hu.arnoldfarkas.pot.service;

import hu.arnoldfarkas.pot.domain.Item;
import java.util.List;

public interface OrderService {

    /**
     * all items in the active order of the user
     * @param userId
     * @return 
     */
    public List<Item> findAllByUser(long userId);

    /**
     * increase by incBy the count of the photo in the active order of the user
     * @param userId
     * @param photoId
     * @param incBy
     * @return 
     */
    public int increasePhotoCount(long userId, String photoId, int incBy);

    /**
     * count the photos in the active order of the user
     * @param userId
     * @param photoId
     * @return 
     */
    int countPhotos(long userId, String photoId);
}