package hu.arnoldfarkas.pot.repository;

import hu.arnoldfarkas.pot.domain.PhotoTypeCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PhotoTypeCounterRepository extends JpaRepository<PhotoTypeCounter, Long>, JpaSpecificationExecutor<PhotoTypeCounter> {
}
