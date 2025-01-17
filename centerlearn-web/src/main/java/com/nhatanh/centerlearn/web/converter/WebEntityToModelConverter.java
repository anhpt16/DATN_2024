package com.nhatanh.centerlearn.web.converter;

import com.nhatanh.centerlearn.common.entity.*;
import com.nhatanh.centerlearn.web.model.*;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

@EzySingleton
@AllArgsConstructor
public class WebEntityToModelConverter {

    public AccountModel toModel(Account account) {
        if (account == null) {
            return null;
        } else {
            return AccountModel.builder()
                .id(account.getId())
                .username(account.getUsername())
                .password(account.getPassword())
                .displayName(account.getDisplayName())
                .email(account.getEmail())
                .phone(account.getPhone())
                .creatorId(account.getCreatorId())
                .avatarId(account.getAvatarImageId())
                .status(account.getStatus())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .build();
        }
    }

    public CourseModel toCourseModel(Course course) {
        if (course == null) {
            return null;
        }
        return CourseModel.builder()
            .id(course.getId())
            .code(course.getCode())
            .displayName(course.getDisplayName())
            .courseType(course.getCourseType())
            .duration(course.getDuration())
            .description(course.getDescription())
            .status(course.getStatus())
            .createdAt(course.getCreatedAt())
            .updatedAt(course.getUpdatedAt())
            .creatorId(course.getCreatorId())
            .imageId(course.getImageId())
            .slug(course.getSlug())
            .price(course.getPrice())
            .managerId(course.getManagerId())
            .build();
    }

    public CourseSubjectModel toCourseSubjectModel(CourseSubject courseSubject) {
        if (courseSubject == null) {
            return null;
        }
        return CourseSubjectModel.builder()
            .id(courseSubject.getId())
            .courseId(courseSubject.getCourseId())
            .subjectId(courseSubject.getSubjectId())
            .textbookId(courseSubject.getTextbookId())
            .build();
    }

    public SubjectModel toModel(Subject subject) {
        if (subject == null) {
            return null;
        } else {
            return SubjectModel.builder()
                .id(subject.getId())
                .name(subject.getName())
                .displayName(subject.getDisplayName())
                .description(subject.getDescription())
                .status(subject.getStatus())
                .createdAt(subject.getCreatedAt())
                .updatedAt(subject.getUpdatedAt())
                .imageId(subject.getImageId())
                .slug(subject.getSlug())
                .build();
        }
    }

    public TextbookLessonModel toTextbookLessonModel(TextbookLesson textbookLesson) {
        if (textbookLesson == null) {
            return null;
        }
        return TextbookLessonModel.builder()
            .lessonId(textbookLesson.getLessonId())
            .textbookId(textbookLesson.getTextbookId())
            .priority(textbookLesson.getPriority())
            .build();
    }

    public LessonModel toLessonModel(Lesson lesson) {
        return LessonModel.builder()
            .id(lesson.getId())
            .title(lesson.getTitle())
            .description(lesson.getDescription())
            .build();
    }
}
