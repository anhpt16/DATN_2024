package com.nhatanh.centerlearn.web.repo;

import com.nhatanh.centerlearn.common.entity.Course;
import com.nhatanh.centerlearn.web.filter.CourseFilterCriteria;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.ezyfox.util.Next;

import java.util.List;

@EzyRepository
public interface CourseRepositoryCustom {
    List<Course> findCourseByCriteria(CourseFilterCriteria criteria, Next next);
    long countCourseByCriteria(CourseFilterCriteria criteria);
}
