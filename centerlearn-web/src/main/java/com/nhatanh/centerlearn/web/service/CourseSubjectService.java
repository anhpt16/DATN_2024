package com.nhatanh.centerlearn.web.service;

import com.nhatanh.centerlearn.common.entity.CourseSubject;
import com.nhatanh.centerlearn.common.exception.FailedCreationException;
import com.nhatanh.centerlearn.web.converter.WebEntityToModelConverter;
import com.nhatanh.centerlearn.web.model.CourseSubjectModel;
import com.nhatanh.centerlearn.web.repo.CourseSubjectRepository;
import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class CourseSubjectService {
    private final CourseSubjectRepository courseSubjectRepository;
    private final WebEntityToModelConverter webEntityToModelConverter;

    public List<CourseSubjectModel> getCourseSubjectsByCourseId(long courseId) {
        List<CourseSubject> courseSubjects = this.courseSubjectRepository.findCourseSubjectsByCourseId(courseId);
        if (courseSubjects == null || courseSubjects.isEmpty()) {
            return Collections.emptyList();
        }
        return newArrayList(courseSubjects, this.webEntityToModelConverter::toCourseSubjectModel);
    }

    public CourseSubjectModel getCourseSubjectByCourseIdAndSubjectId(long courseId, long subjectId) {
        CourseSubject courseSubject = this.courseSubjectRepository.findCourseSubjectByCourseIdAndSubjectId(courseId, subjectId);
        if (courseSubject == null) {
            return null;
        }
        return this.webEntityToModelConverter.toCourseSubjectModel(courseSubject);
    }

}
