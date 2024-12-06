package com.nhatanh.centerlearn.common.interceptor;

import com.nhatanh.centerlearn.common.exception.AccessDeniedException;
import com.nhatanh.centerlearn.common.exception.HttpTokenExpiration;
import com.nhatanh.centerlearn.common.model.PermissionModel;
import com.nhatanh.centerlearn.common.model.UriRequestModel;
import com.nhatanh.centerlearn.common.service.PermissionService;
import com.nhatanh.centerlearn.common.service.TokenService;
import com.nhatanh.centerlearn.common.utils.PermissionChecker;
import com.nhatanh.centerlearn.common.utils.RequestContext;
import com.nhatanh.centerlearn.common.validator.TokenValidator;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyhttp.core.annotation.Interceptor;
import com.tvd12.ezyhttp.core.constant.HttpMethod;
import com.tvd12.ezyhttp.core.exception.HttpUnauthorizedException;
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
        // 1. Kiểm tra xem đây có phải là api cần xác thực hay không, nếu không thì trả về true
        // 2. Nếu là api cần xác thực, lấy token trong cookie để kiểm tra.
        // 3. Kiểm tra token có hợp lệ hay không (hiệu lực, xác thực).
        // 4. Kiểm tra người dùng có quyền truy cập api này không (userId, roleIds).

        String token = arguments.getCookieValue("authToken");
        long userId = 0;
        List<Long> roleIds = null;
        System.out.println(token);
        if (token != null && !token.isEmpty()) {
            // Lấy ra các vai trò của người dùng
            userId = this.tokenService.getTokenAccountId(token);
            roleIds = this.tokenService.getTokenRoleId(token);
        }

        String uriTemplate = arguments.getUriTemplate();
        HttpMethod method = arguments.getMethod();
        if (!this.requestURIManager.isManagementURI(method, uriTemplate)) {
            this.logger.info("pre handle request uri: {}, method: {}", arguments.getRequest().getRequestURI(), arguments.getMethod());
        }
        if (this.requestURIManager.isAuthenticatedURI(method, uriTemplate)) {
            // Nếu api cần xác thực
            this.logger.info("Cai nay can xac thuc");

            if (token == null || token.isEmpty()) {
                throw new HttpTokenExpiration("JWT String argument cannot be null or empty");
            }
            if (!this.tokenValidator.validate(token)) {
                this.logger.info("Token Invalid");
                throw new HttpUnauthorizedException("Token Invalid");
            }
            // Kiểm tra trạng thái của người dùng


            // Kiểm tra quyền hạn của người dùng
            boolean isAuthorization = this.tokenValidator.validatePermissionAccess(roleIds, uriTemplate, method.name());
            if(!isAuthorization) {
                throw new AccessDeniedException("is Denied");
            }

            RequestContext.set("accountId", userId);
            RequestContext.set("roleIds", roleIds);
            return true;
        }
        RequestContext.set("accountId", userId);
        RequestContext.set("roleIds", roleIds);
        // Trả true nếu không cần xác thực
        this.logger.info("Khong can xac thuc");
        return true;
    }

    public void postHandle(RequestArguments arguments, Method handler) {
        String uriTemplate = arguments.getUriTemplate();
        HttpMethod method = arguments.getMethod();
        if (!this.requestURIManager.isManagementURI(method, uriTemplate)) {
            this.logger.info("post handle request uri: {}, method: {}, code: {}", new Object[]{arguments.getRequest().getRequestURI(), arguments.getMethod(), arguments.getResponse().getStatus()});
        }
        RequestContext.clear();
    }
}
