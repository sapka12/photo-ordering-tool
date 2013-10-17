package hu.arnoldfarkas.pot.repository;

import hu.arnoldfarkas.pot.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
