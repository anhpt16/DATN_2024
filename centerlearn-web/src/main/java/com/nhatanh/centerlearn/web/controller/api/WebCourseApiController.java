package com.nhatanh.centerlearn.web.controller.api;

import com.nhatanh.centerlearn.common.controller.controller.ExerciseServiceController;
import com.nhatanh.centerlearn.common.controller.controller.SectionServiceController;
import com.nhatanh.centerlearn.common.response.ExerciseResponse;
import com.nhatanh.centerlearn.common.response.SectionResponse;
import com.nhatanh.centerlearn.common.service.LessonService;
import com.nhatanh.centerlearn.common.service.SectionService;
import com.nhatanh.centerlearn.common.utils.RequestContext;
import com.nhatanh.centerlearn.web.controller.service.CourseServiceController;
import com.nhatanh.centerlearn.web.controller.service.TextbookServiceController;
import com.nhatanh.centerlearn.web.model.CourseModel;
import com.nhatanh.centerlearn.web.response.AllCourseTextbookExercise;
import com.nhatanh.centerlearn.web.response.AllCourseTextbookSection;
import com.nhatanh.centerlearn.web.response.WebCourseDetailResponse;
import com.nhatanh.centerlearn.web.response.WebCourseResponse;
import com.nhatanh.centerlearn.web.validator.CourseValidator;
import com.tvd12.ezyhttp.core.exception.HttpUnauthorizedException;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.Api;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.PathVariable;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Api
@Controller("/api/v1/web/course")
public class WebCourseApiController {
    private final CourseServiceController courseServiceController;
    private final CourseValidator courseValidator;
    private final SectionServiceController sectionServiceController;
    private final ExerciseServiceController exerciseServiceController;
    private final TextbookServiceController textbookServiceController;


    @DoGet("/{slug}")
    public ResponseEntity getCourseDetailBySlug(
        @PathVariable String slug
    ) {
        WebCourseDetailResponse courseDetailResponse = this.courseServiceController.getCourseDetailBySlug(slug);
        if (courseDetailResponse == null) {
            return ResponseEntity.notFound("Course With Slug Not Found");
        }
        return ResponseEntity.ok(courseDetailResponse);
    }

    @DoGet("/{code}")
    public ResponseEntity getCourseByCode(
        @PathVariable String code
    ) {
        this.courseValidator.validate(code);
        WebCourseDetailResponse courseResponse = this.courseServiceController.getCourseByCode(code);
        if (courseResponse == null) {
            return ResponseEntity.notFound("Course Not Found");
        }
        return ResponseEntity.ok(courseResponse);
    }

    @DoGet("/{courseId}/lesson/{lessonId}/section/{sectionId}")
    public ResponseEntity getDetailSection(
        @PathVariable long courseId,
        @PathVariable long lessonId,
        @PathVariable long sectionId
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.courseValidator.validateSection(courseId, accountId, lessonId, sectionId);
        SectionResponse sectionResponse = this.sectionServiceController.getSectionById(sectionId);
        return ResponseEntity.ok(sectionResponse);
    }

    @DoGet("/{courseId}/subjects/sections")
    public List<AllCourseTextbookSection> getSubjectsSections(
        @PathVariable long courseId
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.courseValidator.validateCourseAccount(courseId, accountId);
        CourseModel courseModel = this.courseServiceController.getCourseById(courseId);
        List<AllCourseTextbookSection> allCourseTextbookSections = this.textbookServiceController.getAllCourseWithTextbookSection(courseModel.getSlug());
        if (allCourseTextbookSections.isEmpty()) {
            return Collections.emptyList();
        }
        return allCourseTextbookSections;
    }

    @DoGet("/{courseId}/subjects/exercises")
    public List<AllCourseTextbookExercise> getSubjectsExercises(
        @PathVariable long courseId
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.courseValidator.validateCourseAccount(courseId, accountId);
        CourseModel courseModel = this.courseServiceController.getCourseById(courseId);
        List<AllCourseTextbookExercise> allCourseTextbookExercises = this.textbookServiceController.getAllCourseWithTextbookExercise(courseModel.getSlug());
        if (allCourseTextbookExercises.isEmpty()) {
            return Collections.emptyList();
        }
        return allCourseTextbookExercises;
    }

    @DoGet("/{courseId}/lesson/{lessonId}/exercise/{exerciseId}")
    public ResponseEntity getDetailExercise(
        @PathVariable long courseId,
        @PathVariable long lessonId,
        @PathVariable long exerciseId
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.courseValidator.validateExercise(courseId, accountId, lessonId, exerciseId);
        ExerciseResponse exerciseResponse = this.exerciseServiceController.getExerciseById(exerciseId);
        if (exerciseResponse == null) {
            return ResponseEntity.notFound("Exercise with id:" + exerciseId + " Not Found");
        }
        return ResponseEntity.ok(exerciseResponse);
    }
}
