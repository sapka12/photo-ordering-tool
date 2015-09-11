package hu.arnoldfarkas.pot.service.jpa.specification;

import hu.arnoldfarkas.pot.domain.model.Item;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

public class ItemSpecificationBuilder {

    public Specification<Item> byOrder(final long orderId) {
        return new Specification<Item>() {
            @Override
            public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("order").get("id").as(Long.class), new Long(orderId));
            }
        };
    }

    public Specification<Item> activeByUser(final long userId) {
        return activeByUser(userId, true);
    }

    public Specification<Item> activeByUser(final long userId, final boolean active) {
        return Specifications.where(active(active)).and(byUser(userId));
    }

    public Specification<Item> byUser(final long userId) {
        return new Specification<Item>() {
            @Override
            public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("order").get("user").get("id").as(Long.class), new Long(userId));
            }
        };
    }

    public Specification<Item> byPhotoId(final String photoId) {
        return new Specification<Item>() {
            @Override
            public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("photoId").as(String.class), photoId);
            }
        };
    }

    public Specification<Item> activeByUserAndPhotoId(final long userId, final String photoId) {
        return Specifications.where(activeByUser(userId)).and(byPhotoId(photoId));
    }

    public Specification<Item> active(final boolean active) {
        return new Specification<Item>() {
            @Override
            public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (active) {
                    return cb.isNull(root.get("order").get("closingDate"));
                }
                return cb.isNotNull(root.get("order").get("closingDate"));
            }
        };
    }
}
