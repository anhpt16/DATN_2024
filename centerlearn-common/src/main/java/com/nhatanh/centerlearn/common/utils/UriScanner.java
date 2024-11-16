package com.nhatanh.centerlearn.common.utils;

import com.nhatanh.centerlearn.common.constant.Constants;
import com.nhatanh.centerlearn.common.enums.MethodName;
import com.nhatanh.centerlearn.common.enums.PermissionStatus;
import com.nhatanh.centerlearn.common.model.UriModel;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoDelete;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.DoPost;
import com.tvd12.ezyhttp.server.core.annotation.DoPut;
import lombok.Getter;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@EzySingleton
public class UriScanner {
    public static void main(String[] args) {
//        // Thay 'com.nhatanh.centerlearn' bằng package của bạn
//        List<UriModel> uris = new ArrayList<>();
//        uris.addAll(scanForUris("com.nhatanh.centerlearn"));
//
//        // In ra các URI và phương thức HTTP tương ứng
//        for (UriModel uri : uris) {
//            System.out.println(uri);
//        }
    }

    public List<UriModel> scanForUris(String basePackage) {
        List<UriModel> uris = new ArrayList<>();

        // Sử dụng Reflections để tìm tất cả các class có annotation @Controller trong package
        Reflections reflections = new Reflections(new ConfigurationBuilder()
            .setUrls(ClasspathHelper.forPackage(basePackage))
            .setScanners(new SubTypesScanner(false), new TypeAnnotationsScanner()));

        // Tìm tất cả các class có annotation @Controller
        Set<Class<?>> controllerClasses = reflections.getTypesAnnotatedWith(Controller.class);

        for (Class<?> controllerClass : controllerClasses) {
            System.out.println("Controller: " + controllerClass.getName());

            // Lấy base URI từ annotation @Controller nếu có
            String baseUri = "";
            Controller controllerAnnotation = controllerClass.getAnnotation(Controller.class);
            if (controllerAnnotation != null) {
                // Sử dụng giá trị từ 'value()' hoặc 'uri()' nếu có
                baseUri = !controllerAnnotation.uri().isEmpty() ? controllerAnnotation.uri() : controllerAnnotation.value();
            }

            // Lấy tất cả các method có annotation @DoGet, @DoPost, @DoPut, @DoDelete
            Method[] methods = controllerClass.getDeclaredMethods();
            for (Method method : methods) {
                Annotation[] annotations = method.getAnnotations();

                for (Annotation annotation : annotations) {
                    String methodUri = "";
                    MethodName methodName = null;

                    if (annotation instanceof DoGet) {
                        methodUri = String.join(", ", ((DoGet) annotation).value());
                        methodName = MethodName.GET;
                    } else if (annotation instanceof DoPost) {
                        methodUri = String.join(", ", ((DoPost) annotation).value());
                        methodName = MethodName.POST;
                    } else if (annotation instanceof DoPut) {
                        methodUri = String.join(", ", ((DoPut) annotation).value());
                        methodName = MethodName.PUT;
                    } else if (annotation instanceof DoDelete) {
                        methodUri = String.join(", ", ((DoDelete) annotation).value());
                        methodName = MethodName.DELETE;
                    }

                    if (!methodUri.isEmpty()) {
                        String completeUri = normalizeUri(baseUri + methodUri);
                        uris.add(new UriModel(completeUri, methodName, null));
                    }
                }
            }
        }

        return uris;
    }
    public String normalizeUri(String uri) {
        if (uri.equals("/")) {
            return uri;
        }
        if (uri.endsWith("/")) {
            return uri.substring(0, uri.length() - 1);
        }
        return uri;
    }

}
