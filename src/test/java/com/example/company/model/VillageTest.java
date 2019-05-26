package com.example.company.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.util.List;

/**
 * Tests for {@link Village}.F
 */
public class VillageTest {

    @Test
    public void givenAVillageWhenIsRepresentedThenItIsDoneWithProperSymbols() {
        Village village = new Village(5, 4);
        final String villageEmpty =
            "-------\n" +
            "|     |\n" +
            "|     |\n" +
            "|     |\n" +
            "|     |\n" +
            "-------";

        assertThat(village.toString()).isEqualTo(villageEmpty);
        final String villageWithHero =
            "-------\n" +
            "|x    |\n" +
            "|     |\n" +
            "|     |\n" +
            "|     |\n" +
            "-------";

        village.setCharacter(new Entity(0, 0, Weapon.CROSSBOW));
        assertThat(village.toString()).isEqualTo(villageWithHero);

        village.setupEnemies(3);

        List<Entity> mockEnemies = village.getEnemies();

        String villageWithEnemies =
            "-------\n" +
            "|x    |\n" +
            "|     |\n" +
            "|     |\n" +
            "|     |\n" +
            "-------";
        StringBuilder stringBuilder = new StringBuilder(villageWithEnemies);
        for (Entity mockEnemy : mockEnemies) {
            int position = 8 * ( mockEnemy.getY() + 1 ) + mockEnemy.getX() + 1;
            stringBuilder.replace(position, position + 1, "*");
        }

        assertThat(village.toString()).isEqualTo(stringBuilder.toString());
    }
}
