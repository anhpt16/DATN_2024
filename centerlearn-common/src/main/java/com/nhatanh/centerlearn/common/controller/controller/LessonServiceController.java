package com.nhatanh.centerlearn.common.controller.controller;

import com.nhatanh.centerlearn.common.converter.ModelToModelConverter;
import com.nhatanh.centerlearn.common.converter.ModelToResponseConverter;
import com.nhatanh.centerlearn.common.entity.LessonExerciseId;
import com.nhatanh.centerlearn.common.exception.FailedCreationException;
import com.nhatanh.centerlearn.common.model.*;
import com.nhatanh.centerlearn.common.response.LessonResponse;
import com.nhatanh.centerlearn.common.service.*;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LessonServiceController {
    private final LessonService lessonService;
    private final SectionService sectionService;
    private final UserTermService userTermService;
    private final AccountService accountService;
    private final ExerciseService exerciseService;
    private final LessonExerciseService lessonExerciseService;
    private final ModelToResponseConverter modelToResponseConverter;
    private final ModelToModelConverter modelToModelConverter;

    public void updateLesson(UpdateLessonModel updateLessonModel) {
        this.lessonService.updateLesson(updateLessonModel);
    }

    public void addLesson(AddLessonModel model) {
        this.lessonService.addLesson(model);
    }

    public LessonResponse getLessonById(long lessonId) {
        LessonModel lessonModel = this.lessonService.getLessonById(lessonId);
        if (lessonModel == null) {
            return null;
        }
        String userTermName = "";
        if (lessonModel.getUserTermId() > 0) {
            String termName = this.userTermService.getUserTermNameById(lessonModel.getUserTermId());
            if (termName != null) {
                userTermName = termName;
            }
        }
        String accountDisplayName = "";
        if (lessonModel.getCreatorId() > 0) {
            AccountModel accountModel = this.accountService.getAccountById(lessonModel.getCreatorId());
            if (accountModel.getDisplayName() != null) {
                accountDisplayName = accountModel.getDisplayName();
            }
        }
        return this.modelToResponseConverter.toLessonResponse(lessonModel, userTermName, accountDisplayName);
    }

    public void addSection(AddSectionModel model) {
        this.sectionService.addSection(model);
    }

    public void addExerciseForLesson(AddExerciseModel model, long lessonId, float priority) {
        long exerciseId = this.exerciseService.addExercise(model);
        if (exerciseId <= 0) {
            throw new FailedCreationException("Fail to create Exercise");
        }
        LessonExerciseModel lessonExerciseModel = this.modelToModelConverter.toLessonExerciseModel(lessonId, exerciseId, priority);
        LessonExerciseId lessonExerciseId = this.lessonExerciseService.addLessonExercise(lessonExerciseModel);
        if (lessonExerciseId == null) {
            throw new FailedCreationException("Fail to create LessonExercise");
        }
    }

    public void addExerciseExistForLesson(LessonExerciseModel model) {
        this.lessonExerciseService.addLessonExercise(model);
    }

    public void deleteLessonExerciseById(LessonExerciseId id) {
        this.lessonExerciseService.deleteLessonExerciseById(id);
    }
}
