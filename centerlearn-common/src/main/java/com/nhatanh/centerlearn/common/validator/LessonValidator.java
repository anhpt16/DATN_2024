package com.nhatanh.centerlearn.common.validator;

import com.nhatanh.centerlearn.common.request.AddLessonRequest;
import com.nhatanh.centerlearn.common.request.UpdateLessonRequest;
import com.nhatanh.centerlearn.common.enums.LessonStatus;
import com.nhatanh.centerlearn.common.service.LessonService;
import com.nhatanh.centerlearn.common.service.UserTermService;
import com.nhatanh.centerlearn.common.validator.FormValidator;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.core.exception.HttpBadRequestException;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@EzySingleton
public class LessonValidator {
    private final LessonService lessonService;
    private final UserTermService userTermService;
    private final FormValidator formValidator;

    public void validate(UpdateLessonRequest request, long lessonId, long accountId) {
        List<String> errors = new ArrayList<>();
        // Mã bài học phải tồn tại
        if (lessonId <= 0) {
            errors.add("LessonId Invalid");
        } else {
            if (this.lessonService.getLessonByIdAndCreatorId(lessonId, accountId) == null) {
                errors.add("UserId - LessonId Invalid");
            }
        }
        // Title không được trống
        if (request.getTitle() != null) {
            if (formValidator.validateBlank(request.getTitle())) {
                errors.add("Title Blank");
            }
        }
        // Trạng thái phải hợp lệ
        if (request.getStatus() != null) {
            if (LessonStatus.fromString(request.getStatus()) == null) {
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

    public void validateNull(UpdateLessonRequest request) {
        if (request.getTitle() == null
            && request.getDescription() == null
            && request.getStatus() == null
            && request.getNote() == null
            && request.getUserTermId() == null
        ) {
            throw new HttpBadRequestException("No Data");
        }
    }

    public void validate(long lessonId, long accountId) {
        List<String> errors = new ArrayList<>();
        // Kiểm tra quyền truy cập giáo trình tồn tại
        if (lessonId <= 0 ) {
            errors.add("LessonId Invalid");
        } else {
            if (this.lessonService.getLessonByIdAndCreatorId(lessonId, accountId) == null) {
                errors.add("LessonId - CreatorId Not Found");
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validate(AddLessonRequest request, long accountId) {
        List<String> errors = new ArrayList<>();
        // Kiểm tra tiêu đề
        if (request.getTitle() == null) {
            errors.add("Title Invalid");
        } else {
            if (formValidator.validateBlank(request.getTitle())) {
                errors.add("Title blank");
            }
        }
        // Kiểm tra userTermId
        if (request.getUserTermId() > 0) {
            if (this.userTermService.getUserTermByIdAndCreatorId(request.getUserTermId(), accountId) == null) {
                errors.add("UserTermId - CreatorId not found");
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }
}
