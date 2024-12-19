package com.nhatanh.centerlearn.common.converter;

import com.nhatanh.centerlearn.common.entity.*;
import com.nhatanh.centerlearn.common.enums.LessonStatus;
import com.nhatanh.centerlearn.common.model.*;
import com.nhatanh.centerlearn.common.utils.ClockProxy;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

@EzySingleton
@AllArgsConstructor
public class ModelToEntityConverter {
    protected final ClockProxy clock;

    public Media toMediaEntityConverter(ImageUploadModel model) {
        Media entity = new Media();
        this.mergeToEntity(model, entity);
        return entity;
    }

    public Lesson toLessonEntityConverter(AddLessonModel model) {
        Lesson entity = new Lesson();
        this.mergeToEntity(entity, model);
        return entity;
    }

    public Section toSectionEntityConverter(AddSectionModel model) {
        Section entity = new Section();
        this.mergeToEntity(entity, model);
        return entity;
    }

    public Exercise toExerciseEntityConverter(AddExerciseModel model) {
        Exercise entity = new Exercise();
        this.mergeToEntity(entity, model);
        return entity;
    }

    public LessonExercise toLessonExerciseEntityConverter(LessonExerciseModel model) {
        LessonExercise entity = new LessonExercise();
        this.mergeToEntity(entity, model);
        return entity;
    }

    public void mergeToSaveEntity(Exercise entity, UpdateExerciseModel model) {
        if (model.getTitle() != null) {
            entity.setTitle(model.getTitle());
        }
        if (model.getContent() != null) {
            entity.setContent(model.getContent());
        }
        if (model.getStatus() != null) {
            entity.setStatus(model.getStatus());
        }
        if (model.getUserTermId() != null) {
            entity.setUserTermId(model.getUserTermId());
        }
        entity.setUpdatedAt(this.clock.nowDateTime());
    }

    public void mergeToSaveEntity(Section entity, SaveSectionModel model) {
        if (model.getTitle() != null) {
            entity.setTitle(model.getTitle());
        }
        if (model.getContent() != null) {
            entity.setContent(model.getContent());
        }
        if (model.getStatus() != null) {
            entity.setStatus(model.getStatus());
        }
        if (model.getLessonId() != null) {
            entity.setLessonId(model.getLessonId());
        }
        if (model.getPriority() != null) {
            entity.setPriority(model.getPriority());
        }
        entity.setUpdatedAt(this.clock.nowDateTime());
    }

    public void mergeToEntity(LessonExercise entity, LessonExerciseModel model) {
        entity.setLessonId(model.getLessonId());
        entity.setExerciseId(model.getExerciseId());
        entity.setPriority(model.getPriority());
    }

    public void mergeToEntity(Exercise entity, AddExerciseModel model) {
        entity.setTitle(model.getTitle());
        entity.setContent(model.getContent());
        entity.setCreatorId(model.getCreatorId());
        entity.setStatus(model.getStatus());
        entity.setUserTermId(model.getUserTermId());
        entity.setCreatedAt(this.clock.nowDateTime());
        entity.setUpdatedAt(this.clock.nowDateTime());
    }

    public void mergeToEntity(Section entity, AddSectionModel model) {
        entity.setTitle(model.getTitle());
        entity.setContent(model.getContent());
        entity.setCreatorId(model.getCreatorId());
        entity.setStatus(model.getStatus());
        entity.setCreatedAt(this.clock.nowDateTime());
        entity.setUpdatedAt(this.clock.nowDateTime());
        entity.setLessonId(model.getLessonId());
        entity.setPriority(model.getPriority());
    }

    public void mergeToSaveEntity(Lesson entity, UpdateLessonModel model) {
        if (model.getTitle() != null) {
            entity.setTitle(model.getTitle());
        }
        if (model.getDescription() != null) {
            entity.setDescription(model.getDescription());
        }
        if (model.getStatus() != null) {
            entity.setStatus(model.getStatus());
        }
        if (model.getNote() != null) {
            entity.setNote(model.getNote());
        }
        if (model.getUserTermId() != null) {
            entity.setUserTermId(model.getUserTermId());
        }
        entity.setUpdatedAt(this.clock.nowDateTime());
    }

    public void mergeToEntity(Lesson entity, AddLessonModel model) {
        entity.setTitle(model.getTitle());
        entity.setDescription(model.getDescription());
        entity.setCreatorId(model.getCreatorId());
        entity.setStatus(LessonStatus.ACTIVE.name());
        entity.setNote(model.getNote());
        entity.setCreatedAt(this.clock.nowDateTime());
        entity.setUpdatedAt(this.clock.nowDateTime());
        entity.setUserTermId(model.getUserTermId());
    }

    public void mergeToEntity(ImageUploadModel model, Media entity) {
        entity.setName(model.getName());
        entity.setUrl(model.getUrl());
        entity.setMediaType(model.getMediaType());
        entity.setOwnerAccountId(model.getOwnerImageId());
        entity.setDescription(model.getDescription());
        entity.setCreatedAt(this.clock.nowDateTime());
        entity.setUpdatedAt(this.clock.nowDateTime());
        entity.setFileSize(model.getFileSize());
    }

    public void mergeToSaveEntity(Media entity, String name) {
        entity.setName(name);
        entity.setUpdatedAt(this.clock.nowDateTime());
    }

    public void mergeToSaveEntity(Account entity, long avatarId) {
        entity.setAvatarImageId(avatarId);
        entity.setUpdatedAt(this.clock.nowDateTime());
    }

    public void mergeToSaveAccountWithDisplayName(Account entity, String displayName) {
        entity.setDisplayName(displayName);
        entity.setUpdatedAt(this.clock.nowDateTime());
    }

    public void mergeToSaveAccountWithPhone(Account entity, String phone) {
        entity.setPhone(phone);
        entity.setUpdatedAt(this.clock.nowDateTime());
    }

    public void mergeToSaveAccountWithEmail(Account entity, String email) {
        entity.setEmail(email);
        entity.setUpdatedAt(this.clock.nowDateTime());
    }
}
