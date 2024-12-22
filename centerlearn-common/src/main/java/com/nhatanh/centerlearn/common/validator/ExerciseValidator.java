package com.nhatanh.centerlearn.common.validator;

import com.nhatanh.centerlearn.common.entity.LessonExerciseId;
import com.nhatanh.centerlearn.common.enums.ExerciseStatus;
import com.nhatanh.centerlearn.common.enums.LessonStatus;
import com.nhatanh.centerlearn.common.request.AddExerciseRequest;
import com.nhatanh.centerlearn.common.request.UpdateExerciseFromLesson;
import com.nhatanh.centerlearn.common.request.UpdateExerciseRequest;
import com.nhatanh.centerlearn.common.service.ExerciseService;
import com.nhatanh.centerlearn.common.service.LessonExerciseService;
import com.nhatanh.centerlearn.common.service.LessonService;
import com.nhatanh.centerlearn.common.service.UserTermService;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.core.exception.HttpBadRequestException;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EzySingleton
@AllArgsConstructor
public class ExerciseValidator {
    private final FormValidator formValidator;
    private final UserTermService userTermService;
    private final LessonService lessonService;
    private final ExerciseService exerciseService;
    private final LessonExerciseService lessonExerciseService;

    public void validate(AddExerciseRequest request, long accountId, long lessonId) {
        List<String> errors = new ArrayList<>();

        //validate title
        if (request.getTitle() == null) {
            errors.add("Title Invalid");
        } else {
            if (formValidator.validateBlank(request.getTitle())) {
                errors.add("Title Blank");
            }
        }
        //validate termId
        if (request.getUserTermId() > 0) {
            if (this.userTermService.getUserTermByIdAndCreatorId(request.getUserTermId(), accountId) == null) {
                errors.add("UserTermId - CreatorId not found");
            }
        }
        // validate lesson
        if (lessonId <= 0) {
            errors.add("Lesson Invalid");
        } else {
            if (this.lessonService.getLessonByIdAndCreatorId(lessonId, accountId) == null) {
                errors.add("LessonId - CreatorId not found");
            }
        }

        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validate(AddExerciseRequest request, long accountId) {
        List<String> errors = new ArrayList<>();
        //validate title
        if (request.getTitle() == null) {
            errors.add("Title Invalid");
        } else {
            if (formValidator.validateBlank(request.getTitle())) {
                errors.add("Title Blank");
            }
        }
        //validate termId
        if (request.getUserTermId() > 0) {
            if (this.userTermService.getUserTermByIdAndCreatorId(request.getUserTermId(), accountId) == null) {
                errors.add("UserTermId - CreatorId not found");
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validate(long lessonId, long exerciseId, long accountId) {
        List<String> errors = new ArrayList<>();
        if (lessonId <= 0 ) {
            errors.add("LessonId Invalid");
        } else {
            if (this.lessonService.getLessonByIdAndCreatorId(lessonId, accountId) == null) {
                errors.add("LessonId - CreatorId not found");
            }
        }
        if (exerciseId <= 0) {
            errors.add("ExerciseId Invalid");
        } else {
            if (this.exerciseService.getExerciseByIdAndCreatorId(exerciseId, accountId) == null) {
                errors.add("ExerciseId - CreatorId not found");
            }
        }
        if (lessonId > 0 && exerciseId > 0) {
            if (this.lessonExerciseService.getLessonExerciseById(new LessonExerciseId(lessonId, exerciseId)) != null) {
                errors.add("ExerciseId - LessonId exist");
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validateDelete(long lessonId, long exerciseId, long accountId) {
        List<String> errors = new ArrayList<>();
        if (lessonId <= 0 ) {
            errors.add("LessonId Invalid");
        } else {
            if (this.lessonService.getLessonByIdAndCreatorId(lessonId, accountId) == null) {
                errors.add("LessonId - CreatorId not found");
            }
        }
        if (exerciseId <= 0) {
            errors.add("ExerciseId Invalid");
        } else {
            if (this.exerciseService.getExerciseByIdAndCreatorId(exerciseId, accountId) == null) {
                errors.add("ExerciseId - CreatorId not found");
            }
        }
        if (lessonId > 0 && exerciseId > 0) {
            if (this.lessonExerciseService.getLessonExerciseById(new LessonExerciseId(lessonId, exerciseId)) == null) {
                errors.add("ExerciseId - LessonId not found");
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validate(long exerciseId, long accountId) {
        List<String> errors = new ArrayList<>();
        if (exerciseId <= 0) {
            errors.add("ExerciseId Invalid");
        } else {
            if (this.exerciseService.getExerciseByIdAndCreatorId(exerciseId, accountId) == null) {
                errors.add("ExerciseId - CreatorId not found");
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validate(UpdateExerciseRequest request, long exerciseId, long accountId) {
        List<String> errors = new ArrayList<>();
        // Kiểm tra người dùng có quyền chỉnh sửa
        if (exerciseId <= 0) {
            errors.add("ExerciseId Invalid");
        } else {
            if (this.exerciseService.getExerciseByIdAndCreatorId(exerciseId, accountId) == null) {
                errors.add("ExerciseId - CreatorId not found");
            }
        }
        // Kiểm tra dữ liệu cập nhật
        if (request.getTitle() != null) {
            if (formValidator.validateBlank(request.getTitle())) {
                errors.add("Title Blank");
            }
        }
        // Trạng thái phải hợp lệ
        if (request.getStatus() != null) {
            if (ExerciseStatus.fromString(request.getStatus()) == null) {
                errors.add("Status does not exist");
            } else {
                request.setStatus(request.getStatus().toUpperCase());
            }
        }
        // Tài khoản phải sở hữu thuật ngữ
        if (request.getUserTermId() != null && request.getUserTermId() != 0) {
            if (this.userTermService.getUserTermByIdAndCreatorId(request.getUserTermId(), accountId) == null) {
                errors.add("UserTerm Invalid");
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validatePut(long lessonId, long exerciseId, long accountId, UpdateExerciseFromLesson request) {
        List<String> errors = new ArrayList<>();
        // Kiểm tra người dùng có quyền chỉnh sửa
        if (exerciseId <= 0) {
            errors.add("ExerciseId Invalid");
        } else {
            if (this.exerciseService.getExerciseByIdAndCreatorId(exerciseId, accountId) == null) {
                errors.add("ExerciseId - CreatorId not found");
            }
        }
        // Kiểm tra dữ liệu cập nhật
        if (request.getTitle() != null) {
            if (formValidator.validateBlank(request.getTitle())) {
                errors.add("Title Blank");
            }
        }
        // Trạng thái phải hợp lệ
        if (request.getStatus() != null) {
            if (ExerciseStatus.fromString(request.getStatus()) == null) {
                errors.add("Status does not exist");
            } else {
                request.setStatus(request.getStatus().toUpperCase());
            }
        }
        // Tài khoản phải sở hữu thuật ngữ
        if (request.getUserTermId() != null && request.getUserTermId() != 0) {
            if (this.userTermService.getUserTermByIdAndCreatorId(request.getUserTermId(), accountId) == null) {
                errors.add("UserTerm Invalid");
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validateNull(UpdateExerciseFromLesson request) {
        if (request.getTitle() == null
            && request.getContent() == null
            && request.getStatus() == null
            && request.getUserTermId() == null
            && request.getPriority() == null
        ) {
            throw new HttpBadRequestException("No Data");
        }
    }

    public void validateNull(UpdateExerciseRequest request) {
        if (request.getTitle() == null
            && request.getContent() == null
            && request.getStatus() == null
            && request.getUserTermId() == null
        ) {
            throw new HttpBadRequestException("No Data");
        }
    }
}
