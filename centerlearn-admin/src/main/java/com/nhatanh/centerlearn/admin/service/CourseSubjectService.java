package com.nhatanh.centerlearn.admin.service;

import com.nhatanh.centerlearn.admin.converter.AdminEntityToModelConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToEntityConverter;
import com.nhatanh.centerlearn.admin.model.CourseSubjectModel;
import com.nhatanh.centerlearn.admin.repo.CourseSubjectRepository;
import com.nhatanh.centerlearn.common.entity.CourseSubject;
import com.nhatanh.centerlearn.common.exception.FailedCreationException;
import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class CourseSubjectService {
    private final CourseSubjectRepository courseSubjectRepository;
    private final AdminEntityToModelConverter adminEntityToModelConverter;
    private final AdminModelToEntityConverter adminModelToEntityConverter;

    public List<CourseSubjectModel> getCourseSubjectsByCourseId(long courseId) {
        List<CourseSubject> courseSubjects = this.courseSubjectRepository.findCourseSubjectsByCourseId(courseId);
        if (courseSubjects == null || courseSubjects.isEmpty()) {
            return Collections.emptyList();
        }
        return newArrayList(courseSubjects, this.adminEntityToModelConverter::toCourseSubjectModel);
    }

    public CourseSubjectModel getCourseSubjectByCourseIdAndSubjectId(long courseId, long subjectId) {
        CourseSubject courseSubject = this.courseSubjectRepository.findCourseSubjectByCourseIdAndSubjectId(courseId, subjectId);
        if (courseSubject == null) {
            return null;
        }
        return this.adminEntityToModelConverter.toCourseSubjectModel(courseSubject);
    }

    public long addCourseSubject(long courseId, long subjectId, long textbookId) {
        CourseSubject courseSubject = this.adminModelToEntityConverter.toCourseSubjectEntity(courseId, subjectId, textbookId);
        this.courseSubjectRepository.save(courseSubject);
        if (courseSubject.getId() == 0) {
            throw new FailedCreationException("Fail to create CourseSubject");
        }
        return courseSubject.getId();
    }

    public void deleteCourseSubject(long courseId, long subjectId) {
        CourseSubject courseSubject = this.courseSubjectRepository.findCourseSubjectByCourseIdAndSubjectId(courseId, subjectId);
        if (courseSubject == null) {
            throw new HttpNotFoundException("CourseSubject Not Found");
        }
        this.courseSubjectRepository.delete(courseSubject.getId());
    }
}
