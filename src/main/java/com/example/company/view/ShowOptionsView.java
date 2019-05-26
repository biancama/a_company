package com.example.company.view;


import com.example.company.component.GameState;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ShowOptionsView implements Observer {

    public void promptOptions(List<String> options) {
        options.forEach(System.out::println);
    }

    @Override
    public void update(Observable o, Object arg) {
        GameState gameState = (GameState) o;
        gameState.getPrompt().forEach(System.out::println);

        gameState.getVillage()
            .ifPresent(v -> System.out.println(v));
    }
}
