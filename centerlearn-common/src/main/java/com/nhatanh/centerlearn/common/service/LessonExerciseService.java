package com.nhatanh.centerlearn.common.service;

import com.nhatanh.centerlearn.common.converter.EntityToModelConverter;
import com.nhatanh.centerlearn.common.converter.ModelToEntityConverter;
import com.nhatanh.centerlearn.common.entity.LessonExercise;
import com.nhatanh.centerlearn.common.entity.LessonExerciseId;
import com.nhatanh.centerlearn.common.exception.FailedCreationException;
import com.nhatanh.centerlearn.common.model.LessonExerciseModel;
import com.nhatanh.centerlearn.common.model.UpdateExerciseFromLessonModel;
import com.nhatanh.centerlearn.common.repository.LessonExerciseRepository;
import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class LessonExerciseService {
    private final LessonExerciseRepository lessonExerciseRepository;
    private final ModelToEntityConverter modelToEntityConverter;
    private final EntityToModelConverter entityToModelConverter;

    public LessonExerciseId addLessonExercise(LessonExerciseModel lessonExerciseModel) {
        LessonExercise lessonExercise = this.modelToEntityConverter.toLessonExerciseEntityConverter(lessonExerciseModel);
        this.lessonExerciseRepository.save(lessonExercise);
        if (lessonExercise.getLessonId() == 0 || lessonExercise.getExerciseId() == 0) {
            throw new FailedCreationException("Fail to create LessonExercise");
        }
        return new LessonExerciseId(
            lessonExercise.getLessonId(),
            lessonExercise.getExerciseId()
        );
    }

    public LessonExerciseModel getLessonExerciseById(LessonExerciseId id) {
        LessonExercise lessonExercise = this.lessonExerciseRepository.findById(id);
        return lessonExercise == null ? null : this.entityToModelConverter.toLessonExerciseModel(lessonExercise);
    }

    public List<LessonExerciseModel> getLessonExerciseByLessonId(long lessonId) {
        List<LessonExercise> lessonExercises = this.lessonExerciseRepository.findListByLessonId(lessonId);
        if (lessonExercises.isEmpty()) {
            return Collections.emptyList();
        }
        return newArrayList(lessonExercises, this.entityToModelConverter::toLessonExerciseModel);
    }

    public void deleteLessonExerciseById(LessonExerciseId id) {
        LessonExercise lessonExercise = this.lessonExerciseRepository.findById(id);
        if (lessonExercise == null) {
            throw new HttpNotFoundException("LessonExercise not found");
        }
        this.lessonExerciseRepository.delete(id);
    }

    public void updateLessonExercise(UpdateExerciseFromLessonModel model) {
        LessonExerciseId id = new LessonExerciseId(
            model.getLessonId(),
            model.getId()
        );
        LessonExercise lessonExercise = this.lessonExerciseRepository.findById(id);
        if (lessonExercise == null) {
            throw new HttpNotFoundException("LessonExercise not found");
        }
        this.modelToEntityConverter.mergeToSaveEntity(lessonExercise, model.getPriority());
        this.lessonExerciseRepository.save(lessonExercise);
    }
}
