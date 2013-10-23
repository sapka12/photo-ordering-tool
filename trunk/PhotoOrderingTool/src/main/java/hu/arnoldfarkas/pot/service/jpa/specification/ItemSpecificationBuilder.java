package hu.arnoldfarkas.pot.service.jpa.specification;

import hu.arnoldfarkas.pot.domain.Item;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

public class ItemSpecificationBuilder {

    public Specification<Item> buildActiveByUser(final long userId) {
        return buildActiveByUser(userId, true);
    }

    public Specification<Item> buildActiveByUser(final long userId, final boolean active) {
        return Specifications.where(buildActive(active)).and(buildByUser(userId));
    }

    public Specification<Item> buildByUser(final long userId) {
        return new Specification<Item>() {
            @Override
            public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("order").get("user").get("id").as(Long.class), new Long(userId));
            }
        };
    }

    public Specification<Item> buildByPhotoId(final String photoId) {
        return new Specification<Item>() {
            @Override
            public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("photoId").as(String.class), photoId);
            }
        };
    }

    public Specification<Item> buildActiveByUserAndPhotoId(final long userId, final String photoId) {
        return Specifications.where(buildActiveByUser(userId)).and(buildByPhotoId(photoId));
    }

    public Specification<Item> buildActive(final boolean active) {
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
