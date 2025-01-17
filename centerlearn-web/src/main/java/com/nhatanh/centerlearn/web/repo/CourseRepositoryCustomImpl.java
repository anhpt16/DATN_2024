package com.nhatanh.centerlearn.web.repo;

import com.nhatanh.centerlearn.common.entity.Course;
import com.nhatanh.centerlearn.common.enums.CourseStatus;
import com.nhatanh.centerlearn.web.filter.CourseFilterCriteria;
import com.tvd12.ezydata.jpa.repository.EzyJpaRepository;
import com.tvd12.ezyfox.util.Next;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseRepositoryCustomImpl extends EzyJpaRepository<Long, Course> implements CourseRepositoryCustom {
    @Override
    public List<Course> findCourseByCriteria(CourseFilterCriteria criteria, Next next) {
        EntityManager entityManager = databaseContext.createEntityManager();
        StringBuilder jpql = new StringBuilder("SELECT c FROM Course c ");

        jpql.append("WHERE 1=1 ");

        Map<String, Object> parameters = new HashMap<>();

        if (criteria.getKeyword() != null) {
            jpql.append("AND c.displayName LIKE :displayName ");
            parameters.put("displayName", "%" + criteria.getKeyword() + "%");
        }
        jpql.append("AND c.status = :status ");
        parameters.put("status", CourseStatus.ACTIVE.name());
        if (criteria.getCourseType() != null) {
            jpql.append("AND c.courseType = :courseType ");
            parameters.put("courseType", criteria.getCourseType());
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
    public long countCourseByCriteria(CourseFilterCriteria criteria) {
        EntityManager entityManager = databaseContext.createEntityManager();

        StringBuilder jpql = new StringBuilder("SELECT COUNT(c) FROM Course c ");

        jpql.append("WHERE 1=1 ");

        Map<String, Object> parameters = new HashMap<>();

        if (criteria.getKeyword() != null) {
            jpql.append("AND c.displayName LIKE :displayName ");
            parameters.put("displayName", "%" + criteria.getKeyword() + "%");
        }
        jpql.append("AND c.status = :status ");
        parameters.put("status", CourseStatus.ACTIVE.name());
        if (criteria.getCourseType() != null) {
            jpql.append("AND c.courseType = :courseType ");
            parameters.put("courseType", criteria.getCourseType());
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
