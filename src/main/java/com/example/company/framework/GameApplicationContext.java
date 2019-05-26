package com.example.company.framework;


import com.example.company.component.GameState;
import com.example.company.component.GameStateMachine;
import com.example.company.framework.ioc.IotSetup;
import com.example.company.view.ShowOptionsView;

import java.util.Scanner;

public enum  GameApplicationContext implements ApplicationContext {
    INSTANCE;

    @Override
    public void startUp() {
        IotSetup iotSetup = new IotSetup();

        GameStateMachine gameStateMachine = new GameStateMachine();
        iotSetup.setAutoWire(gameStateMachine);
        gameStateMachine.setUpMachine();
        getSession().put(GameStateMachine.class, gameStateMachine);
        GameState gameState = new GameState();
        gameState.addObserver(new ShowOptionsView());
        getSession().put(GameState.class, gameState);
    }

    @Override
    public Session getSession() {
        return Session.INSTANCE;
    }

    @Override
    public void run() {
        GameStateMachine gameStateMachine = (GameStateMachine) getSession().get(GameStateMachine.class).orElseThrow(() -> new  RuntimeException("Game state machine Not created"));
        Scanner in = new Scanner(System.in);
        GameState gameState = (GameState) getSession().get(GameState.class).orElseThrow(() -> new  RuntimeException("Game state  Not created"));
        while (!gameStateMachine.isFinal()) {
            gameState.setPrompt(gameStateMachine.getOptions());
            Character nextCommand = in.next().charAt(0);
            gameStateMachine.event(nextCommand);
        }
    }
}
