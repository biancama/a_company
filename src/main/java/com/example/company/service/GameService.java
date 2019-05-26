package com.example.company.service;


import com.example.company.model.Village;

public interface GameService {
    Village createVillage(int with, int height);

    boolean isAClash(Village village);

    boolean clashBetweenHeroAndEnemies(Village village);

    boolean areModeEnemies(Village village);
}
