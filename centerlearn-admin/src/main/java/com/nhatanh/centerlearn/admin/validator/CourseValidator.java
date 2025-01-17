package com.nhatanh.centerlearn.admin.validator;

import com.nhatanh.centerlearn.admin.request.AddCourseRequest;
import com.nhatanh.centerlearn.admin.request.UpdateCourseRequest;
import com.nhatanh.centerlearn.admin.service.*;
import com.nhatanh.centerlearn.common.constant.Constants;
import com.nhatanh.centerlearn.common.enums.CourseStatus;
import com.nhatanh.centerlearn.common.enums.CourseType;
import com.nhatanh.centerlearn.common.enums.ExerciseStatus;
import com.nhatanh.centerlearn.common.validator.FormValidator;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.core.exception.HttpBadRequestException;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@EzySingleton
public class CourseValidator {
    private final FormValidator formValidator;
    private final CourseService courseService;
    private final SubjectService subjectService;
    private final TextbookService textbookService;
    private final AccountRoleService accountRoleService;
    private final CourseSubjectService courseSubjectService;

    public void validate(AddCourseRequest request) {
        List<String> errors = new ArrayList<>();
        if (request == null) {
            throw new HttpBadRequestException("Request Null");
        }
        // Kiểm tra code
        if (request.getCode() == null) {
            errors.add("Code Invalid");
        } else {
            if (this.formValidator.validateBlank(request.getCode())) {
                errors.add("Code Blank");
            }
        }
        // Kiểm tra displayName
        if (request.getDisplayName() == null) {
            errors.add("DisplayName Invalid");
        } else {
            if (this.formValidator.validateBlank(request.getDisplayName())) {
                errors.add("DisplayName Blank");
            }
        }

        // Kiểm tra courseType
        if (request.getCourseType() == null) {
            errors.add("CourseType Invalid");
        } else {
            if (CourseType.fromString(request.getCourseType()) == null) {
                errors.add("CourseType does not exist");
            } else {
                request.setCourseType(request.getCourseType().toUpperCase());
            }
        }

        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validate (UpdateCourseRequest request, long courseId) {
        List<String> errors = new ArrayList<>();

        if (request.getCode() != null) {
            if (formValidator.validateBlank(request.getCode())) {
                errors.add("Code Blank");
            }
        }

        if (request.getDisplayName() != null) {
            if (formValidator.validateBlank(request.getDisplayName())) {
                errors.add("DisplayName Blank");
            }
        }

        if (request.getCourseType() != null) {
            if (CourseType.fromString(request.getCourseType()) == null) {
                errors.add("CourseType does not exist");
            } else {
                request.setCourseType(request.getCourseType().toUpperCase());
            }
        }

        if (request.getStatus() != null) {
            if (CourseStatus.fromString(request.getStatus()) == null) {
                errors.add("Status does not exist");
            } else {
                request.setStatus(request.getStatus().toUpperCase());
            }
        }


        if(errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validateNull(UpdateCourseRequest request) {
        if (request.getCode() == null
            && request.getDisplayName() == null
            && request.getCourseType() == null
            && request.getStatus() == null
            && request.getDescription() == null
            && request.getImageId() == null
            && request.getPrice() == null
        ) {
            throw new HttpBadRequestException("No Data");
        }
    }

    public void validate(long courseId, long subjectId, long textbookId) {
        if (courseId <= 0) {
            throw new HttpBadRequestException("CourseId Invalid");
        } else {
            if (this.courseService.getCourseById(courseId) == null) {
                throw new HttpBadRequestException("Course Not Found");
            }
        }
        if (subjectId <= 0) {
            throw new HttpBadRequestException("CourseId Invalid");
        } else {
            if (this.subjectService.getSubjectById(subjectId) == null) {
                throw new HttpBadRequestException("Subject Not Found");
            }
        }
        if (textbookId <= 0) {
            throw new HttpBadRequestException("CourseId Invalid");
        } else {
            if (this.textbookService.getTextBookById(textbookId) == null) {
                throw new HttpBadRequestException("Textbook Not Found");
            }
        }
        if (this.courseSubjectService.getCourseSubjectByCourseIdAndSubjectId(courseId, subjectId) != null) {
            throw new HttpBadRequestException("CourseSubject Exist");
        }
    }

    public void validate(long courseId, long subjectId) {
        if (courseId <= 0) {
            throw new HttpBadRequestException("CourseId Invalid");
        }
        if (subjectId <= 0) {
            throw new HttpBadRequestException("CourseId Invalid");
        }
        if (this.courseSubjectService.getCourseSubjectByCourseIdAndSubjectId(courseId, subjectId) == null) {
            throw new HttpBadRequestException("CourseSubject Not Exist");
        }
    }

    public void validateManager(long courseId, long managerId) {
        if (courseId <= 0) {
            throw new HttpBadRequestException("CourseId Invalid");
        } else {
            if (this.courseService.getCourseById(courseId) == null) {
                throw new HttpBadRequestException("Course Not Found");
            }
        }
        if (managerId <= 0) {
            throw new HttpBadRequestException("ManagerId Invalid");
        } else {
            if (this.accountRoleService.getAccountRole(managerId, Constants.ROLE_ID_MANAGER) == null) {
                throw new HttpBadRequestException("Manager Not Found");
            }
        }
    }
}
