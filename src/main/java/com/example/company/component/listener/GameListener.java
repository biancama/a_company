package com.example.company.component.listener;

import com.example.company.component.GameState;
import com.example.company.component.Movement;
import com.example.company.framework.GameApplicationContext;
import com.example.company.framework.Session;
import com.example.company.fsm.Event;
import com.example.company.model.Village;
import com.example.company.service.GameService;

import java.util.function.Consumer;


public class GameListener implements Consumer<Event> {

    private GameService gameService;

    public GameListener(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void accept(Event event) {
        Session session = GameApplicationContext.INSTANCE.getSession();
        GameState gameState = (GameState) session.get(GameState.class).orElseThrow(() -> new RuntimeException("Game state not set"));
        switch (event.getCharacter()) {
            case 'e':
                gameState.moveHero(Movement.UP);
                break;
            case 's':
                gameState.moveHero(Movement.LEFT);
                break;
            case 'd':
                gameState.moveHero(Movement.RIGHT);
                break;
            case 'x':
                gameState.moveHero(Movement.DOWN);
                break;
            default:
                break;
        }
        Village village = gameState.getVillage().orElseThrow(() -> new RuntimeException("Village not set"));
        if (gameService.isAClash(village)) {
            System.out.println("There is clash");
            boolean heroWon = gameService.clashBetweenHeroAndEnemies(village);
            if (heroWon) {
                System.out.println("You Won the battle!!!!");
                if (!gameService.areModeEnemies (village)) {
                    System.out.println("Thre are no more enemies. You won the war!!!");
                    System.exit(0);
                }
            } else {
                System.out.println("You lost. Bye");
                System.exit(0);
            }
        }

    }
}
