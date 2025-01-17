package com.nhatanh.centerlearn.web.converter;

import com.nhatanh.centerlearn.common.constant.Constants;
import com.nhatanh.centerlearn.common.enums.CourseStatus;
import com.nhatanh.centerlearn.common.enums.CourseType;
import com.nhatanh.centerlearn.common.model.ExerciseModelWithPriority;
import com.nhatanh.centerlearn.common.model.GalleryModel;
import com.nhatanh.centerlearn.common.model.OrderModel;
import com.nhatanh.centerlearn.common.model.SectionModel;
import com.nhatanh.centerlearn.web.model.AccountModel;
import com.nhatanh.centerlearn.web.model.CourseModel;
import com.nhatanh.centerlearn.web.model.LessonModel;
import com.nhatanh.centerlearn.web.response.*;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.util.List;

@EzySingleton
@AllArgsConstructor
public class WebModelToResponseConverter {

    public AccountAvatarResponse toAccountAvatarResponse(String displayName, String avatarUrl) {
        String apiUrl = "";
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            apiUrl = Constants.apiImage + avatarUrl;
        }
        return AccountAvatarResponse.builder()
            .displayName(displayName)
            .userImageUrl(apiUrl)
            .build();
    }

    public WebCourseResponse toAdminCourseResponse(CourseModel model, String creatorName, String imageUrl) {
        String apiUrl = "";
        String creator = "";
        if (imageUrl != null && !imageUrl.isEmpty()) {
            apiUrl = Constants.apiImage + imageUrl;
        }
        if (creatorName != null) {
            creator= creatorName;
        }
        return WebCourseResponse.builder()
            .id(model.getId())
            .code(model.getCode())
            .displayName(model.getDisplayName())
            .courseType(CourseType.fromString(model.getCourseType()).getDisplayName())
            .duration(model.getDuration())
            .description(model.getDescription())
            .status(CourseStatus.fromString(model.getStatus()))
            .createdAt(model.getCreatedAt())
            .updatedAt(model.getUpdatedAt())
            .creatorId(model.getCreatorId())
            .creatorName(creator)
            .apiUrl(apiUrl)
            .slug(model.getSlug())
            .price(model.getPrice())
            .managerId(model.getManagerId())
            .build();
    }

    public WebListSubjectResponse toListSubjectResponse(String subjectName, List<LessonModel> lessons) {
        return WebListSubjectResponse.builder()
            .subjectName(subjectName)
            .lessons(lessons)
            .build();
    }

    public WebCourseDetailResponse toCourseDetailResponse
        (CourseModel courseModel, AccountModel accountModel, String imageUrl, String avatarUrl, List<WebListSubjectResponse> subjectResponses)
    {
        String apiUrl = "";
        if (imageUrl != null && !imageUrl.isEmpty()) {
            apiUrl = Constants.apiImage + imageUrl;
        }
        String avatarApi = "";
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            avatarApi = Constants.apiImage + avatarUrl;
        }
        int duration = 0;
        if (subjectResponses != null && !subjectResponses.isEmpty()) {
            duration = subjectResponses.stream()
                .mapToInt(response -> response.getLessons().size())
                .sum();
        }

        String managerName = "";
        String managerPhone = "";
        String managerEmail = "";
        if (accountModel != null) {
            managerEmail = accountModel.getEmail();
            managerPhone = accountModel.getPhone();
            managerName = accountModel.getDisplayName();
        }
        return WebCourseDetailResponse.builder()
            .id(courseModel.getId())
            .slug(courseModel.getSlug())
            .code(courseModel.getCode())
            .displayName(courseModel.getDisplayName())
            .courseType(courseModel.getCourseType())
            .duration(duration)
            .description(courseModel.getDescription())
            .apiUrl(apiUrl)
            .price(courseModel.getPrice())
            .imageApi(avatarApi)
            .managerName(managerName)
            .managerEmail(managerEmail)
            .managerPhone(managerPhone)
            .subjects(subjectResponses)
            .build();
    }

    public WebCourseResponse toCourseResponse (CourseModel model) {
        return WebCourseResponse.builder()
            .id(model.getId())
            .code(model.getCode())
            .displayName(model.getDisplayName())
            .courseType(model.getCourseType())
            .duration(model.getDuration())
            .description(model.getDescription())
            .status(CourseStatus.fromString(model.getStatus()))
            .createdAt(model.getCreatedAt())
            .updatedAt(model.getUpdatedAt())
            .creatorId(model.getCreatorId())
            .slug(model.getSlug())
            .price(model.getPrice())
            .managerId(model.getManagerId())
            .build();
    }

    public AdminListSectionResponse toListSectionResponse(SectionModel model) {
        return AdminListSectionResponse.builder()
            .id(model.getId())
            .title(model.getTitle())
            .priority(model.getPriority())
            .build();
    }

    public AdminListExerciseResponse toListExerciseResponse(ExerciseModelWithPriority model) {
        return AdminListExerciseResponse.builder()
            .id(model.getId())
            .title(model.getTitle())
            .priority(model.getPriority())
            .build();
    }

    public AdminLessonSectionResponse toAdminLessonSectionResponse(com.nhatanh.centerlearn.common.model.LessonModel lessonModel, List<AdminListSectionResponse> adminListSectionResponses, float lessonPriority) {
        return AdminLessonSectionResponse.builder()
            .id(lessonModel.getId())
            .title(lessonModel.getTitle())
            .priority(lessonPriority)
            .sections(adminListSectionResponses)
            .build();
    }

    public AdminLessonExerciseResponse toAdminLessonExerciseResponse(com.nhatanh.centerlearn.common.model.LessonModel lessonModel, List<AdminListExerciseResponse> adminListExerciseResponses, float priority) {
        return AdminLessonExerciseResponse.builder()
            .id(lessonModel.getId())
            .title(lessonModel.getTitle())
            .priority(priority)
            .exercises(adminListExerciseResponses)
            .build();
    }

    public AllCourseTextbookSection toAllCourseTextbookSectionResponse(long subjectId, String subjectName, List<AdminLessonSectionResponse> adminLessonSectionResponses) {
        return AllCourseTextbookSection.builder()
            .subjectId(subjectId)
            .SubjectName(subjectName)
            .lessonsSections(adminLessonSectionResponses)
            .build();
    }

    public AllCourseTextbookExercise toAllCourseTextbookExerciseResponse(long subjectId, String subjectName, List<AdminLessonExerciseResponse> adminLessonExerciseResponses) {
        return AllCourseTextbookExercise.builder()
            .subjectId(subjectId)
            .SubjectName(subjectName)
            .lessonsExercises(adminLessonExerciseResponses)
            .build();
    }

    public OrderDetailResponse toOrderDetailResponse(OrderModel orderModel, CourseModel courseModel) {
        return OrderDetailResponse.builder()
            .id(orderModel.getId())
            .status(orderModel.getStatus())
            .price(orderModel.getPrice())
            .code(orderModel.getCode())
            .courseId(courseModel.getId())
            .courseCode(courseModel.getCode())
            .courseName(courseModel.getDisplayName())
            .createdAt(orderModel.getCreatedAt())
            .updatedAt(orderModel.getUpdatedAt())
            .build();
    }
}
