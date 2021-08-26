package org.example;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

import java.lang.annotation.Annotation;
import java.util.Optional;

public class ExtensionHelper {
    public static <T extends Annotation> Optional<T> findAnnotation(ExtensionContext context, Class<T> clazz) {
        return AnnotationSupport.findAnnotation(context.getElement(), clazz);
    }
}
