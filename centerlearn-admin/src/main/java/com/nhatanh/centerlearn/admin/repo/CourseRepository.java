package com.nhatanh.centerlearn.admin.repo;

import com.nhatanh.centerlearn.common.entity.Course;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

@EzyRepository
public interface CourseRepository extends EzyDatabaseRepository<Long, Course> {

}
