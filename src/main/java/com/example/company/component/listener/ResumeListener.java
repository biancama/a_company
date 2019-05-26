package com.example.company.component.listener;

import com.example.company.component.GameState;
import com.example.company.framework.GameApplicationContext;
import com.example.company.framework.Session;
import com.example.company.fsm.Event;
import com.example.company.model.Village;
import com.example.company.service.PersistenceService;

import java.io.IOException;
import java.util.Scanner;
import java.util.function.Consumer;

public class ResumeListener implements Consumer<Event> {

    private PersistenceService persistenceService;

    public ResumeListener(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    @Override
    public void accept(Event event) {
        Scanner in = new Scanner(System.in);
        Session session = GameApplicationContext.INSTANCE.getSession();
        GameState gameState = (GameState) session.get(GameState.class).orElseThrow(() -> new RuntimeException("Game state not set"));
        Village village = null;
        System.out.println("Enter file absolute path");

        try {
            village = persistenceService.resume(in.nextLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameState.setVillage(village);
    }
}
