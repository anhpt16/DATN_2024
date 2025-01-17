package com.nhatanh.centerlearn.web.converter;

import com.nhatanh.centerlearn.common.utils.Base64Util;
import com.nhatanh.centerlearn.web.model.AccountLoginModel;
import com.nhatanh.centerlearn.web.request.AuthAccountRequest;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

@EzySingleton
@AllArgsConstructor
public class WebRequestToModelConverter {
    private final Base64Util base64Util;

    public AccountLoginModel toAccountLoginModel(AuthAccountRequest request) {
        return AccountLoginModel.builder()
            .username(request.getUsername())
            .password(base64Util.encodePassword(request.getPassword()))
            .build();
    }
}
