package com.nhatanh.centerlearn.admin.controller.view;

import com.nhatanh.centerlearn.common.controller.controller.MediaServiceController;
import com.nhatanh.centerlearn.common.filter.MediaFilterCriteria;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.common.response.GalleryResponse;
import com.nhatanh.centerlearn.common.utils.RequestContext;
import com.tvd12.ezyhttp.core.exception.HttpUnauthorizedException;
import com.tvd12.ezyhttp.server.core.annotation.Authenticated;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.RequestParam;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
@Controller("/media")
public class AdminMediaController {
    private final MediaServiceController mediaServiceController;

    @Authenticated
    @DoGet("/gallery")
    public View initGallary(
    ) {
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        MediaFilterCriteria criteria = MediaFilterCriteria.builder()
            .ownerAccountId(accountId)
            .sortOrder(1)
            .build();
        PaginationModel<GalleryResponse> galleryPaginationResponse = this.mediaServiceController.getGalleryPaginationByAccountId(criteria, 0, 12);
        return View.builder()
            .addVariable("galleryPagination", galleryPaginationResponse)
            .template("/contents/info/gallery")
            .build();
    }
}
