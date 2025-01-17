package com.nhatanh.centerlearn.web.controller.view;

import com.nhatanh.centerlearn.common.utils.RequestContext;
import com.nhatanh.centerlearn.web.controller.service.OrderServiceController;
import com.nhatanh.centerlearn.web.response.OrderDetailResponse;
import com.tvd12.ezyhttp.core.exception.HttpUnauthorizedException;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller("/personal")
public class OrderHistoryController {
    private final OrderServiceController orderServiceController;

    @DoGet("/order")
    public View initOrderHistory(

    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        List<OrderDetailResponse> orderDetailResponses = this.orderServiceController.getOrderSuccessByAccountId(accountId);
        return View.builder()
            .addVariable("orders", orderDetailResponses)
            .template("order_history")
            .build();
    }
}
