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

public class SaveListener implements Consumer<Event> {

    private PersistenceService persistenceService;

    public SaveListener(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    @Override
    public void accept(Event event) {
        Scanner in = new Scanner(System.in);
        Session session = GameApplicationContext.INSTANCE.getSession();
        GameState gameState = (GameState) session.get(GameState.class).orElseThrow(() -> new RuntimeException("Game state not set"));
        Village village = gameState.getVillage().orElseThrow(() -> new RuntimeException("Village not set"));
        System.out.println("Enter file absolute path");

        try {
            persistenceService.saveGame(in.nextLine(), village);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
