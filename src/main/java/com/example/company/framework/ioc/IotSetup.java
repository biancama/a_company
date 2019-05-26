package com.example.company.framework.ioc;

import com.example.company.component.GameStateMachine;
import com.example.company.framework.util.Pair;
import com.example.company.framework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Set;

public class IotSetup {

    public void setAutoWire(GameStateMachine gameStateMachine) {
        ClassScanner classScanner = new ClassScanner();
        Set<Pair<Class<? extends Service>, Object>> annotatedClasses = null;
        try {
            annotatedClasses =  classScanner.findAnnotatedClasses("com.example.company.service", Service.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        for (Field field : ReflectionUtils.getAllPrivateFields(gameStateMachine.getClass())) {
            if (field.isAnnotationPresent(AutoWire.class)) {
                Object service = findServiceByType(field.getType(), annotatedClasses);
                ReflectionUtils.setField(field, gameStateMachine, service);
            }
        }
    }

    private Object findServiceByType(Class<?> aClass, Set<Pair<Class<? extends Service>, Object>> annotatedClasses) {
        if (annotatedClasses == null){
            return null;
        }
        for (Pair<Class<? extends Service>, Object> annotatedClass : annotatedClasses) {
            Class<? extends Service> service = annotatedClass.getLeft();
            if (service.getCanonicalName().equals(aClass.getCanonicalName()))
                return annotatedClass.getValue();
        }
        return null;
    }
}
