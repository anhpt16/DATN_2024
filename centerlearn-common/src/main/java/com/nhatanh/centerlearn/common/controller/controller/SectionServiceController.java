package com.nhatanh.centerlearn.common.controller.controller;

import com.nhatanh.centerlearn.common.controller.decorator.SectionModelDecorator;
import com.nhatanh.centerlearn.common.enums.ExerciseStatus;
import com.nhatanh.centerlearn.common.enums.SectionStatus;
import com.nhatanh.centerlearn.common.model.SaveSectionModel;
import com.nhatanh.centerlearn.common.model.SectionModel;
import com.nhatanh.centerlearn.common.response.SectionResponse;
import com.nhatanh.centerlearn.common.service.SectionService;
import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class SectionServiceController {
    private final SectionService sectionService;
    private final SectionModelDecorator sectionModelDecorator;

    public SectionResponse getSectionById(long sectionId) {
        SectionModel sectionModel = this.sectionService.getSectionById(sectionId);
        if (sectionModel == null) {
            throw new HttpNotFoundException("Section Not Found");
        }
        return this.sectionModelDecorator.decorateSectionModel(sectionModel);
    }

    public void updateSection(SaveSectionModel model) {
        this.sectionService.updateSection(model);
    }

    public void deleteSection(long sectionId) {
        this.sectionService.deleteSection(sectionId);
    }

    public List<SectionStatus> getSectionStatuses() {
        return Arrays.asList(SectionStatus.values());
    }
}
