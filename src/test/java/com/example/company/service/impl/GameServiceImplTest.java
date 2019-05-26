package com.example.company.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.company.model.Village;
import com.example.company.service.GameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for {@link GameServiceImpl}.F
 */
@RunWith(MockitoJUnitRunner.class)
public class GameServiceImplTest {

    @InjectMocks
    private GameService gameService = new GameServiceImpl();

    @Test
    public void whenCreateVillageThenHeroOnTopLeftPositionIsPlaced() {
        Village village =  gameService.createVillage(5, 4);
        assertThat(village.getCharacter().getX()).isEqualTo(0);
        assertThat(village.getCharacter().getY()).isEqualTo(0);
        assertThat(village.getEnemies().size()).isEqualTo(3);
    }
}
