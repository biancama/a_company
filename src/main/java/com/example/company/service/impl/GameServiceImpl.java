package com.example.company.service.impl;


import static com.example.company.framework.util.RandomUtils.nextInt;

import com.example.company.framework.ioc.Service;
import com.example.company.model.Entity;
import com.example.company.model.Village;
import com.example.company.model.Weapon;
import com.example.company.service.GameService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class GameServiceImpl implements GameService {
    @Override
    public Village createVillage(int with, int height) {
        Village village = new Village(with, height);
        village.setCharacter(new Entity(0, 0, Weapon.BOMB));
        village.setupEnemies(3);
        return village;
    }

    @Override
    public boolean isAClash(Village village) {
        Entity hero = village.getCharacter();
        for (Entity enemy : village.getEnemies()) {
            if (hero.sameCellOf(enemy)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean clashBetweenHeroAndEnemies(Village village) {
        int i = 0;
        while (i < 3) {
            System.out.println("......");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
        boolean herWon = nextInt(0, 4) > 0;
        if (herWon) {
            removeAnEnemy(village);
        }
        return herWon;
    }

    @Override
    public boolean areModeEnemies(Village village) {
        return village.getEnemies().size() > 0;
    }

    private void removeAnEnemy(Village village) {
        List<Entity> enemies = village.getEnemies();

        for (int i = 0; i < enemies.size(); ++i) {
            if(enemies.get(i).sameCellOf(village.getCharacter())){
                int x = enemies.get(i).getX();
                int y = enemies.get(i).getY();
                enemies.remove(i);
                village.clearPosition(x, y);
                return;
            }
        }
    }
}
