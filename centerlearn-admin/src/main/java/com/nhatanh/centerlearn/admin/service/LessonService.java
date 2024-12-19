package com.nhatanh.centerlearn.admin.service;

import com.nhatanh.centerlearn.admin.repo.LessonRepository;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;


}
