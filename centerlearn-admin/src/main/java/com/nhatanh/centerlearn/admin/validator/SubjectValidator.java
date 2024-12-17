package com.nhatanh.centerlearn.admin.validator;

import com.nhatanh.centerlearn.admin.filter.SubjectFilterCriteria;
import com.nhatanh.centerlearn.admin.request.AddSubjectRequest;
import com.nhatanh.centerlearn.admin.request.SaveSubjectRequest;
import com.nhatanh.centerlearn.admin.service.SubjectService;
import com.nhatanh.centerlearn.admin.service.SubjectTextbookService;
import com.nhatanh.centerlearn.admin.service.TextbookService;
import com.nhatanh.centerlearn.common.entity.SubjectTextbookId;
import com.nhatanh.centerlearn.common.enums.SubjectStatus;
import com.nhatanh.centerlearn.common.service.MediaService;
import com.nhatanh.centerlearn.common.validator.FormValidator;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.core.exception.HttpBadRequestException;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@EzySingleton
public class SubjectValidator {
    private final FormValidator formValidator;
    private final SubjectService subjectService;
    private final MediaService mediaService;
    private final TextbookService textbookService;
    private final SubjectTextbookService subjectTextbookService;

    public void validate(AddSubjectRequest request, long accountId) {
        List<String> errors = new ArrayList<>();

        // Kiểm tra tên không được trống
        if (request.getName() == null || formValidator.validateBlank(request.getName())) {
            errors.add("Name is blank");
        }  else {
            // Kiểm tra tên không bao gồm ký tự đặc biệt
            if (!formValidator.validateSpecialCharacter(request.getName())) {
                errors.add("Name has special character");
            } else {
                // Kiểm tra tên không tồn tại trong CSDL
                if (this.subjectService.getSubjectIdByName(request.getName()) != 0) {
                    errors.add("Name is exist");
                }
            }
        }
        // Kiểm tra tên hiển thị không được trống
        if (request.getDisplayName() == null || formValidator.validateBlank(request.getDisplayName())) {
            errors.add("DisplayName is Blank");
        }
        // Kiểm tra nếu có ảnh
        if (request.getImageId() > 0) {
            // Kiểm tra ảnh có tồn tại trong CSDL
            if (this.mediaService.getGalleryById(request.getImageId()) == null) {
                errors.add("Image is not exist");
            } else {
                // Kiểm tra người dùng có sở hữu ảnh
                if (this.mediaService.getGalleryByAccountIdAndId(accountId, request.getImageId()) == null) {
                    errors.add("Account hasn't Image");
                }
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }
    public void validate(SaveSubjectRequest request, long accountId, long subjectId) {
        List<String> errors = new ArrayList<>();
        // Kiểm tra id có và tồn tại
        if (subjectId == 0) {
            errors.add("Subject Id blank");
        } else {
            if (this.subjectService.getSubjectById(subjectId) == null) {
                errors.add("Subject Id invalid");
            }
        }
        // Kiểm tra name: không trống, không chứa ký tự đặc biệt, chưa tồn tại (id)
        if (request.getName() != null) {
            if (formValidator.validateBlank(request.getName())) {
                errors.add("Name is blank");
            } else {
                if (!formValidator.validateSpecialCharacter(request.getName())) {
                    errors.add("Name has special character");
                } else {
                    if (this.subjectService.getSubjectIdByName(request.getName()) != subjectId
                        && this.subjectService.getSubjectIdByName(request.getName()) != 0
                    ) {
                        errors.add("Name exist");
                    }
                }
            }
        }
        // Kiểm tra displayName: không trống, không chứa ký tự đặc biệt
        if (request.getDisplayName() != null) {
            if (formValidator.validateBlank(request.getDisplayName())) {
                errors.add("DisplayName is blank");
            }
        }
        // Kiểm tra nếu có ảnh
        if (request.getImageId() > 0) {
            // Kiểm tra ảnh có tồn tại trong CSDL
            if (this.mediaService.getGalleryById(request.getImageId()) == null) {
                errors.add("Image does not exist");
            } else {
                // Kiểm tra người dùng có sở hữu ảnh
                if (this.mediaService.getGalleryByAccountIdAndId(accountId, request.getImageId()) == null) {
                    errors.add("Account hasn't Image");
                }
            }
        }
        // Kiểm tra trạng thái
        if (request.getStatus() != null) {
            if (SubjectStatus.fromString(request.getStatus()) == null) {
                errors.add("Status does not exist");
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }
    public void validateNull(SaveSubjectRequest request) {
        if (request.getName() == null && request.getDisplayName() == null && request.getDescription() == null && request.getImageId() <= 0 && request.getStatus() == null) {
            throw new HttpBadRequestException("No Data");
        }
    }

    public void validate(SubjectFilterCriteria criteria) {
        List<String> errors = new ArrayList<>();
        // Kiểm tra status tồn tại
        if (criteria.getStatus() != null) {
            if (SubjectStatus.fromString(criteria.getStatus()) == null) {
                errors.add("Status does not exist");
            } else {
                criteria.setStatus(criteria.getStatus().toUpperCase());
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validateAdd(long subjectId, long textbookId) {
        List<String> errors = new ArrayList<>();
        // Kiểm tra id môn học
        if (subjectId < 0) {
            errors.add("Subject Id Invalid");
        } else {
            if (this.subjectService.getSubjectById(subjectId) == null) {
                errors.add("Subject Not Found");
            }
        }
        // Kiểm tra id giáo trình
        if (textbookId < 0) {
            errors.add("Textbook Id Invalid");
        } else {
            if (this.textbookService.getTextBookById(textbookId) == null) {
                errors.add("Textbook Not Found");
            }
        }
        // Kiểm tra cặp giá trị tồn tại
        SubjectTextbookId subjectTextbookId = new SubjectTextbookId(subjectId, textbookId);
        if (this.subjectTextbookService.getSubjectTextbookById(subjectTextbookId) != null) {
            errors.add("Subject Has This Textbook");
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validateDelete(long subjectId, long textbookId) {
        List<String> errors = new ArrayList<>();
        // Kiểm tra mã môn học
        if (subjectId < 0) {
            errors.add("Subject Id Invalid");
        } else {
            if (this.subjectService.getSubjectById(subjectId) == null) {
                errors.add("Subject Not Found");
            }
        }
        // Kiểm tra mã giáo trình
        if (textbookId < 0) {
            errors.add("Textbook Id Invalid");
        } else {
            if (this.textbookService.getTextBookById(textbookId) == null) {
                errors.add("Textbook Not Found");
            }
        }
        // Kiểm tra cặp giá trị không tồn tại
        SubjectTextbookId subjectTextbookId = new SubjectTextbookId(subjectId, textbookId);
        if (this.subjectTextbookService.getSubjectTextbookById(subjectTextbookId) == null) {
            errors.add("Subject - Textbook Not Found");
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validateGet(long subjectId) {
        List<String> errors = new ArrayList<>();
        // Kiểm tra mã môn học
        if (subjectId < 0) {
            errors.add("Subject Id Invalid");
        } else {
            if (this.subjectService.getSubjectById(subjectId) == null) {
                errors.add("Subject Not Found");
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }
}
