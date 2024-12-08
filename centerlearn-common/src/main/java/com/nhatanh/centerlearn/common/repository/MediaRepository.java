package com.nhatanh.centerlearn.common.repository;

import com.nhatanh.centerlearn.common.entity.Media;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

import java.util.List;

@EzyRepository
public interface MediaRepository extends EzyDatabaseRepository<Long, Media> {

    @EzyQuery("SELECT m FROM Media m WHERE m.ownerAccountId = ?0")
    List<Media> getGalleryByAccountId(long id);

}
