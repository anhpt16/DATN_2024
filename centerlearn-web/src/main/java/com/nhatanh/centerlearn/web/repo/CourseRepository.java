package com.nhatanh.centerlearn.web.repo;

import com.nhatanh.centerlearn.common.entity.Course;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

@EzyRepository
public interface CourseRepository extends EzyDatabaseRepository<Long, Course> {

    @EzyQuery("SELECT c FROM Course c WHERE c.slug = ?0 ")
    Course findBySlug(String slug);

    @EzyQuery("SELECT c FROM Course c WHERE c.code = ?0")
    Course findByCode(String code);
}
