package com.nhatanh.centerlearn.admin.repo;

import com.nhatanh.centerlearn.admin.filter.AccountFilterCriteria;
import com.nhatanh.centerlearn.common.entity.Account;
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

public class AccountRepositoryCustomImpl extends EzyJpaRepository<Long, Account> implements AccountRepositoryCustom{

    @Override
    public List<Account> findAccountByCriteria(AccountFilterCriteria criteria, Next next) {
        EntityManager entityManager = databaseContext.createEntityManager();
        StringBuilder jpql = new StringBuilder("SELECT a FROM Account a ");

        if (criteria.getRoleId() > 0) {
            jpql.append("INNER JOIN AccountRole r ON a.id = r.accountId ");
        } else {
            jpql.append("WHERE 1=1 ");
        }

        Map<String, Object> parameters = new HashMap<>();

        if (criteria.getUsername() != null) {
            jpql.append("AND a.username LIKE :username ");
            parameters.put("username", "%" + criteria.getUsername() + "%");
        }

        if (criteria.getDisplayName() != null) {
            jpql.append("AND a.displayName LIKE :displayName ");
            parameters.put("displayName", "%" + criteria.getDisplayName() + "%");
        }

        if (criteria.getEmail() != null) {
            jpql.append("AND a.email LIKE :email ");
            parameters.put("email", "%" + criteria.getEmail() + "%");
        }

        if (criteria.getPhone() != null) {
            jpql.append("AND a.phone LIKE :phone ");
            parameters.put("phone", "%" + criteria.getPhone() + "%");
        }

        if (criteria.getStatus() > 0) {
            jpql.append("AND a.status = :status ");
            parameters.put("status", AccountStatus.getNameByCode(criteria.getStatus()));
        }

        if (criteria.getStartDate() != null) {
            jpql.append("AND a.createdAt >= :dateStart ");
            parameters.put("dateStart", DateFormatter.toDate(criteria.getStartDate()));
        }

        if (criteria.getEndDate() != null) {
            jpql.append("AND a.createdAt <= :dateEnd ");
            parameters.put("dateEnd", DateFormatter.toDate(criteria.getEndDate()));
        }

        if (criteria.getRoleId() > 0) {
            jpql.append("AND r.roleId = :roleId ");
            parameters.put("roleId", criteria.getRoleId());
        }

        if (criteria.getId() > 0) {
            jpql.append("AND a.id = :id");
            parameters.put("id", criteria.getId());
        }

        Query query = entityManager.createQuery(jpql.toString());
        parameters.forEach(query::setParameter);

        query.setFirstResult((int) next.getSkip());
        query.setMaxResults((int) next.getLimit());

        return query.getResultList();
    }

    @Override
    public long countAccountByCriteria(AccountFilterCriteria criteria) {
        EntityManager entityManager = databaseContext.createEntityManager();
        StringBuilder jpql = new StringBuilder("SELECT COUNT(a) FROM Account a ");

        if (criteria.getRoleId() > 0) {
            jpql.append("INNER JOIN AccountRole r ON a.id = r.accountId ");
        } else {
            jpql.append("WHERE 1=1 ");
        }

        Map<String, Object> parameters = new HashMap<>();

        if (criteria.getUsername() != null) {
            jpql.append("AND a.username LIKE :username ");
            parameters.put("username", "%" + criteria.getUsername() + "%");
        }

        if (criteria.getDisplayName() != null) {
            jpql.append("AND a.displayName LIKE :displayName ");
            parameters.put("displayName", "%" + criteria.getDisplayName() + "%");
        }

        if (criteria.getEmail() != null) {
            jpql.append("AND a.email LIKE :email ");
            parameters.put("email", "%" + criteria.getEmail() + "%");
        }

        if (criteria.getPhone() != null) {
            jpql.append("AND a.phone LIKE :phone ");
            parameters.put("phone", "%" + criteria.getPhone() + "%");
        }

        if (criteria.getStatus() > 0) {
            jpql.append("AND a.status = :status ");
            parameters.put("status", AccountStatus.getNameByCode(criteria.getStatus()));
        }

        if (criteria.getStartDate() != null) {
            jpql.append("AND a.createdAt >= :dateStart ");
            parameters.put("dateStart", DateFormatter.toDate(criteria.getStartDate()));
        }

        if (criteria.getEndDate() != null) {
            jpql.append("AND a.createdAt <= :dateEnd ");
            parameters.put("dateEnd", DateFormatter.toDate(criteria.getEndDate()));
        }

        if (criteria.getRoleId() > 0) {
            jpql.append("AND r.roleId = :roleId ");
            parameters.put("roleId", criteria.getRoleId());
        }

        if (criteria.getId() > 0) {
            jpql.append("AND a.id = :id");
            parameters.put("id", criteria.getId());
        }

        TypedQuery<Long> query = entityManager.createQuery(jpql.toString(), Long.class);
        parameters.forEach(query::setParameter);

        return query.getSingleResult();
    }
}
