package com.nhatanh.centerlearn.admin.service;

import com.nhatanh.centerlearn.admin.converter.AdminEntityToModelConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToEntityConverter;
import com.nhatanh.centerlearn.admin.filter.TextbookFilterCriteria;
import com.nhatanh.centerlearn.admin.model.AddTextbookModel;
import com.nhatanh.centerlearn.admin.model.SaveTextbookModel;
import com.nhatanh.centerlearn.admin.model.SubjectModel;
import com.nhatanh.centerlearn.admin.model.TextbookModel;
import com.nhatanh.centerlearn.admin.repo.TextbookRepository;
import com.nhatanh.centerlearn.admin.repo.TextbookRepositoryCustom;
import com.nhatanh.centerlearn.admin.result.IdResult;
import com.nhatanh.centerlearn.common.entity.Textbook;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyfox.util.Next;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.List;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class TextbookService {
    private final TextbookRepository textbookRepository;
    private final TextbookRepositoryCustom textbookRepositoryCustom;
    private final AdminModelToEntityConverter adminModelToEntityConverter;
    private final AdminEntityToModelConverter adminEntityToModelConverter;

    public long getTextbookIdByName(String name) {
        IdResult id = this.textbookRepository.findTextbookIdByName(name);
        return id == null ? 0L : id.getId();
    }

    public long addTextbook(AddTextbookModel model) {
        Textbook textbook = this.adminModelToEntityConverter.toTextbookEntityConverter(model);
        this.textbookRepository.save(textbook);
        return textbook.getId();
    }

    public TextbookModel getTextBookById(long id) {
        Textbook textbook = this.textbookRepository.findById(id);
        return textbook == null ? null : this.adminEntityToModelConverter.toModel(textbook);
    }

    public void updateTextbook(SaveTextbookModel model) {
        Textbook textbook = this.textbookRepository.findById(model.getId());
        if (textbook == null) {
            throw new ResourceNotFoundException("Textbook with id: " + model.getId() + " not found");
        }
        this.adminModelToEntityConverter.mergeToSaveEntity(textbook, model);
        this.textbookRepository.save(textbook);
    }

    public PaginationModel<TextbookModel> getTextbookPaginationByFilter(TextbookFilterCriteria criteria, int page, int size) {
        long totalPage = (long) Math.ceil((double) this.textbookRepositoryCustom.countTextbookByCriteria(criteria) / size);
        if (page > totalPage) {
            throw new ResourceNotFoundException("page", "invalid");
        }
        List<TextbookModel> textbookModels = newArrayList(this.textbookRepositoryCustom.findTextbookByCriteria(criteria, Next.fromPageSize(page, size)), adminEntityToModelConverter::toModel);
        return PaginationModel.<TextbookModel>builder()
            .items(textbookModels)
            .currentPage(page)
            .totalPage(totalPage)
            .build();
    }
}
