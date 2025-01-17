package com.nhatanh.centerlearn.web.repo;

import com.nhatanh.centerlearn.common.entity.Order;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

import java.util.List;

@EzyRepository
public interface OrderRepository extends EzyDatabaseRepository<Long, Order> {

    @EzyQuery("SELECT o FROM Order o WHERE o.accountId = ?0 AND o.courseId = ?1 AND o.status = ?2")
    Order findOrderComplete(long accountId, long courseId, String status);

    @EzyQuery("SELECT o FROM Order o WHERE o.accountId = ?0 AND o.status = ?1 ")
    List<Order> findOrdersByAccountIdAndStatus(long accountId, String status);
}
