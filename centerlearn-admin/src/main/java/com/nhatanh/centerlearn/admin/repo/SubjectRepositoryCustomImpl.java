package com.nhatanh.centerlearn.admin.repo;

import com.nhatanh.centerlearn.admin.filter.SubjectFilterCriteria;
import com.nhatanh.centerlearn.common.entity.Subject;
import com.nhatanh.centerlearn.common.enums.AccountStatus;
import com.nhatanh.centerlearn.common.utils.DateFormatter;
import com.tvd12.ezydata.jpa.repository.EzyJpaRepository;
import com.tvd12.ezyfox.util.Next;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubjectRepositoryCustomImpl extends EzyJpaRepository<Long, Subject> implements SubjectRepositoryCustom{
    @Override
    public List<Subject> findSubjectByCriteria(SubjectFilterCriteria criteria, Next next) {
        EntityManager entityManager = databaseContext.createEntityManager();
        StringBuilder jpql = new StringBuilder("SELECT s FROM Subject s ");

        jpql.append("WHERE 1=1 ");

        Map<String, Object> parameters = new HashMap<>();

        if (criteria.getName() != null) {
            jpql.append("AND s.name LIKE :name ");
            parameters.put("name", "%" + criteria.getName() + "%");
        }
        if (criteria.getDisplayName() != null) {
            jpql.append("AND s.displayName LIKE :displayName ");
            parameters.put("displayName", "%" + criteria.getDisplayName() + "%");
        }
        if (criteria.getStatus() != null) {
            jpql.append("AND s.status = :status ");
            parameters.put("status", criteria.getStatus());
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
    public long countSubjectByCriteria(SubjectFilterCriteria criteria) {
        EntityManager entityManager = databaseContext.createEntityManager();
        StringBuilder jpql = new StringBuilder("SELECT COUNT(s) FROM Subject s ");

        jpql.append("WHERE 1=1 ");

        Map<String, Object> parameters = new HashMap<>();

        if (criteria.getName() != null) {
            jpql.append("AND s.name LIKE :name ");
            parameters.put("name", "%" + criteria.getName() + "%");
        }
        if (criteria.getDisplayName() != null) {
            jpql.append("AND s.displayName LIKE :displayName ");
            parameters.put("displayName", "%" + criteria.getDisplayName() + "%");
        }
        if (criteria.getStatus() != null) {
            jpql.append("AND s.status = :status ");
            parameters.put("status", criteria.getStatus());
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
