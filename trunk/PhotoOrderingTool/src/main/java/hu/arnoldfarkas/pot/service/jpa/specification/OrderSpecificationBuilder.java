package hu.arnoldfarkas.pot.service.jpa.specification;

import hu.arnoldfarkas.pot.domain.Order;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

public class OrderSpecificationBuilder {

    public Specification<Order> activeByUser(final long userId, final boolean active) {
        return Specifications.where(active(active)).and(byUser(userId));
    }

    public Specification<Order> activeByUser(final long userId) {
        return activeByUser(userId, true);
    }

    public Specification<Order> byUser(final long userId) {
        return new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("user").get("id").as(Long.class), new Long(userId));
            }
        };
    }

    public Specification<Order> active() {
        return active(true);
    }

    public Specification<Order> active(final boolean active) {
        return new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (active) {
                    return cb.isNull(root.get("closingDate"));
                } else {
                    return cb.isNotNull(root.get("closingDate"));
                }

            }
        };
    }
}