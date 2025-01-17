package com.nhatanh.centerlearn.admin.service;

import com.nhatanh.centerlearn.admin.converter.AdminEntityToModelConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToEntityConverter;
import com.nhatanh.centerlearn.admin.filter.CourseFilterCriteria;
import com.nhatanh.centerlearn.admin.model.AddCourseModel;
import com.nhatanh.centerlearn.admin.model.CourseModel;
import com.nhatanh.centerlearn.admin.model.TextbookModel;
import com.nhatanh.centerlearn.admin.model.UpdateCourseModel;
import com.nhatanh.centerlearn.admin.repo.CourseRepository;
import com.nhatanh.centerlearn.admin.repo.CourseRepositoryCustom;
import com.nhatanh.centerlearn.common.entity.Course;
import com.nhatanh.centerlearn.common.exception.FailedCreationException;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyfox.util.Next;
import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.List;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseRepositoryCustom courseRepositoryCustom;
    private final AdminModelToEntityConverter adminModelToEntityConverter;
    private final AdminEntityToModelConverter adminEntityToModelConverter;

    public long addCourse(AddCourseModel model) {
        Course course = this.adminModelToEntityConverter.toCourseEntity(model);
        this.courseRepository.save(course);
        if (course.getId() == 0) {
            throw new FailedCreationException("Fail to create Course");
        }
        return course.getId();
    }

    public void updateCourse(UpdateCourseModel model) {
        Course course = this.courseRepository.findById(model.getId());
        if (course == null) {
            throw new HttpNotFoundException("Course Not Found");
        }
        this.adminModelToEntityConverter.mergeToSaveEntity(course, model);
        this.courseRepository.save(course);
    }

    public CourseModel getCourseById(long id) {
        Course course = this.courseRepository.findById(id);
        if (course == null) {
            throw new HttpNotFoundException("Course Not Found");
        }
        return this.adminEntityToModelConverter.toCourseModel(course);
    }

    public PaginationModel<CourseModel> getCoursePagination(CourseFilterCriteria criteria, int page, int size) {
        long totalPage = (long) Math.ceil((double) this.courseRepositoryCustom.countCourseByCriteria(criteria) / size);
        if (page > totalPage) {
            throw new ResourceNotFoundException("page", "invalid");
        }
        List<CourseModel> courseModels = newArrayList(this.courseRepositoryCustom.findCourseByCriteria(criteria, Next.fromPageSize(page, size)), adminEntityToModelConverter::toCourseModel);
        return PaginationModel.<CourseModel>builder()
            .items(courseModels)
            .currentPage(page)
            .totalPage(totalPage)
            .build();
    }

    public void updateManager(long courseId, long managerId) {
        Course course = this.courseRepository.findById(courseId);
        if (course == null) {
            throw new HttpNotFoundException("Course Not Found");
        }
        this.adminModelToEntityConverter.mergeToSaveEntity(course, managerId);
        this.courseRepository.save(course);
    }
}
