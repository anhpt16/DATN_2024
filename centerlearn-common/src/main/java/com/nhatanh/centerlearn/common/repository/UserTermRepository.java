package com.nhatanh.centerlearn.common.repository;

import com.nhatanh.centerlearn.common.entity.UserTerm;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

@EzyRepository
public interface UserTermRepository extends EzyDatabaseRepository<Long, UserTerm> {

    @EzyQuery("SELECT u FROM UserTerm u WHERE u.userTermId = ?0 AND u.creatorId = ?1 ")
    UserTerm findUserTermByIdAndCreatorId(long userTermId, long creatorId);
}
