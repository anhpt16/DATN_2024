package com.nhatanh.centerlearn.common.interceptor;

import com.nhatanh.centerlearn.common.exception.AccessDeniedException;
import com.nhatanh.centerlearn.common.model.PermissionModel;
import com.nhatanh.centerlearn.common.model.UriRequestModel;
import com.nhatanh.centerlearn.common.service.PermissionService;
import com.nhatanh.centerlearn.common.service.TokenService;
import com.nhatanh.centerlearn.common.utils.PermissionChecker;
import com.nhatanh.centerlearn.common.validator.TokenValidator;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyhttp.core.annotation.Interceptor;
import com.tvd12.ezyhttp.core.constant.HttpMethod;
import com.tvd12.ezyhttp.server.core.interceptor.RequestInterceptor;
import com.tvd12.ezyhttp.server.core.manager.RequestURIManager;
import com.tvd12.ezyhttp.server.core.request.RequestArguments;
import lombok.AllArgsConstructor;

import java.lang.reflect.Method;
import java.util.List;

@Interceptor
@AllArgsConstructor
public class AdminAuthenticationInterceptor extends EzyLoggable implements RequestInterceptor {
    private final RequestURIManager requestURIManager;
    private final TokenValidator tokenValidator;
    private final PermissionService permissionService;
    private final TokenService tokenService;
    private final PermissionChecker permissionChecker;

    public boolean preHandle(RequestArguments arguments, Method handler) {
        // 1. Lấy được method và uri (URIManager)
        // 2. Lấy được token (Header)
        // 3. Kiểm tra token
        // 4. Lấy roleId trong token và method - uri sau đó kiểm tra: roleId-Method-Uri có tồn tại không

        String token = arguments.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Xóa "Bearer " khỏi token
            if (token.isEmpty()) {
                throw new IllegalArgumentException("JWT String argument cannot be null or empty");
            }
        }
        String uriTemplate = arguments.getUriTemplate();
        HttpMethod method = arguments.getMethod();
        if (!this.requestURIManager.isManagementURI(method, uriTemplate)) {
            this.logger.info("pre handle request uri: {}, method: {}", arguments.getRequest().getRequestURI(), arguments.getMethod());
        }
        // Kiểm tra URI có cần xác thực hay không
        if (this.requestURIManager.isAuthenticatedURI(method, uriTemplate)) {
            this.logger.info("Cai nay can xac thuc");
        } else {
            this.logger.info("Khong can xac thuc");
            return true;
        }
        // Kiểm tra token
        if (!this.tokenValidator.validate(token)) {
            this.logger.info("Token Invalid");
        }
        // Kiểm tra người dùng có quyền truy cập vào URI - Method
        long roleId = this.tokenService.getTokenRoleId(token);
        UriRequestModel uriRequestModel = UriRequestModel.builder()
            .roleId(roleId)
            .path(uriTemplate)
            .method(method.name())
            .build();
        List<PermissionModel> permissionModelList = this.permissionService.getPermissionByRoleId(roleId);
        boolean isAuthorization = this.permissionChecker.isPermissionGranted(permissionModelList, uriRequestModel);
        if(!isAuthorization) {
            throw new AccessDeniedException("is Denied");
        }
        return true;
    }

    public void postHandle(RequestArguments arguments, Method handler) {
        String uriTemplate = arguments.getUriTemplate();
        HttpMethod method = arguments.getMethod();
        if (!this.requestURIManager.isManagementURI(method, uriTemplate)) {
            this.logger.info("post handle request uri: {}, method: {}, code: {}", new Object[]{arguments.getRequest().getRequestURI(), arguments.getMethod(), arguments.getResponse().getStatus()});
        }
    }
}
