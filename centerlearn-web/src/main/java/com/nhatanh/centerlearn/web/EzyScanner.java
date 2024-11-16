package com.nhatanh.centerlearn.web;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoDelete;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.DoPost;
import com.tvd12.ezyhttp.server.core.annotation.DoPut;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

public class EzyScanner {

    public static void main(String[] args) {
        // Thay 'com.nhatanh.centerlearn' bằng package của bạn
        scanForUris("com.nhatanh.centerlearn.web.controller");
    }

    public static void scanForUris(String basePackage) {
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
                    if (annotation instanceof DoGet ||
                        annotation instanceof DoPost || annotation instanceof DoPut || annotation instanceof DoDelete) {

                        String methodUri = getUriFromAnnotation(annotation);
                        String completeUri = baseUri + methodUri;
                        System.out.println("  Method: " + method.getName());
                        System.out.println("  Complete URI: " + completeUri);
                    }
                }
            }
        }
    }

    private static String getUriFromAnnotation(Annotation annotation) {
        if (annotation instanceof DoGet) {
            return String.join(", ", ((DoGet) annotation).value());
        } else if (annotation instanceof DoPost) {
            return String.join(", ", ((DoPost) annotation).value());
        } else if (annotation instanceof DoPut) {
            return String.join(", ", ((DoPut) annotation).value());
        } else if (annotation instanceof DoDelete) {
            return String.join(", ", ((DoDelete) annotation).value());
        }
        return "";
    }
}