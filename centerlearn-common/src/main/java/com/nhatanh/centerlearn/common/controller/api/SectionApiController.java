package com.nhatanh.centerlearn.common.controller.api;

import com.nhatanh.centerlearn.common.controller.controller.SectionServiceController;
import com.nhatanh.centerlearn.common.enums.SectionStatus;
import com.tvd12.ezyhttp.server.core.annotation.Api;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;

@Api
@Controller("/api/v1/section")
@AllArgsConstructor
public class SectionApiController {
    private final SectionServiceController sectionServiceController;

    @DoGet("/statuses")
    public List<SectionStatus> getSectionStatuses() {
        List<SectionStatus> sectionStatuses = this.sectionServiceController.getSectionStatuses();
        if (sectionStatuses.isEmpty()) {
            return Collections.emptyList();
        }
        return sectionStatuses;
    }
}
