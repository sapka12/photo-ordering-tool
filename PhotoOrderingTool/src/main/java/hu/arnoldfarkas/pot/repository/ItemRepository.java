package hu.arnoldfarkas.pot.repository;

import hu.arnoldfarkas.pot.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
