package hu.arnoldfarkas.pot.service.jpa.specification;

import hu.arnoldfarkas.pot.domain.model.PhotoType;
import hu.arnoldfarkas.pot.domain.model.PhotoTypeCounter;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

public class PhotoTypeCounterSpecificationBuilder {

    public Specification<PhotoTypeCounter> byItem(final long itemId) {
        return new Specification<PhotoTypeCounter>() {
            @Override
            public Predicate toPredicate(Root<PhotoTypeCounter> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("item").get("id").as(Long.class), new Long(itemId));
            }
        };
    }

    public Specification<PhotoTypeCounter> byPhotoType(final PhotoType photoType) {
        return new Specification<PhotoTypeCounter>() {
            @Override
            public Predicate toPredicate(Root<PhotoTypeCounter> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("type").as(PhotoType.class), photoType);
            }
        };
    }

    public Specification<PhotoTypeCounter> byItemAndPhotoType(final long itemId, final PhotoType photoType) {
        return Specifications.where(byItem(itemId)).and(byPhotoType(photoType));
    }

    public Specification<PhotoTypeCounter> byOrder(final long userId) {
        return new Specification<PhotoTypeCounter>() {
            @Override
            public Predicate toPredicate(Root<PhotoTypeCounter> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("item").get("id").as(Long.class), new Long(userId));
            }
        };
    }
}