package hu.arnoldfarkas.pot.service.jpa.specification;

import hu.arnoldfarkas.pot.domain.PhotoType;
import hu.arnoldfarkas.pot.domain.PhotoTypeCounter;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

public class PhotoTypeCounterSpecificationBuilder {

    public Specification<PhotoTypeCounter> buildByItem(final long userId) {
        return new Specification<PhotoTypeCounter>() {
            @Override
            public Predicate toPredicate(Root<PhotoTypeCounter> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("item").get("id").as(Long.class), new Long(userId));
            }
        };
    }

    public Specification<PhotoTypeCounter> buildByPhotoType(final PhotoType photoType) {
        return new Specification<PhotoTypeCounter>() {
            @Override
            public Predicate toPredicate(Root<PhotoTypeCounter> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("type").as(PhotoType.class), photoType);
            }
        };
    }

    public Specification<PhotoTypeCounter> buildByItemAndPhotoType(final long itemId, final PhotoType photoType) {
        return Specifications.where(buildByItem(itemId)).and(buildByPhotoType(photoType));
    }

    public Specification<PhotoTypeCounter> buildByOrder(final long userId) {
        return new Specification<PhotoTypeCounter>() {
            @Override
            public Predicate toPredicate(Root<PhotoTypeCounter> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("item").get("id").as(Long.class), new Long(userId));
            }
        };
    }
}
