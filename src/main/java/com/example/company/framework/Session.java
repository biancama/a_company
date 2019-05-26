package com.example.company.framework;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public enum Session {
    INSTANCE;
    private Map<Class<? extends Object>, Object> map = new HashMap<>();

    public void put(Class<? extends Object> key, Object obj) {
        map.put(key, obj);
    }

    public Optional<Object> get(Class<? extends Object> key) {
        return Optional.ofNullable(map.get(key));
    }
}
