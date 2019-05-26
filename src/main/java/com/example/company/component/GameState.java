package com.example.company.component;

import com.example.company.model.Entity;
import com.example.company.model.Village;

import java.util.List;
import java.util.Observable;
import java.util.Optional;


public class GameState extends Observable {

    private int rows;

    private int columns;

    private Village village;

    private List<String> prompt;


    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public Optional<Village> getVillage() {
        return Optional.ofNullable(village);
    }

    public void setVillage(Village village) {
        this.village = village;
        setChanged();
        notifyObservers();
    }

    public List<String> getPrompt() {
        return prompt;
    }

    public void setPrompt(List<String> prompt) {
        this.prompt = prompt;
        setChanged();
        notifyObservers();
    }

    public void moveHero(Movement movement) {
        Village village = this.getVillage().orElseThrow(() -> new RuntimeException("Village not set"));
        Entity hero = village.getCharacter();
        switch (movement) {
            case UP:
                hero.moveUp();
                break;
            case DOWN:
                hero.moveDown();
                break;
            case RIGHT:
               hero.moveRight();
               break;
            case LEFT:
                hero.moveLeft();
                break;
            default:
                throw new IllegalStateException("Unsupported movement: " + movement);
        }
        setChanged();
        notifyObservers();
    }

}
