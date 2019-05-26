package com.example.company.component.listener;

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
public class GameListenerClashTest {

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
    public void givenAGameServiceWhenThereIsAClashThenSystemStartsAClash() {

        when(gameService.isAClash(village)).thenReturn(true);
        when(gameService.clashBetweenHeroAndEnemies(village)).thenReturn(true);
        when(gameService.areModeEnemies(village)).thenReturn(true);

        gameListener.accept(new Event('e'));
        verify(gameService).clashBetweenHeroAndEnemies(village);
        verify(gameService).areModeEnemies(village);

    }

}
