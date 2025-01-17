package com.nhatanh.centerlearn.common.service;

import com.nhatanh.centerlearn.common.repository.CourseRepository;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;


}
