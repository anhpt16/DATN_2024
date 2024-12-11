package com.nhatanh.centerlearn.common.repository;

import com.nhatanh.centerlearn.common.entity.Media;
import com.nhatanh.centerlearn.common.filter.MediaFilterCriteria;
import com.tvd12.ezydata.jpa.repository.EzyJpaRepository;
import com.tvd12.ezyfox.util.Next;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaRepositoryCustomImpl extends EzyJpaRepository<Long, Media> implements MediaRepositoryCustom {

    @Override
    public List<Media> findGalleryByCriteria(MediaFilterCriteria criteria, Next next) {
        EntityManager entityManager = databaseContext.createEntityManager();
        StringBuilder jpql = new StringBuilder("SELECT m FROM Media m ");

        Map<String, Object> parameters = new HashMap<>();

        jpql.append("WHERE m.ownerAccountId = :ownerAccountId ");
        parameters.put("ownerAccountId", criteria.getOwnerAccountId());

        if (criteria.getName() != null) {
            jpql.append("AND m.name LIKE :name ");
            parameters.put("name", "%" + criteria.getName() + "%");
        }

        if (criteria.getSortOrder() == 1) {
            jpql.append("ORDER BY createdAt DESC ");
        }
        if (criteria.getSortOrder() == 2) {
            jpql.append("ORDER BY createdAt ASC ");
        }

        Query query = entityManager.createQuery(jpql.toString());
        parameters.forEach(query::setParameter);

        query.setFirstResult((int) next.getSkip());
        query.setMaxResults((int) next.getLimit());

        return query.getResultList();
    }

    @Override
    public long countMediaByCriteria(MediaFilterCriteria criteria) {
        EntityManager entityManager = databaseContext.createEntityManager();
        StringBuilder jpql = new StringBuilder("SELECT COUNT(m) FROM Media m ");

        Map<String, Object> parameters = new HashMap<>();

        jpql.append("WHERE m.ownerAccountId = :ownerAccountId ");
        parameters.put("ownerAccountId", criteria.getOwnerAccountId());

        if (criteria.getName() != null) {
            jpql.append("AND m.name LIKE :name ");
            parameters.put("name", "%" + criteria.getName() + "%");
        }

        if (criteria.getSortOrder() == 1) {
            jpql.append("ORDER BY createdAt DESC ");
        }
        if (criteria.getSortOrder() == 2) {
            jpql.append("ORDER BY createdAt ASC ");
        }

        TypedQuery<Long> query = entityManager.createQuery(jpql.toString(), Long.class);
        parameters.forEach(query::setParameter);

        return query.getSingleResult();
    }
}
