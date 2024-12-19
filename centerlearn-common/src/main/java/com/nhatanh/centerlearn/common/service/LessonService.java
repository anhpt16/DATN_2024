package com.nhatanh.centerlearn.common.service;

import com.nhatanh.centerlearn.common.converter.EntityToModelConverter;
import com.nhatanh.centerlearn.common.converter.ModelToEntityConverter;
import com.nhatanh.centerlearn.common.entity.Lesson;
import com.nhatanh.centerlearn.common.exception.FailedCreationException;
import com.nhatanh.centerlearn.common.model.AddLessonModel;
import com.nhatanh.centerlearn.common.model.LessonModel;
import com.nhatanh.centerlearn.common.model.UpdateLessonModel;
import com.nhatanh.centerlearn.common.repository.LessonRepository;
import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final ModelToEntityConverter modelToEntityConverter;
    private final EntityToModelConverter entityToModelConverter;

    public long addLesson(AddLessonModel model) {
        Lesson lesson = this.modelToEntityConverter.toLessonEntityConverter(model);
        this.lessonRepository.save(lesson);
        if (lesson.getId() == 0) {
            throw new FailedCreationException("Fail to create Lesson");
        }
        return lesson.getId();
    }
    public LessonModel getLessonByIdAndCreatorId(long lessonId, long creatorId) {
        Lesson lesson = this.lessonRepository.findLessonByIdAndCreatorId(lessonId, creatorId);
        return lesson == null ? null : this.entityToModelConverter.toLessonModel(lesson);
    }
    public void updateLesson(UpdateLessonModel lessonModel) {
        Lesson lesson = this.lessonRepository.findById(lessonModel.getId());
        if (lesson == null) {
            throw new HttpNotFoundException("Lesson with id: " + lesson.getId() + " not found");
        }
        this.modelToEntityConverter.mergeToSaveEntity(lesson, lessonModel);
        this.lessonRepository.save(lesson);
    }
    public LessonModel getLessonById(long lessonId) {
        Lesson lesson = this.lessonRepository.findById(lessonId);
        return lesson == null ? null : this.entityToModelConverter.toLessonModel(lesson);
    }
}
