package com.nhatanh.centerlearn.admin.repo;

import com.nhatanh.centerlearn.common.entity.CourseSubject;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

import java.util.List;
import java.util.Optional;

@EzyRepository
public interface CourseSubjectRepository extends EzyDatabaseRepository<Long, CourseSubject> {

    @EzyQuery("SELECT c FROM CourseSubject c WHERE c.courseId = ?0 ")
    List<CourseSubject> findCourseSubjectsByCourseId(long courseId);

    @EzyQuery("SELECT c FROM CourseSubject c WHERE c.courseId = ?0 AND c.subjectId = ?1 ")
    CourseSubject findCourseSubjectByCourseIdAndSubjectId(long courseId, long subjectId);
}
