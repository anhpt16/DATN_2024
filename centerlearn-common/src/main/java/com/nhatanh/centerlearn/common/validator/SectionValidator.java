package com.nhatanh.centerlearn.common.validator;

import com.nhatanh.centerlearn.common.enums.LessonStatus;
import com.nhatanh.centerlearn.common.enums.SectionStatus;
import com.nhatanh.centerlearn.common.request.AddSectionRequest;
import com.nhatanh.centerlearn.common.request.SaveSectionRequest;
import com.nhatanh.centerlearn.common.service.LessonService;
import com.nhatanh.centerlearn.common.service.SectionService;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.core.exception.HttpBadRequestException;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@EzySingleton
public class SectionValidator {
    private final FormValidator formValidator;
    private final LessonService lessonService;
    private final SectionService sectionService;

    public void validate(AddSectionRequest request, long accountId, long lessonId) {
        List<String> errors = new ArrayList<>();

        // Kiểm tra title
        if (request.getTitle() == null) {
            errors.add("Title Invalid");
        } else {
            if (formValidator.validateBlank(request.getTitle())) {
                errors.add("Title Blank");
            }
        }
        // Kiểm tra lessonId
        if (lessonId <= 0) {
            errors.add("LessonId Invalid");
        } else {
            if (this.lessonService.getLessonByIdAndCreatorId(lessonId, accountId) == null) {
                errors.add("LessonId - CreatorId not found");
            }
        }

        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }


    public void validate(long lessonId, long sectionId, long accountId) {
        List<String> errors = new ArrayList<>();
        // Kiểm tra xem người dùng có được phép thao tác vơi bài học không
        if (lessonId <= 0) {
            errors.add("LessonId Invalid");
        } else {
            if (this.lessonService.getLessonByIdAndCreatorId(lessonId, accountId) == null) {
                errors.add("LessonId - CreatorId not found");
            }
        }
        // Kiểm tra đề mục - bài học hợp lệ
        if (sectionId <= 0) {
            errors.add("SectionId Invalid");
        } else {
            if (this.sectionService.getSectionByIdAndLessonId(sectionId, lessonId) == null) {
                errors.add("SectionId - LessonId not found");
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validateSave(long sectionId, long lessonId, long accountId, SaveSectionRequest request) {
        List<String> errors = new ArrayList<>();
        // Kiểm tra xem người dùng có được phép thao tác vơi bài học không
        if (lessonId <= 0) {
            errors.add("LessonId Invalid");
        } else {
            if (this.lessonService.getLessonByIdAndCreatorId(lessonId, accountId) == null) {
                errors.add("LessonId - CreatorId not found");
            }
        }
        // Kiểm tra đề mục - bài học hợp lệ
        if (sectionId <= 0) {
            errors.add("SectionId Invalid");
        } else {
            if (this.sectionService.getSectionByIdAndLessonId(sectionId, lessonId) == null) {
                errors.add("SectionId - LessonId not found");
            }
        }
        // Kiểm tra dữ liệu cần sửa
        if (request.getTitle() != null) {
            if (formValidator.validateBlank(request.getTitle())) {
                errors.add("Title Blank");
            }
        }
        if (request.getStatus() != null) {
            if (SectionStatus.fromString(request.getStatus()) == null) {
                errors.add("Status does not exist");
            } else {
                request.setStatus(request.getStatus().toUpperCase());
            }
        }

        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validateNull(SaveSectionRequest request) {
        if (request.getTitle() == null
            && request.getContent() == null
            && request.getStatus() == null
            && request.getLessonId() == null
            && request.getPriority() == null
        ) {
            throw new HttpBadRequestException("No Data");
        }
    }

    public void validateDelete(long lessonId, long sectionId, long accountId) {
        List<String> errors = new ArrayList<>();
        // Kiểm tra xem người dùng có được phép thao tác vơi bài học không
        if (lessonId <= 0) {
            errors.add("LessonId Invalid");
        } else {
            if (this.lessonService.getLessonByIdAndCreatorId(lessonId, accountId) == null) {
                errors.add("LessonId - CreatorId not found");
            }
        }
        // Kiểm tra đề mục - bài học hợp lệ
        if (sectionId <= 0) {
            errors.add("SectionId Invalid");
        } else {
            if (this.sectionService.getSectionByIdAndLessonId(sectionId, lessonId) == null) {
                errors.add("SectionId - LessonId not found");
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }
}
