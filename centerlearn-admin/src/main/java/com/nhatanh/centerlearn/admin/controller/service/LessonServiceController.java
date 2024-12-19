package com.nhatanh.centerlearn.admin.controller.service;

import com.nhatanh.centerlearn.admin.converter.AdminModelToModelConverter;
import com.nhatanh.centerlearn.admin.model.TextbookLessonModel;
import com.nhatanh.centerlearn.common.model.UpdateLessonModel;
import com.nhatanh.centerlearn.admin.service.TextbookLessonService;
import com.nhatanh.centerlearn.common.entity.TextbookLessonId;
import com.nhatanh.centerlearn.common.exception.FailedCreationException;
import com.nhatanh.centerlearn.common.model.AddLessonModel;
import com.nhatanh.centerlearn.common.service.LessonService;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LessonServiceController {
    private final LessonService lessonService;
    private final TextbookLessonService textbookLessonService;
    private final AdminModelToModelConverter adminModelToModelConverter;

    // Thêm bài học mới cho giáo trình
    public void addNewLessonForTextbook(AddLessonModel model, long textbookId) {
        // Thêm mới bài học
        long lessonId = this.lessonService.addLesson(model);
        if (lessonId == 0) {
            throw new FailedCreationException("Fail to create Lesson");
        }
        // Thêm bài học cho giáo trình
        TextbookLessonModel textbookLessonModel = this.adminModelToModelConverter.toTextbookLessonModelConverter(textbookId, lessonId, model);
        TextbookLessonId textbookLessonId = this.textbookLessonService.addTextbookLesson(textbookLessonModel);
        if (textbookLessonId == null) {
            throw new FailedCreationException("Fail to create TextbookLesson");
        }
    }

    // Thêm bài học đã có cho giáo trình
    public void addExistLessonForTextbook(long lessonId, long textbookId, float priority) {
        TextbookLessonModel textbookLessonModel = this.adminModelToModelConverter.toTextbookLessonModelConverter(textbookId, lessonId, priority);
        TextbookLessonId textbookLessonId = this.textbookLessonService.addTextbookLesson(textbookLessonModel);
        if (textbookLessonId == null) {
            throw new FailedCreationException("Fail to create TextbookLesson");
        }
    }

    // Xóa một bài học khỏi giáo trình
    public void deleteLessonForTextbook(long lessonId, long textbookId) {
        TextbookLessonId textbookLessonId = new TextbookLessonId(
            lessonId, textbookId
        );
        this.textbookLessonService.deleteLessonForTextbook(textbookLessonId);
    }
}
