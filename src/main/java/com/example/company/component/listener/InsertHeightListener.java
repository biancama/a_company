package com.example.company.component.listener;


import com.example.company.component.GameState;
import com.example.company.framework.GameApplicationContext;
import com.example.company.framework.Session;
import com.example.company.fsm.Event;

import java.util.Scanner;
import java.util.function.Consumer;

public class InsertHeightListener implements Consumer<Event> {

    @Override
    public void accept(Event event) {
        Scanner in = new Scanner(System.in);
        System.out.println("Height of the village? (number of cells)");
        int height = in.nextInt();
        Session session = GameApplicationContext.INSTANCE.getSession();
        GameState gameState = (GameState) session.get(GameState.class).orElseThrow(() -> new RuntimeException("Game state not set"));
        gameState.setColumns(height);
    }
}
