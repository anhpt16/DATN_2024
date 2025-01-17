package com.nhatanh.centerlearn.web.controller.decorator;


import com.nhatanh.centerlearn.common.model.GalleryModel;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.common.response.LessonResponse;
import com.nhatanh.centerlearn.common.service.MediaService;
import com.nhatanh.centerlearn.web.converter.WebModelToResponseConverter;
import com.nhatanh.centerlearn.web.model.*;
import com.nhatanh.centerlearn.web.response.WebCourseDetailResponse;
import com.nhatanh.centerlearn.web.response.WebCourseResponse;
import com.nhatanh.centerlearn.web.response.WebListSubjectResponse;
import com.nhatanh.centerlearn.web.service.*;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;
import static com.tvd12.ezyfox.io.EzyLists.newArrayList;


@EzySingleton
@AllArgsConstructor
public class WebCourseModelDecorator {
    private final AccountService accountService;
    private final MediaService mediaService;
    private final CourseSubjectService courseSubjectService;
    private final WebModelToResponseConverter webModelToResponseConverter;
    private final SubjectService subjectService;
    private final TextbookLessonService textbookLessonService;
    private final LessonService lessonService;

    public WebCourseResponse decorateAdminCourseModel(CourseModel model) {
        String creatorName = "";
        if (model.getCreatorId() > 0) {
            AccountModel accountModel = this.accountService.getAccountById(model.getCreatorId());
            if (accountModel != null) {
                creatorName = accountModel.getDisplayName();
            }
        }
        String imageUrl = "";
        if (model.getImageId() > 0) {
            GalleryModel galleryModel = this.mediaService.getGalleryById(model.getImageId());
            if (galleryModel != null) {
                imageUrl = galleryModel.getUrl();
            }
        }
        return webModelToResponseConverter.toAdminCourseResponse(model, creatorName, imageUrl);
    }

    public PaginationModel<WebCourseResponse> decorateAdminCoursePaginationModel(PaginationModel<CourseModel> coursePaginationModel) {
        List<CourseModel> courseModels = coursePaginationModel.getItems();
        Set<Long> creatorIds = courseModels.stream()
            .map(CourseModel::getCreatorId)
            .filter(id -> id > 0)
            .collect(Collectors.toSet());
        Set<Long> imageIds = courseModels.stream()
            .map(CourseModel::getImageId)
            .filter(id -> id > 0)
            .collect(Collectors.toSet());
        Map<Long, String> creatorIdsWithNames = this.accountService.getCreatorNameMapByIds(creatorIds);
        Map<Long, String> imageIdsWithUrls = this.mediaService.getImageUrlMapByIds(imageIds);
        List<WebCourseResponse> adminCourseResponses = newArrayList(courseModels, courseModel ->
            this.webModelToResponseConverter.toAdminCourseResponse(
                courseModel,
                creatorIdsWithNames.get(courseModel.getCreatorId()),
                imageIdsWithUrls.get(courseModel.getImageId()
                )));
        return PaginationModel.<WebCourseResponse>builder()
            .items(adminCourseResponses)
            .currentPage(coursePaginationModel.getCurrentPage())
            .totalPage(coursePaginationModel.getTotalPage())
            .build();
    }

    public WebCourseDetailResponse decorateCourseModel(CourseModel courseModel) {
        AccountModel accountModel = Optional.of(courseModel.getManagerId())
            .filter(id -> id > 0)
            .map(this.accountService::getAccountById)
            .orElse(null);

        String imageUrl = "";
        if (courseModel.getImageId() > 0) {
            GalleryModel galleryModel = this.mediaService.getGalleryById(courseModel.getImageId());
            if (galleryModel != null) {
                imageUrl = galleryModel.getUrl();
            }
        }
        String avatarUrl = "";
        if (accountModel != null) {
            if (accountModel.getAvatarId() > 0) {
                GalleryModel galleryModel = this.mediaService.getGalleryById(accountModel.getAvatarId());
                if (galleryModel != null) {
                    avatarUrl = galleryModel.getUrl();
                }
            }
        }

        List<WebListSubjectResponse> subjects = null;
        List<CourseSubjectModel> courseSubjectModels = this.courseSubjectService.getCourseSubjectsByCourseId(courseModel.getId());
        if (!courseSubjectModels.isEmpty()) {
            Set<Long> subjectIds = courseSubjectModels.stream().map(CourseSubjectModel::getSubjectId).filter(id -> id > 0).collect(Collectors.toSet());
            Map<Long, String> subjectNameMapByIds = this.subjectService.getSubjectNameMapByIds(subjectIds);
            Set<Long> textbookIds = courseSubjectModels.stream().map(CourseSubjectModel::getTextbookId).filter(id -> id > 0).collect(Collectors.toSet());
            Map<Long, List<LessonModel>> lessonModelsMapByTextbookIds = textbookIds.stream()
                .collect(Collectors.toMap(
                    textbookId -> textbookId,
                    textbookId -> this.textbookLessonService.getTextbookLessonByTextbookId(textbookId).stream()
                        .filter(textbookLessonModel -> textbookLessonModel.getTextbookId() == textbookId)
                        .sorted(Comparator.comparingDouble(TextbookLessonModel::getPriority))
                        .map(textbookLessonModel -> {
                            return this.lessonService.getLessonById(textbookLessonModel.getLessonId());
                        })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList())
                ));
            subjects = courseSubjectModels.stream()
                .collect(Collectors.groupingBy(CourseSubjectModel::getSubjectId))
                .entrySet()
                .stream()
                .map(entry -> {
                    Long subjectId = entry.getKey();
                    String subjectName = subjectNameMapByIds.getOrDefault(subjectId, "Unknown Subject");
                    List<LessonModel> lessonModels = entry.getValue().stream()
                        .map(courseSubject -> {
                            Long textbookId = courseSubject.getTextbookId();
                            return lessonModelsMapByTextbookIds.getOrDefault(textbookId, Collections.emptyList());
                        })
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());
                    return this.webModelToResponseConverter.toListSubjectResponse(subjectName, lessonModels);
                })
                .collect(Collectors.toList());
        }
        return this.webModelToResponseConverter.toCourseDetailResponse(courseModel, accountModel, imageUrl, avatarUrl, subjects);
    }
}
