package com.example.company.framework;


public interface ApplicationContext {
    void startUp();

    Session getSession();

    void run();
}
