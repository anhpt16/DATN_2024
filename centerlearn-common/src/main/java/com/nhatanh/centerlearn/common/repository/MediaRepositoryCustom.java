package com.nhatanh.centerlearn.common.repository;

import com.nhatanh.centerlearn.common.entity.Media;
import com.nhatanh.centerlearn.common.filter.MediaFilterCriteria;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.ezyfox.util.Next;

import java.util.List;

@EzyRepository
public interface MediaRepositoryCustom {
    List<Media> findGalleryByCriteria(MediaFilterCriteria mediaFilterCriteria, Next next);
    long countMediaByCriteria(MediaFilterCriteria criteria);
}
