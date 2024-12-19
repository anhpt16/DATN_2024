package com.nhatanh.centerlearn.common.converter;

import com.nhatanh.centerlearn.common.entity.*;
import com.nhatanh.centerlearn.common.model.*;
import com.nhatanh.centerlearn.common.result.IdResult;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@EzySingleton
@AllArgsConstructor
public class EntityToModelConverter {

    public AccountRoleModel toModel(AccountRole accountRole) {
        if (accountRole == null) {
            return null;
        }
        return AccountRoleModel.builder()
            .accountId(accountRole.getAccountId())
            .roleId(accountRole.getRoleId())
            .build();
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

    public GalleryModel toGalleryModel(Media media) {
        if (media == null) {
            return null;
        }
        return GalleryModel.builder()
            .id(media.getId())
            .name(media.getName())
            .url(media.getUrl())
            .description(media.getDescription())
            .createdAt(media.getCreatedAt())
            .updatedAt(media.getUpdatedAt())
            .fileSize(media.getFileSize())
            .build();
    }

    public List<GalleryModel> toGalleryListModel(List<Media> medias) {
        if (medias.isEmpty()) {
            return Collections.emptyList();
        }
        return newArrayList(medias, this::toGalleryModel);
    }

    public UserTermModel toUserTermModel(UserTerm userTerm) {
        if (userTerm == null) {
            return null;
        }
        return UserTermModel.builder()
            .id(userTerm.getId())
            .creatorId(userTerm.getCreatorId())
            .term(userTerm.getTerm())
            .build();
    }

    public LessonModel toLessonModel(Lesson lesson) {
        if (lesson == null) {
            return null;
        }
        return LessonModel.builder()
            .id(lesson.getId())
            .title(lesson.getTitle())
            .description(lesson.getDescription())
            .creatorId(lesson.getCreatorId())
            .status(lesson.getStatus())
            .note(lesson.getNote())
            .createdAt(lesson.getCreatedAt())
            .updatedAt(lesson.getUpdatedAt())
            .userTermId(lesson.getUserTermId())
            .build();
    }

    public ExerciseModel toExerciseModel(Exercise exercise) {
        if (exercise == null) {
            return null;
        }
        return ExerciseModel.builder()
            .id(exercise.getId())
            .title(exercise.getTitle())
            .content(exercise.getContent())
            .creatorId(exercise.getCreatorId())
            .status(exercise.getStatus())
            .createdAt(exercise.getCreatedAt())
            .updatedAt(exercise.getUpdatedAt())
            .userTermId(exercise.getUserTermId())
            .build();
    }

    public LessonExerciseModel toLessonExerciseModel(LessonExercise lessonExercise) {
        if (lessonExercise == null) {
            return null;
        }
        return LessonExerciseModel.builder()
            .lessonId(lessonExercise.getLessonId())
            .exerciseId(lessonExercise.getExerciseId())
            .priority(lessonExercise.getPriority())
            .build();
    }

    public SectionModel toSectionModel(Section section) {
        if (section == null) {
            return null;
        }
        return SectionModel.builder()
            .id(section.getId())
            .title(section.getTitle())
            .content(section.getContent())
            .creatorId(section.getCreatorId())
            .status(section.getStatus())
            .createdAt(section.getCreatedAt())
            .updatedAt(section.getUpdatedAt())
            .lessonId(section.getLessonId())
            .priority(section.getPriority())
            .build();
    }
}
