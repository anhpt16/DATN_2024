package com.nhatanh.centerlearn.admin.controller.api;

import com.nhatanh.centerlearn.admin.controller.service.MediaServiceController;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import com.tvd12.ezyhttp.server.core.request.RequestArguments;
import lombok.AllArgsConstructor;

@Api
@Controller("/api/v1/admin/media")
@AllArgsConstructor
public class AdminMediaApiController {

    private final MediaServiceController mediaServiceController;

    @Authenticated
    @DoPost("/image")
    public ResponseEntity uploadImage(
        RequestArguments requestArguments
    ) throws Exception {
        this.mediaServiceController.upload(requestArguments.getRequest());
        return  ResponseEntity.noContent();
    }

}
