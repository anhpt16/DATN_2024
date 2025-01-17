package com.nhatanh.centerlearn.web.service;

import com.nhatanh.centerlearn.common.entity.Subject;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.web.converter.WebEntityToModelConverter;
import com.nhatanh.centerlearn.web.model.SubjectModel;
import com.nhatanh.centerlearn.web.repo.SubjectRepository;
import com.tvd12.ezyfox.io.EzyMaps;
import com.tvd12.ezyfox.util.Next;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final WebEntityToModelConverter webEntityToModelConverter;

    public Map<Long, String> getSubjectNameMapByIds(Collection<Long> ids) {
        return EzyMaps.newHashMapNewValues(this.getSubjectMapByIds(ids), SubjectModel::getDisplayName);
    }

    public Map<Long, SubjectModel> getSubjectMapByIds(Collection<Long> ids) {
        return ids.isEmpty() ? Collections.emptyMap() : this.subjectRepository.findListByIds(ids).stream()
            .collect(Collectors.toMap(Subject::getId, this.webEntityToModelConverter::toModel, (o, n) -> n));
    }
}
