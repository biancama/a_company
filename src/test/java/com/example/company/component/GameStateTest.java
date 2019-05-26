package com.example.company.component;

import static com.example.company.component.Movement.DOWN;
import static com.example.company.component.Movement.LEFT;
import static com.example.company.component.Movement.RIGHT;
import static com.example.company.component.Movement.UP;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.example.company.framework.util.RandomUtils;
import com.example.company.model.Entity;
import com.example.company.model.Village;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for {@link GameState}.F
 */

@RunWith(MockitoJUnitRunner.class)
public class GameStateTest {
    @Spy
    private GameState gameState;

    @Test
    public void givenAGameWhenVillageIsSetThenObserversAreNotified() {
        Village village = new Village(RandomUtils.nextInt(0, 3), RandomUtils.nextInt(0, 4));
        gameState.setVillage(village);
        verify(gameState).notifyObservers();
    }

    @Test
    public void givenAGameWhenPromptIsSetThenObserversAreNotified() {
        gameState.setPrompt(anyList());
        verify(gameState).notifyObservers();
    }

    @Test
    public void givenAGameWhenMoveThenHeroIsMoved() {
        Village village = new Village(RandomUtils.nextInt(0, 3), RandomUtils.nextInt(0, 4));
        Entity hero = mock(Entity.class);
        village.setCharacter(hero);
        gameState.setVillage(village);

        gameState.moveHero(UP);
        verify(hero).moveUp();
        gameState.moveHero(DOWN);
        verify(hero).moveDown();
        gameState.moveHero(LEFT);
        verify(hero).moveLeft();
        gameState.moveHero(RIGHT);
        verify(hero).moveRight();
    }

    @Test
    public void givenAGameWithoutAVillageWhenHeroIsmovedThenAnExceptionIsThrown() {
        assertThatThrownBy(() -> {
            gameState.moveHero(Movement.values()[RandomUtils.nextInt(0, Movement.values().length)]);
        }).isInstanceOf(RuntimeException.class)
            .hasMessage("Village not set");
    }

}
