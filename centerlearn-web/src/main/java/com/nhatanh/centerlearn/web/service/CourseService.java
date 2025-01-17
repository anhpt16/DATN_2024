package com.nhatanh.centerlearn.web.service;

import com.nhatanh.centerlearn.common.entity.Course;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.web.converter.WebEntityToModelConverter;
import com.nhatanh.centerlearn.web.filter.CourseFilterCriteria;
import com.nhatanh.centerlearn.web.model.CourseModel;
import com.nhatanh.centerlearn.web.repo.CourseRepository;
import com.nhatanh.centerlearn.web.repo.CourseRepositoryCustom;
import com.tvd12.ezyfox.util.Next;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseRepositoryCustom courseRepositoryCustom;
    private final WebEntityToModelConverter webEntityToModelConverter;

    public PaginationModel<CourseModel> getCoursePagination(CourseFilterCriteria criteria, int page, int size) {
        long totalPage = (long) Math.ceil((double) this.courseRepositoryCustom.countCourseByCriteria(criteria) / size);
        if (page > totalPage) {
            throw new ResourceNotFoundException("page", "invalid");
        }
        List<CourseModel> courseModels = newArrayList(this.courseRepositoryCustom.findCourseByCriteria(criteria, Next.fromPageSize(page, size)), webEntityToModelConverter::toCourseModel);
        return PaginationModel.<CourseModel>builder()
            .items(courseModels)
            .currentPage(page)
            .totalPage(totalPage)
            .build();
    }

    public CourseModel getCourseBySlug(String slug) {
        Course course = this.courseRepository.findBySlug(slug);
        if (course == null) {
            return null;
        }
        return this.webEntityToModelConverter.toCourseModel(course);
    }

    public CourseModel getCourseById(long id) {
        Course course = this.courseRepository.findById(id);
        if (course == null) {
            return null;
        }
        return this.webEntityToModelConverter.toCourseModel(course);
    }

    public CourseModel getCourseByCode(String code) {
        Course course = this.courseRepository.findByCode(code);
        if (course == null) {
            return null;
        }
        return this.webEntityToModelConverter.toCourseModel(course);
    }

    public List<CourseModel> getListCourseByIds(Set<Long> ids) {
        List<Course> courses = this.courseRepository.findListByIds(ids);
        if (courses.isEmpty()) {
            return Collections.emptyList();
        }
        return newArrayList(courses, this.webEntityToModelConverter::toCourseModel);
    }
}
