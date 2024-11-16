package com.nhatanh.centerlearn.web.test;

import com.tvd12.ezyfox.annotation.EzyPackagesToScan;
import com.tvd12.ezyhttp.server.boot.EzyHttpApplicationBootstrap;

@EzyPackagesToScan("com.nhatanh.centerlearn")
public class CenterLearnWebStartup {

    public static void main(String[] args) throws Exception {
        EzyHttpApplicationBootstrap.start(CenterLearnWebStartup.class);
    }
}
