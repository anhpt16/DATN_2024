package com.nhatanh.centerlearn.common.controller.decorator;

import com.nhatanh.centerlearn.common.converter.ModelToResponseConverter;
import com.nhatanh.centerlearn.common.model.AccountModel;
import com.nhatanh.centerlearn.common.model.LessonModel;
import com.nhatanh.centerlearn.common.model.SectionModel;
import com.nhatanh.centerlearn.common.response.SectionResponse;
import com.nhatanh.centerlearn.common.service.AccountService;
import com.nhatanh.centerlearn.common.service.LessonService;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

@EzySingleton
@AllArgsConstructor
public class SectionModelDecorator {
    private final AccountService accountService;
    private final LessonService lessonService;
    private final ModelToResponseConverter modelToResponseConverter;

    public SectionResponse decorateSectionModel(SectionModel model) {
        String creatorName = "";
        if (model.getCreatorId() > 0) {
            AccountModel accountModel = this.accountService.getAccountById(model.getCreatorId());
            if (accountModel != null) {
                creatorName = accountModel.getDisplayName();
            }
        }
        String lessonName = "";
        if (model.getLessonId() > 0) {
            LessonModel lessonModel = this.lessonService.getLessonById(model.getLessonId());
            if (lessonModel != null) {
                lessonName = lessonModel.getTitle();
            }
        }
        return modelToResponseConverter.toSectionResponse(model, creatorName, lessonName);
    }
}
