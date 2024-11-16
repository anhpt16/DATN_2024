package com.nhatanh.centerlearn.admin.repo;

import com.nhatanh.centerlearn.admin.result.IdResult;
import com.nhatanh.centerlearn.common.entity.Room;
import com.nhatanh.centerlearn.common.entity.Term;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.ezyfox.util.Next;

import java.util.Collection;
import java.util.List;

@EzyRepository
public interface RoomRepository extends EzyDatabaseRepository<Long, Room> {
    List<Room> findAllRoom(Next next);
    List<Room> findByTermId(long id, Next next);
    long countByTermId(long id);
    Room findById(long id);

    @EzyQuery("SELECT r.termId FROM Room r WHERE r.id = ?0")
    IdResult findTermIdByRoomId(long id);
}
