package com.nhatanh.centerlearn.admin.repo;

import com.nhatanh.centerlearn.admin.filter.AccountFilterCriteria;
import com.nhatanh.centerlearn.admin.filter.CourseFilterCriteria;
import com.nhatanh.centerlearn.common.entity.Account;
import com.nhatanh.centerlearn.common.entity.Course;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.ezyfox.util.Next;

import java.util.List;

@EzyRepository
public interface CourseRepositoryCustom {
    List<Course> findCourseByCriteria(CourseFilterCriteria criteria, Next next);
    long countCourseByCriteria(CourseFilterCriteria criteria);
}
