package hu.arnoldfarkas.pot.service.jpa;

import hu.arnoldfarkas.pot.domain.Item;
import hu.arnoldfarkas.pot.repository.ItemRepository;
import hu.arnoldfarkas.pot.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JpaItemService implements ItemService {

    @Autowired
    private ItemRepository repository;

    @Override
    public int countPhotos(String photoId, Long orderId) {
        for (Item item : repository.findAll()) {
            if (item.getOrder().getId().equals(orderId) && item.getPhotoId().equals(photoId)) {
                return item.getQuantity();
            }
        }
        return 0;
    }
}
