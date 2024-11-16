package com.nhatanh.centerlearn.admin.service;


import com.nhatanh.centerlearn.admin.converter.AdminEntityToModelConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToEntityConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToModelConverter;
import com.nhatanh.centerlearn.admin.model.SaveTermModel;
import com.nhatanh.centerlearn.admin.model.TermModel;
import com.nhatanh.centerlearn.admin.repo.TermRepository;
import com.nhatanh.centerlearn.admin.result.IdResult;
import com.nhatanh.centerlearn.admin.result.TermNameResult;
import com.nhatanh.centerlearn.admin.result.TermTypeResult;
import com.nhatanh.centerlearn.common.entity.Term;
import com.nhatanh.centerlearn.common.enums.RoomStatus;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyfox.io.EzyMaps;
import com.tvd12.ezyfox.util.Next;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class TermService {
    private final TermRepository termRepository;
    private final AdminModelToEntityConverter modelToEntityConverter;
    private final AdminModelToModelConverter modelToModelConverter;
    private final AdminEntityToModelConverter entityToModelConverter;

    public void addTerm(SaveTermModel model) {
        this.termRepository.save(this.modelToEntityConverter.toTermEntityConverter(model));
    }

    public void updateTermById(long id, SaveTermModel model) {
        Term entity = this.termRepository.findById(id);
        if (entity == null) {
            throw new ResourceNotFoundException("term");
        } else {
            this.modelToEntityConverter.mergeToEntity(model, entity);
            this.termRepository.save(entity);
        }
    }

    public void deleteTermById(long id) {
        Term entity = this.termRepository.findById(id);
        if (entity == null) {
            throw new ResourceNotFoundException("term");
        } else {
            this.termRepository.delete(id);
        }
    }

    public long getIdByTermNameAndType(String name, String type) {
        IdResult result = termRepository.findIdByTermNameAndType(name, type);
        return result != null ? result.getId() : 0L;
    }

    public List<String> getTypesByTermTypes() {
        return newArrayList(this.termRepository.findAllTermTypes(), TermTypeResult::getType);
    }

    public List<String> getNamesByTermTypes(String keyword) {
        return newArrayList(this.termRepository.findNamesContainingKeyword(keyword), TermNameResult::getName);
    }

    public List<String> getTypesByName(String keyword) {
        return newArrayList(this.termRepository.findTypesByName(keyword), TermNameResult::getName);
    }

    public PaginationModel<TermModel> getAllTermPagination(int page, int size) {
        long totalPage = (long) Math.ceil((double) this.termRepository.count() / size);
        if (page > totalPage) {
            throw new ResourceNotFoundException("page", "invalid");
        }
        List<TermModel> termModels = newArrayList(this.termRepository.findAllTerms(Next.fromPageSize(page, size)), entityToModelConverter::toModel);
        return this.modelToModelConverter.toTermModelPagination(termModels, totalPage, page);
    }

    public Map<Long, TermModel> getTermsMapByIds(Collection<Long> ids) {
        return ids.isEmpty() ? Collections.emptyMap() : this.termRepository.findListByIds(ids).stream()
            .collect(Collectors.toMap(Term::getId, this.entityToModelConverter::toModel, (o, n) -> n));
    }
    public Map<Long, String> getTermNameMapByIds(Collection<Long> ids) {
        return EzyMaps.newHashMapNewValues(this.getTermsMapByIds(ids), TermModel::getName);
    }

    public PaginationModel<TermModel> getTermsByTermTypePagination(String termType, int page, int size) {
        long totalPage = (long) Math.ceil((double) this.termRepository.countByTermType(termType) / size);
        if (page > totalPage) {
            throw new ResourceNotFoundException("page", "invalid");
        }
        List<TermModel> termModels = newArrayList(this.termRepository.findByTermType(termType, Next.fromPageSize(page, size)), entityToModelConverter::toModel);
        return this.modelToModelConverter.toTermModelPagination(termModels, totalPage, page);
    }

    public List<TermModel> getTermsByType(String termType) {
        return newArrayList(this.termRepository.findByTermType(termType), entityToModelConverter::toModel);
    }
}
