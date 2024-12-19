package com.nhatanh.centerlearn.common.repository;

import com.nhatanh.centerlearn.common.entity.Section;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

@EzyRepository
public interface SectionRepository extends EzyDatabaseRepository<Long, Section> {

    @EzyQuery("SELECT s FROM Section s WHERE s.id = ?0 AND s.lessonId = ?1")
    Section findSectionByIdAndLessonId(long sectionId, long lessonId);
}
