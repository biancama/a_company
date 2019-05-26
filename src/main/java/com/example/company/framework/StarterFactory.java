package com.example.company.framework;


public class StarterFactory {
    private StarterFactory() {
    }

    public static void startEntryPoint() {
        GameApplicationContext.INSTANCE.startUp();
        GameApplicationContext.INSTANCE.run();
    }
}