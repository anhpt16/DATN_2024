package com.nhatanh.centerlearn.admin.converter;

import com.nhatanh.centerlearn.admin.model.*;
import com.nhatanh.centerlearn.common.entity.*;
import com.nhatanh.centerlearn.common.enums.MethodName;
import com.nhatanh.centerlearn.common.model.UriModel;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

@EzySingleton
@AllArgsConstructor
public class AdminEntityToModelConverter {

    public TermModel toModel(Term term) {
        if (term == null) {
            return null;
        }
        return TermModel.builder()
            .id(term.getId())
            .name(term.getName())
            .slug(term.getSlug())
            .termType(term.getTermType())
            .parentId(term.getParentId())
            .description(term.getDescription())
            .build();
    }

    public RoomModel toModel(Room room) {
        if (room == null) {
            return null;
        }
        return RoomModel.builder()
            .id(room.getId())
            .name(room.getName())
            .slot(room.getSlot())
            .termId(room.getTermId())
            .description(room.getDescription())
            .status(room.getStatus())
            .createdAt(room.getCreatedAt())
            .updatedAt(room.getUpdatedAt())
            .build();
    }

    public TimeslotModel toModel(Timeslot timeslot) {
        if (timeslot == null) {
            return null;
        }
        return TimeslotModel.builder()
            .id(timeslot.getId())
            .period(timeslot.getPeriod())
            .timeStart(timeslot.getTimeStart())
            .timeEnd(timeslot.getTimeEnd())
            .description(timeslot.getDescription())
            .status(timeslot.getStatus())
            .createdAt(timeslot.getCreatedAt())
            .updatedAt(timeslot.getUpdatedAt())
            .build();
    }

    public RoleModel toModel(Role role) {
        if (role == null) {
            return null;
        }
        return RoleModel.builder()
            .id(role.getId())
            .name(role.getName())
            .displayName(role.getDisplayName())
            .createdAt(role.getCreatedAt())
            .build();
    }

    public UriModel toModel(Permission permission) {
        if (permission == null) {
            return null;
        }
        else {
            return UriModel.builder()
                .uriPath(permission.getFeatureUri())
                .uriMethod(MethodName.fromString(permission.getFeatureMethod()))
                .build();
        }
    }

    public PermissionModel toPermissionModel(Permission permission) {
        if (permission == null) {
            return null;
        }
        else {
            return PermissionModel.builder()
                .roleId(permission.getRoleId())
                .featureUri(permission.getFeatureUri())
                .featureMethod(permission.getFeatureMethod())
                .createdAt(permission.getCreatedAt())
                .build();
        }
    }

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

    public AccountRoleModel toModel(AccountRole accountRole) {
        if (accountRole == null) {
            return null;
        } else {
            return AccountRoleModel.builder()
                .accountId(accountRole.getAccountId())
                .roleId(accountRole.getRoleId())
                .build();
        }
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

    public TextbookModel toModel(Textbook textbook) {
        if (textbook == null) {
            return null;
        } else {
            return TextbookModel.builder()
                .id(textbook.getId())
                .name(textbook.getName())
                .description(textbook.getDescription())
                .author(textbook.getAuthor())
                .status(textbook.getStatus())
                .slug(textbook.getSlug())
                .createdAt(textbook.getCreatedAt())
                .updatedAt(textbook.getUpdatedAt())
                .url(textbook.getUrl())
                .build();
        }
    }

    public SubjectTextbookModel toSubjectTextbookModel(SubjectTextbook subjectTextbook) {
        return SubjectTextbookModel.builder()
            .subjectId(subjectTextbook.getSubjectId())
            .textbookId(subjectTextbook.getTextbookId())
            .build();
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
}
