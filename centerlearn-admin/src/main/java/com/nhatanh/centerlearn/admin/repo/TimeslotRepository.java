package com.nhatanh.centerlearn.admin.repo;

import com.nhatanh.centerlearn.admin.result.IdResult;
import com.nhatanh.centerlearn.common.entity.Timeslot;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.ezyfox.util.Next;

import java.util.List;

@EzyRepository
public interface TimeslotRepository extends EzyDatabaseRepository<Long, Timeslot> {
    List<Timeslot> getAllTimeslot(Next next);
    Timeslot findById(long id);
}
