package com.example.company.framework.ioc;


import com.example.company.framework.util.ImmutablePair;
import com.example.company.framework.util.Pair;
import com.example.company.framework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ClassScanner {

    public <T extends Annotation> Set<Pair<Class<? extends T>, Object>> findAnnotatedClasses(String scanPackage, Class<T> annotationClass) throws ClassNotFoundException, IOException, IllegalAccessException, InstantiationException {
        Set<Pair<Class<? extends T>, Object>> annotations = new HashSet<>();

        ArrayList<Class<?>> classes = ReflectionUtils.getClassesForPackage(scanPackage);

        for (Class<?> aClass : classes) {
            if ( aClass.isAnnotationPresent(annotationClass)) {
                for (Class<?> anInterface : aClass.getInterfaces()) {
                    annotations.add(ImmutablePair.of((Class<? extends T>) anInterface, aClass.newInstance()));
                }
            }
        }
        return annotations;
    }

}


