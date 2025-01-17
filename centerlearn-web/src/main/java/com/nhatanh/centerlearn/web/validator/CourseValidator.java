package com.nhatanh.centerlearn.web.validator;

import com.nhatanh.centerlearn.common.entity.LessonExerciseId;
import com.nhatanh.centerlearn.common.model.OrderModel;
import com.nhatanh.centerlearn.common.service.LessonExerciseService;
import com.nhatanh.centerlearn.common.service.LessonService;
import com.nhatanh.centerlearn.common.service.SectionService;
import com.nhatanh.centerlearn.common.validator.FormValidator;
import com.nhatanh.centerlearn.web.model.CourseModel;
import com.nhatanh.centerlearn.web.service.CourseService;
import com.nhatanh.centerlearn.web.service.OrderService;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.core.exception.HttpBadRequestException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@EzySingleton
public class CourseValidator {
    private final CourseService courseService;
    private final OrderService orderService;
    private final FormValidator formValidator;
    private final LessonService lessonService;
    private final SectionService sectionService;
    private final LessonExerciseService lessonExerciseService;


    public void validate(String code) {
        if (code == null) {
            throw new HttpBadRequestException("Code Invalid");
        } else {
            if (formValidator.validateBlank(code)) {
                throw new HttpBadRequestException("Code Blank");
            } else {
                if (this.courseService.getCourseByCode(code) == null) {
                    throw new HttpBadRequestException("Course Not Found");
                }
            }
        }
    }

    public void validateDetail(String slug, long accountId) {
        if (slug == null) {
            throw new HttpBadRequestException("Slug Invalid");
        } else {
            if (formValidator.validateBlank(slug)) {
                throw new HttpBadRequestException("Slug Blank");
            } else {
                // TÌm course với slug
                if (this.courseService.getCourseBySlug(slug) == null) {
                    throw new HttpBadRequestException("Slug Not Found");
                }
            }
        }
        if (accountId > 0) {
            CourseModel courseModel = this.courseService.getCourseBySlug(slug);
            List<OrderModel> orderModels = this.orderService.getOrderSuccessByAccountId(accountId);
            boolean exists = orderModels.stream()
                .anyMatch(order -> order.getCourseId() == courseModel.getId());
            if (!exists) {
                throw new HttpBadRequestException("No Permission");
            }
        }
    }

    public void validateCourseAccount(long courseId, long accountId) {
        if (courseId <= 0) {
            throw new HttpBadRequestException("CourseId Invalid");
        } else {
            if (this.courseService.getCourseById(courseId) == null) {
                throw new HttpBadRequestException("Course Not Found");
            }
        }
        if (accountId > 0) {
            CourseModel courseModel = this.courseService.getCourseById(courseId);
            List<OrderModel> orderModels = this.orderService.getOrderSuccessByAccountId(accountId);
            boolean exists = orderModels.stream()
                .anyMatch(order -> order.getCourseId() == courseModel.getId());
            if (!exists) {
                throw new HttpBadRequestException("No Permission");
            }
        }
    }

    public void validateSection(long courseId, long accountId, long lessonId, long sectionId) {
        if (courseId <= 0) {
            throw new HttpBadRequestException("CourseId Invalid");
        } else {
            if (this.courseService.getCourseById(courseId) == null) {
                throw new HttpBadRequestException("Course Not Found");
            }
        }
        if (accountId > 0) {
            CourseModel courseModel = this.courseService.getCourseById(courseId);
            List<OrderModel> orderModels = this.orderService.getOrderSuccessByAccountId(accountId);
            boolean exists = orderModels.stream()
                .anyMatch(order -> order.getCourseId() == courseModel.getId());
            if (!exists) {
                throw new HttpBadRequestException("No Permission");
            }
        }
        if (lessonId <= 0) {
            throw new HttpBadRequestException("LessonId Invalid");
        }
        if (sectionId <= 0) {
            throw new HttpBadRequestException("SectionId Invalid");
        }
        if (this.sectionService.getSectionByIdAndLessonId(sectionId, lessonId) == null) {
            throw new HttpBadRequestException("Section - Lesson Not Found");
        }
    }

    public void validateExercise(long courseId, long accountId, long lessonId, long exerciseId) {
        if (courseId <= 0) {
            throw new HttpBadRequestException("CourseId Invalid");
        } else {
            if (this.courseService.getCourseById(courseId) == null) {
                throw new HttpBadRequestException("Course Not Found");
            }
        }
        if (accountId > 0) {
            CourseModel courseModel = this.courseService.getCourseById(courseId);
            List<OrderModel> orderModels = this.orderService.getOrderSuccessByAccountId(accountId);
            boolean exists = orderModels.stream()
                .anyMatch(order -> order.getCourseId() == courseModel.getId());
            if (!exists) {
                throw new HttpBadRequestException("No Permission");
            }
        }
        if (lessonId <= 0) {
            throw new HttpBadRequestException("LessonId Invalid");
        }
        if (exerciseId <= 0) {
            throw new HttpBadRequestException("SectionId Invalid");
        }
        LessonExerciseId lessonExerciseId = new LessonExerciseId(lessonId, exerciseId);
        if (this.lessonExerciseService.getLessonExerciseById(lessonExerciseId) == null) {
            throw new HttpBadRequestException("Lesson - Exercise Not Found");
        }
    }
}
