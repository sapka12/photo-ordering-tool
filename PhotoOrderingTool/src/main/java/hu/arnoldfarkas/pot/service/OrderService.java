package hu.arnoldfarkas.pot.service;

import hu.arnoldfarkas.pot.domain.Order;

public interface OrderService {

    public Order findActiveByUser(Long userId);

    public int increasePhotoCount(long actualOrderId, String id);
    
}
