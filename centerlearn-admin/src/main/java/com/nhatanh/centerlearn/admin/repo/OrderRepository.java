package com.nhatanh.centerlearn.admin.repo;

import com.nhatanh.centerlearn.common.entity.Order;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

@EzyRepository
public interface OrderRepository extends EzyDatabaseRepository<Long, Order> {

}
