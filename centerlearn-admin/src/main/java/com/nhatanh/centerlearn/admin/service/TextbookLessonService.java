package com.nhatanh.centerlearn.admin.service;

import com.nhatanh.centerlearn.admin.converter.AdminEntityToModelConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToEntityConverter;
import com.nhatanh.centerlearn.admin.model.TextbookLessonModel;
import com.nhatanh.centerlearn.admin.repo.TextbookLessonRepository;
import com.nhatanh.centerlearn.common.entity.TextbookLesson;
import com.nhatanh.centerlearn.common.entity.TextbookLessonId;
import com.nhatanh.centerlearn.common.exception.FailedCreationException;
import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TextbookLessonService {
    private final TextbookLessonRepository textbookLessonRepository;
    private final AdminModelToEntityConverter adminModelToEntityConverter;
    private final AdminEntityToModelConverter adminEntityToModelConverter;

    public TextbookLessonId addTextbookLesson(TextbookLessonModel model) {
        TextbookLesson textbookLesson = this.adminModelToEntityConverter.toTextbookLessonEntity(model);
        this.textbookLessonRepository.save(textbookLesson);
        if (textbookLesson.getTextbookId() == 0 || textbookLesson.getLessonId() == 0) {
            throw new FailedCreationException("Fail to create TextbookLesson");
        }
        return new TextbookLessonId(
            textbookLesson.getTextbookId(),
            textbookLesson.getLessonId()
        );
    }

    public TextbookLessonModel getTextbookLessonByTextbookIdAndLessonId(long textbookId, long lessonId) {
        TextbookLesson textbookLesson = this.textbookLessonRepository.findTextbookLessonByTextbookIdAndLessonId(textbookId, lessonId);
        return textbookLesson == null ? null : this.adminEntityToModelConverter.toTextbookLessonModel(textbookLesson);
    }

    public void deleteLessonForTextbook(TextbookLessonId textbookLessonId) {
        TextbookLesson textbookLesson = this.textbookLessonRepository.findById(textbookLessonId);
        if (textbookLesson == null) {
            throw new HttpNotFoundException("TextbookLesson Not Found");
        }
        this.textbookLessonRepository.delete(textbookLessonId);
    }
}
