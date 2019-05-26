package com.example.company.component.listener;

import static com.example.company.component.Movement.DOWN;
import static com.example.company.component.Movement.LEFT;
import static com.example.company.component.Movement.RIGHT;
import static com.example.company.component.Movement.UP;
import static com.example.company.framework.util.RandomUtils.nextInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.company.component.GameState;
import com.example.company.framework.GameApplicationContext;
import com.example.company.fsm.Event;
import com.example.company.model.Village;
import com.example.company.service.GameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

/**
 * Tests for {@link GameListener}.F
 */

@RunWith(MockitoJUnitRunner.class)
public class GameListenerMovementTest {

    @Mock
    private GameService gameService;
    @InjectMocks
    private GameListener gameListener;

    private GameState gameState;
    private Village village;
    @Before
    public void setUp() {
        gameState = mock(GameState.class);

        GameApplicationContext.INSTANCE.getSession().put(GameState.class, gameState);
        village = new Village(nextInt(0, 7), nextInt(0,8));
        when(gameState.getVillage()).thenReturn(Optional.of(village));
    }

    @Test
    public void givenAGameServiceWhenMovementEventsThenHeroIsMoved() {

        gameListener.accept(new Event('e'));
        verify(gameState).moveHero(UP);

        gameListener.accept(new Event('s'));
        verify(gameState).moveHero(LEFT);

        gameListener.accept(new Event('d'));
        verify(gameState).moveHero(RIGHT);

        gameListener.accept(new Event('x'));
        verify(gameState).moveHero(DOWN);

    }

}
