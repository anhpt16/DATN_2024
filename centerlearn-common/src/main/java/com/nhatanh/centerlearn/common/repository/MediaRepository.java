package com.nhatanh.centerlearn.common.repository;

import com.nhatanh.centerlearn.common.entity.Media;
import com.nhatanh.centerlearn.common.entity.Term;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

import java.util.Collection;
import java.util.List;

@EzyRepository
public interface MediaRepository extends EzyDatabaseRepository<Long, Media> {

    List<Media> findListByIds(Collection<Long> ids);

    @EzyQuery("SELECT m FROM Media m WHERE m.ownerAccountId = ?0")
    List<Media> findGalleryByAccountId(long id);

    @EzyQuery("SELECT m FROM Media m WHERE m.ownerAccountId = ?0 AND m.id = ?1")
    Media findGallaryByAccountIdAndId(long accountId, long imageId);


}
