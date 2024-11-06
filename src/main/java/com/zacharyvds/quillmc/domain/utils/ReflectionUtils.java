package com.zacharyvds.quillmc.domain.utils;

import org.reflections.Reflections;

import java.util.Set;

public class ReflectionUtils {
    public static Set<Class<?>> getClassesWithAnnotation(String packageName, Class annotation) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getTypesAnnotatedWith(annotation);
    }
}
