package com.nhatanh.centerlearn.common.service;

import com.nhatanh.centerlearn.common.converter.EntityToModelConverter;
import com.nhatanh.centerlearn.common.converter.ModelToEntityConverter;
import com.nhatanh.centerlearn.common.entity.Section;
import com.nhatanh.centerlearn.common.exception.FailedCreationException;
import com.nhatanh.centerlearn.common.model.AddSectionModel;
import com.nhatanh.centerlearn.common.model.SaveSectionModel;
import com.nhatanh.centerlearn.common.model.SectionModel;
import com.nhatanh.centerlearn.common.repository.SectionRepository;
import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class SectionService {
    private final SectionRepository sectionRepository;
    private final ModelToEntityConverter modelToEntityConverter;
    private final EntityToModelConverter entityToModelConverter;

    public long addSection(AddSectionModel model) {
        Section section = this.modelToEntityConverter.toSectionEntityConverter(model);
        this.sectionRepository.save(section);
        if (section.getId() == 0) {
            throw new FailedCreationException("Fail to create Section");
        }
        return section.getId();
    }

    public SectionModel getSectionByIdAndLessonId(long sectionId, long lessonId) {
        Section section = this.sectionRepository.findSectionByIdAndLessonId(sectionId, lessonId);
        return section == null ? null : this.entityToModelConverter.toSectionModel(section);
    }

    public SectionModel getSectionById(long sectionId) {
        Section section = this.sectionRepository.findById(sectionId);
        return section == null ? null : this.entityToModelConverter.toSectionModel(section);
    }

    public void updateSection(SaveSectionModel model) {
        Section section = this.sectionRepository.findById(model.getId());
        if (section == null) {
            throw new HttpNotFoundException("Section Not Found");
        }
        this.modelToEntityConverter.mergeToSaveEntity(section, model);
        this.sectionRepository.save(section);
    }

    public void deleteSection(long sectionId) {
        Section section = this.sectionRepository.findById(sectionId);
        if (section == null) {
            throw new HttpNotFoundException("Section Not Found");
        }
        this.sectionRepository.delete(sectionId);
    }

    public List<SectionModel> getListByLessonId(long lessonId) {
        List<Section> sections = this.sectionRepository.findListByLessonId(lessonId);
        if (sections.isEmpty()) {
            return Collections.emptyList();
        }
        return newArrayList(sections, this.entityToModelConverter::toSectionModel);
    }
}
