package com.nhatanh.centerlearn.web.service;

import com.nhatanh.centerlearn.common.entity.Lesson;
import com.nhatanh.centerlearn.web.converter.WebEntityToModelConverter;
import com.nhatanh.centerlearn.web.model.LessonModel;
import com.nhatanh.centerlearn.web.repo.LessonRepository;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final WebEntityToModelConverter webEntityToModelConverter;

    public LessonModel getLessonById(long id) {
        Lesson lesson = this.lessonRepository.findById(id);
        if (lesson == null) {
            return null;
        }
        return webEntityToModelConverter.toLessonModel(lesson);
    }
}
