package com.nhatanh.centerlearn.common.controller.decorator;

import com.nhatanh.centerlearn.common.converter.ModelToResponseConverter;
import com.nhatanh.centerlearn.common.model.AccountModel;
import com.nhatanh.centerlearn.common.model.ExerciseModel;
import com.nhatanh.centerlearn.common.response.ExerciseResponse;
import com.nhatanh.centerlearn.common.service.AccountService;
import com.nhatanh.centerlearn.common.service.UserTermService;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

@EzySingleton
@AllArgsConstructor
public class ExerciseModelDecorator {
    private final AccountService accountService;
    private final UserTermService userTermService;
    private final ModelToResponseConverter modelToResponseConverter;

    public ExerciseResponse decorateExerciseModel(ExerciseModel model) {
        String creatorName = "";
        if (model.getCreatorId() > 0) {
            AccountModel accountModel = this.accountService.getAccountById(model.getCreatorId());
            if (accountModel != null) {
                creatorName = accountModel.getDisplayName();
            }
        }
        String userTermName = "";
        if (model.getUserTermId() > 0) {
            String termName = this.userTermService.getUserTermNameById(model.getUserTermId());
            if (termName != null) {
                userTermName = termName;
            }
        }
        return this.modelToResponseConverter.toExerciseResponse(model, creatorName, userTermName);
    }
}
