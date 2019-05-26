package com.example.company.model;

import java.util.Objects;

public class Entity {
    private int x;
    private int y;

    private Weapon weapon;

    public Entity(int x, int y, Weapon weapon) {
        this.x = x;
        this.y = y;
        this.weapon = weapon;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }

    public void moveUp() {
        y--;
    }

    public void moveDown() {
        y++;
    }

    public boolean sameCellOf(Entity enemy) {
        return x == enemy.getX() && y == enemy.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return x == entity.x &&
            y == entity.y &&
            weapon == entity.weapon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, weapon);
    }
}
