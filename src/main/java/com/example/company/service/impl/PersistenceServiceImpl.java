package com.example.company.service.impl;

import static java.util.Optional.empty;

import com.example.company.framework.ioc.Service;
import com.example.company.framework.util.ReflectionUtils;
import com.example.company.model.Entity;
import com.example.company.model.Village;
import com.example.company.model.Weapon;
import com.example.company.service.PersistenceService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersistenceServiceImpl implements PersistenceService {
    private static int MIN_NUMBER_OF_LINES = 6;

    @Override
    public void saveGame(String fileName, Village village) throws IOException {
        Path path = Paths.get(fileName);
        List<CharSequence> ser = serialize(village);
        Files.write(path, ser);
    }

    private List<CharSequence> serialize(Village village) {
        List<CharSequence> result = new ArrayList<>();
        result.add(String.valueOf(village.getWidth()));
        result.add(String.valueOf(village.getHeight()));

        Entity hero = village.getCharacter();
        result.add(String.valueOf(hero.getX()));
        result.add(String.valueOf(hero.getY()));
        result.add(String.valueOf(hero.getWeapon()));

        List<Entity> enemies = village.getEnemies();
        result.add(String.valueOf(enemies.size()));
        for (Entity enemy : enemies) {
            result.add(String.valueOf(enemy.getX()));
            result.add(String.valueOf(enemy.getY()));
            result.add(String.valueOf(enemy.getWeapon()));
        }
        return result;
    }

    private Optional<Village> unserialize(List<String> allLines) {

        if (allLines.size() < MIN_NUMBER_OF_LINES) {
            return empty();
        }
        Village result = new Village(Integer.valueOf(allLines.get(0)), Integer.valueOf(allLines.get(1)));
        result.setCharacter(new Entity(Integer.valueOf(allLines.get(2)), Integer.valueOf(allLines.get(3)), Weapon.valueOf(allLines.get(4))));
        int enemiesCount = Integer.valueOf(allLines.get(5));
        List<Entity> enemies = new ArrayList<>();
        for (int i = 0; i < 3 * enemiesCount ; i = i + 3) {
            Entity entity = new Entity(Integer.valueOf(allLines.get(MIN_NUMBER_OF_LINES + i)), Integer.valueOf(allLines.get(MIN_NUMBER_OF_LINES + i + 1)), Weapon.valueOf(allLines.get(MIN_NUMBER_OF_LINES + i + 2)));
            enemies.add(entity);
            result.placeEnemy(entity.getX(), entity.getY(), '*');
        }
        ReflectionUtils.setField("enemies", result, enemies);
        return Optional.of(result);
    }

    @Override
    public Village resume(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        return unserialize(Files.readAllLines(path)).orElseThrow(() -> new RuntimeException("File not valid"));
    }
}
