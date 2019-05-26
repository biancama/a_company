package com.example.company.model;


import static com.example.company.framework.util.RandomUtils.nextInt;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Village {
    private List<List<Character>> area;
    private int width;
    private int height;
    private Entity hero;
    private List<Entity> enemies;

    public void setCharacter(Entity character) {
        this.hero = character;
    }

    public Entity getCharacter() {
        return this.hero;
    }

    public List<Entity> getEnemies() {
        return enemies;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Entity getHero() {
        return hero;
    }

    public void setupEnemies(int n) {
        enemies = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            int y = nextInt(1, height);
            int x = nextInt(1, width);
            enemies.add(new Entity(x, y, Weapon.CROSSBOW));
            placeEnemy(x, y, '*');
        }
    }

    public void placeEnemy(int x, int y,  char c) {
        area.get(y).set(x, c);
    }

    public void clearPosition (int x, int y) {
        for (Entity enemy : getEnemies()) {
            if (x == enemy.getX() && y == enemy.getY()) {
                return;  // extra check if there are more enemies on the same cell
            }
        }
        placeEnemy(x, y, ' ');
    }

    public Village(int width, int height) {
        area = new ArrayList<>(height);
        this.width = width;
        this.height = height;

        for (int i=0; i < height; ++i) {
            area.add(new ArrayList<>(width));
            for (int j = 0; j < width; j++) {
                area.get(i).add(' ');
            }
        }
    }

    @Override
    public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < width + 2; i++) {
            stringBuilder.append('-');
        }
        stringBuilder.append(System.getProperty("line.separator"));

        int f = 0;
        for (List<Character> characters : area) {
            int k = 0;
            stringBuilder.append('|');
            for (Character character : characters) {
                if (hero != null && k == hero.getX() && f == hero.getY()) {
                    stringBuilder.append('x');
                } else {
                    stringBuilder.append(character);
                }
                k++;
            }
            f++;
            stringBuilder.append('|');
            stringBuilder.append(System.getProperty("line.separator"));
        }
        for (int i = 0; i < width + 2; i++) {
            stringBuilder.append('-');
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Village village = (Village) o;
        return width == village.width &&
            height == village.height &&
            Objects.equals(hero, village.hero) &&
            Objects.equals(enemies, village.enemies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height, hero, enemies);
    }
}
