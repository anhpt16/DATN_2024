package com.nhatanh.centerlearn.admin.validator;

import com.nhatanh.centerlearn.admin.controller.service.SubjectServiceController;
import com.nhatanh.centerlearn.admin.controller.service.TextbookServiceController;
import com.nhatanh.centerlearn.admin.filter.TextbookFilterCriteria;
import com.nhatanh.centerlearn.admin.request.AddTextbookRequest;
import com.nhatanh.centerlearn.admin.request.SaveTextbookRequest;
import com.nhatanh.centerlearn.admin.service.SubjectService;
import com.nhatanh.centerlearn.admin.service.TextbookLessonService;
import com.nhatanh.centerlearn.admin.service.TextbookService;
import com.nhatanh.centerlearn.common.enums.SubjectStatus;
import com.nhatanh.centerlearn.common.enums.TextbookStatus;
import com.nhatanh.centerlearn.common.request.AddLessonRequest;
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
public class TextbookValidator {
    private final TextbookServiceController textbookServiceController;
    private final UserTermService userTermService;
    private final SubjectService subjectService;
    private final LessonService lessonService;
    private final TextbookLessonService textbookLessonService;
    private final FormValidator formValidator;
    private final TextbookService textbookService;

    public void validate(AddTextbookRequest request) {
        List<String> errors = new ArrayList<>();
        // validate name
        if (request.getName() == null) {
            errors.add("Name Invalid");
            if (formValidator.validateBlank(request.getName())) {
                errors.add("Name is blank");
            }
        }
        // validate author
        if (request.getAuthor() == null) {
            errors.add("Author Invalid");
            if (formValidator.validateBlank(request.getAuthor())) {
                errors.add("Author is blank");
            }
        }
        // validate subjectId
        if (request.getSubjectId() > 0) {
            if (this.subjectService.getSubjectById(request.getSubjectId()) == null) {
                errors.add("Subject with id: " + request.getSubjectId() + " not found");
            }

        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validate(SaveTextbookRequest request, long textbookId) {
        List<String> errors = new ArrayList<>();
        // validate id
        if (textbookId == 0) {
            errors.add("TextbookId is blank");
        } else {
            if (this.textbookServiceController.getTextbookById(textbookId) == null) {
                errors.add("Textbook with id: " + textbookId + " invalid");
            }
        }
        // validate name
        if (request.getName() != null) {
            if (formValidator.validateBlank(request.getName())) {
                errors.add("Name is blank");
            }
        }
        // validate author
        if (request.getAuthor() != null) {
            if (formValidator.validateBlank(request.getAuthor())) {
                errors.add("Author is blank");
            }
        }
        // validate status
        if (request.getStatus() != null) {
            if (TextbookStatus.fromString(request.getStatus()) == null) {
                errors.add("Status does not exist");
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validateNull(SaveTextbookRequest request) {
        if (request.getName() == null
            && request.getDescription() == null
            && request.getAuthor() == null
            && request.getUrl() == null
            && request.getStatus() == null
        ) {
            throw new HttpBadRequestException("No thing to Update");
        }
    }

    public void validate(TextbookFilterCriteria criteria) {
        List<String> errors = new ArrayList<>();
        // Kiểm tra status tồn tại
        if (criteria.getStatus() != null) {
            if (TextbookStatus.fromString(criteria.getStatus()) == null) {
                errors.add("Status does not exist");
            } else {
                criteria.setStatus(criteria.getStatus().toUpperCase());
            }
        }
        // Kiểm tra subjectId
        if (criteria.getSubjectId() > 0) {
            if (this.subjectService.getSubjectById(criteria.getSubjectId()) == null) {
                errors.add("Subject with id: " + criteria.getSubjectId() + " not found");
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validate(AddLessonRequest request, long accountId, long textbookId) {
        List<String> errors = new ArrayList<>();

        // Kiểm tra mã giáo trình
        if (textbookId <= 0) {
            errors.add("TextbookId Invalid");
        } else {
            if (this.textbookServiceController.getTextbookById(textbookId) == null) {
                errors.add("Textbook with id: " + textbookId + " not found");
            }
        }

        // Kiểm tra UserTermId
        if (request.getUserTermId() > 0) {
            if (this.userTermService.getUserTermByIdAndCreatorId(request.getUserTermId(), accountId) == null) {
                errors.add("UserTerm with id: " + request.getUserTermId() + " and Creator with id: " + accountId + " not found");
            }
        }
        // Title không được trống
        if (request.getTitle() == null) {
            errors.add("Title Invalid");
        } else {
            if (request.getTitle() == "") {
                errors.add("Title Blank");
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validate(long textbookId, long lessonId, long accountId) {
        List<String> errors = new ArrayList<>();

        // Kiểm tra mã giáo trình
        if (textbookId <= 0) {
            errors.add("TextbookId Invalid");
        } else {
            if (this.textbookService.getTextBookById(textbookId) == null) {
                errors.add("Textbook with id: " + textbookId + " not found");
            }
        }
        // Kiểm tra mã bài học
        if (lessonId <= 0) {
            errors.add("LessonId Invalid");
        } else {
            if (this.lessonService.getLessonByIdAndCreatorId(lessonId, accountId) == null) {
                errors.add("Lesson with id: " + lessonId + " and Creator with id: " + accountId + " not found");
            }
        }
        // Kiểm tra bài học đã tồn tại trong giáo trình
        if (textbookId > 0 && lessonId > 0) {
            if (this.textbookLessonService.getTextbookLessonByTextbookIdAndLessonId(textbookId, lessonId) != null) {
                errors.add("Lesson exist on Textbook");
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validateDelete(long textbookId, long lessonId, long accountId) {
        List<String> errors = new ArrayList<>();

        // Kiểm tra mã giáo trình
        if (textbookId <= 0) {
            errors.add("TextbookId Invalid");
        } else {
            if (this.textbookService.getTextBookById(textbookId) == null) {
                errors.add("Textbook with id: " + textbookId + " not found");
            }
        }
        // Kiểm tra mã bài học
        if (lessonId <= 0) {
            errors.add("LessonId Invalid");
        } else {
            if (this.lessonService.getLessonByIdAndCreatorId(lessonId, accountId) == null) {
                errors.add("Lesson with id: " + lessonId + " and Creator with id: " + accountId + " not found");
            }
        }
        // Kiểm tra bài học đã tồn tại trong giáo trình
        if (textbookId > 0 && lessonId > 0) {
            if (this.textbookLessonService.getTextbookLessonByTextbookIdAndLessonId(textbookId, lessonId) == null) {
                errors.add("Lesson not found on Textbook");
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }
}
