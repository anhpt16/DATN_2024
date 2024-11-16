package com.nhatanh.centerlearn.web.view;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.server.core.view.View;
import com.tvd12.ezyhttp.server.core.view.ViewDecorator;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import static com.tvd12.ezyfox.io.EzyStrings.isNotBlank;

@EzySingleton
public class WebViewDecorator implements ViewDecorator {

    @Override
    public void decorate(HttpServletRequest request, View view) {
        setLanguage(request, view);
    }

    protected void setLanguage(HttpServletRequest request, View view) {
        String lang = request.getParameter("lang");
        if (isNotBlank(lang)) {
            view.setLocale(new Locale(lang));
            view.setVariable("ezyLang", lang);
        }
    }

    protected boolean includeWebLanguages() {
        return false;
    }
}