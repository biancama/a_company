package com.example.company.component.listener;


import com.example.company.component.GameState;
import com.example.company.framework.GameApplicationContext;
import com.example.company.framework.Session;
import com.example.company.fsm.Event;
import com.example.company.service.GameService;

import java.util.Scanner;
import java.util.function.Consumer;

public class InsertHeightListener implements Consumer<Event> {

    private GameService gameService;

    public InsertHeightListener(GameService gameService) {
        this.gameService = gameService;
    }
    @Override
    public void accept(Event event) {
        Scanner in = new Scanner(System.in);
        System.out.println("Height of the village? (number of cells)");
        int height = in.nextInt();
        Session session = GameApplicationContext.INSTANCE.getSession();
        GameState gameState = (GameState) session.get(GameState.class).orElseThrow(() -> new RuntimeException("Game state not set"));
        gameState.setColumns(height);
        gameState.setVillage(gameService.createVillage(gameState.getRows(), gameState.getColumns()));
    }
}
