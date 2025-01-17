package com.nhatanh.centerlearn.web.repo;

import com.nhatanh.centerlearn.common.entity.TextbookLesson;
import com.nhatanh.centerlearn.common.entity.TextbookLessonId;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

import java.util.List;

@EzyRepository
public interface TextbookLessonRepository extends EzyDatabaseRepository<TextbookLessonId, TextbookLesson> {

    @EzyQuery("SELECT t FROM TextbookLesson t WHERE t.textbookId = ?0 AND t.lessonId = ?1 ")
    TextbookLesson findTextbookLessonByTextbookIdAndLessonId(long textbookId, long lessonId);

    @EzyQuery("SELECT t FROM TextbookLesson t WHERE t.textbookId = ?0 ORDER BY t.priority ASC ")
    List<TextbookLesson> findListByTextbookId(long textbookId);
}
