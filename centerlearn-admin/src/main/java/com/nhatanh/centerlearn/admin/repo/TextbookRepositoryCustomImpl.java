package com.nhatanh.centerlearn.admin.repo;

import com.nhatanh.centerlearn.admin.filter.SubjectFilterCriteria;
import com.nhatanh.centerlearn.admin.filter.TextbookFilterCriteria;
import com.nhatanh.centerlearn.common.entity.Subject;
import com.nhatanh.centerlearn.common.entity.Textbook;
import com.tvd12.ezydata.jpa.repository.EzyJpaRepository;
import com.tvd12.ezyfox.util.Next;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextbookRepositoryCustomImpl extends EzyJpaRepository<Long, Textbook> implements TextbookRepositoryCustom{

    @Override
    public List<Textbook> findTextbookByCriteria(TextbookFilterCriteria criteria, Next next) {
        EntityManager entityManager = databaseContext.createEntityManager();
        StringBuilder jpql = new StringBuilder("SELECT t FROM Textbook t ");

        if (criteria.getSubjectId() > 0) {
            jpql.append("INNER JOIN SubjectTextbook s ON t.id = s.textbookId ");
        } else {
            jpql.append("WHERE 1=1 ");
        }

        Map<String, Object> parameters = new HashMap<>();

        if (criteria.getSubjectId() > 0) {
            jpql.append("AND s.subjectId = :subjectId ");
            parameters.put("subjectId", criteria.getSubjectId());
        }
        if (criteria.getId() > 0) {
            jpql.append("AND t.id = :id ");
            parameters.put("id", criteria.getId());
        }
        if (criteria.getName() != null) {
            jpql.append("AND t.name LIKE :name ");
            parameters.put("name", "%" + criteria.getName() + "%");
        }

        if (criteria.getAuthor() != null) {
            jpql.append("AND t.author LIKE :author ");
            parameters.put("author", "%" + criteria.getAuthor() + "%");
        }

        if (criteria.getStatus() != null) {
            jpql.append("AND t.status = :status ");
            parameters.put("status", criteria.getStatus());
        }

        if (criteria.getSortOrder() == 1) {
            jpql.append("ORDER BY t.createdAt DESC ");
        }
        if (criteria.getSortOrder() == 2) {
            jpql.append("ORDER BY t.createdAt ASC ");
        }

        Query query = entityManager.createQuery(jpql.toString());
        parameters.forEach(query::setParameter);

        query.setFirstResult((int) next.getSkip());
        query.setMaxResults((int) next.getLimit());

        return query.getResultList();
    }

    @Override
    public long countTextbookByCriteria(TextbookFilterCriteria criteria) {
        EntityManager entityManager = databaseContext.createEntityManager();
        StringBuilder jpql = new StringBuilder("SELECT COUNT(t) FROM Textbook t ");

        if (criteria.getSubjectId() > 0) {
            jpql.append("INNER JOIN SubjectTextbook s ON t.id = s.textbookId ");
        } else {
            jpql.append("WHERE 1=1 ");
        }

        Map<String, Object> parameters = new HashMap<>();

        if (criteria.getSubjectId() > 0) {
            jpql.append("AND s.subjectId = :subjectId ");
            parameters.put("subjectId", criteria.getSubjectId());
        }
        if (criteria.getId() > 0) {
            jpql.append("AND t.id = :id ");
            parameters.put("id", criteria.getId());
        }
        if (criteria.getName() != null) {
            jpql.append("AND t.name LIKE :name ");
            parameters.put("name", "%" + criteria.getName() + "%");
        }

        if (criteria.getAuthor() != null) {
            jpql.append("AND t.author LIKE :author ");
            parameters.put("author", "%" + criteria.getAuthor() + "%");
        }

        if (criteria.getStatus() != null) {
            jpql.append("AND t.status = :status ");
            parameters.put("status", criteria.getStatus());
        }

        if (criteria.getSortOrder() == 1) {
            jpql.append("ORDER BY t.createdAt DESC ");
        }
        if (criteria.getSortOrder() == 2) {
            jpql.append("ORDER BY t.createdAt ASC ");
        }

        TypedQuery<Long> query = entityManager.createQuery(jpql.toString(), Long.class);
        parameters.forEach(query::setParameter);

        return query.getSingleResult();
    }
}
