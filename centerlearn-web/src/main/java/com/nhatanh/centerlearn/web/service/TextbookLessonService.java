package com.nhatanh.centerlearn.web.service;


import com.nhatanh.centerlearn.common.entity.TextbookLesson;
import com.nhatanh.centerlearn.common.entity.TextbookLessonId;
import com.nhatanh.centerlearn.common.exception.FailedCreationException;
import com.nhatanh.centerlearn.web.converter.WebEntityToModelConverter;
import com.nhatanh.centerlearn.web.model.TextbookLessonModel;
import com.nhatanh.centerlearn.web.repo.TextbookLessonRepository;
import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class TextbookLessonService {
    private final TextbookLessonRepository textbookLessonRepository;
    private final WebEntityToModelConverter webEntityToModelConverter;

    public List<TextbookLessonModel> getTextbookLessonByTextbookId(long textbookId) {
        List<TextbookLesson> textbookLessons = this.textbookLessonRepository.findListByTextbookId(textbookId);
        if (textbookLessons.isEmpty()) {
            return Collections.emptyList();
        }
        return newArrayList(textbookLessons, this.webEntityToModelConverter::toTextbookLessonModel);
    }

    public List<TextbookLessonModel> getTextbookLessonsByTextbookId(long textbookId) {
        List<TextbookLesson> textbookLessons = this.textbookLessonRepository.findListByTextbookId(textbookId);
        if (textbookLessons.isEmpty()) {
            return Collections.emptyList();
        }
        return newArrayList(textbookLessons, this.webEntityToModelConverter::toTextbookLessonModel);
    }

}
