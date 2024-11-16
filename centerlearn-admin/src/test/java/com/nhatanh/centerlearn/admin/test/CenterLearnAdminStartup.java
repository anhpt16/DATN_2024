package com.nhatanh.centerlearn.admin.test;

import com.tvd12.ezyfox.annotation.EzyPackagesToScan;
import com.tvd12.ezyhttp.server.boot.EzyHttpApplicationBootstrap;

@EzyPackagesToScan("com.nhatanh.centerlearn")
public class CenterLearnAdminStartup {
    public static void main(String[] args) throws Exception {
        EzyHttpApplicationBootstrap.start(CenterLearnAdminStartup.class);
    }
}
