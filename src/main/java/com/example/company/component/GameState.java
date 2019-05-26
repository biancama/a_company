package com.example.company.component;

import java.util.List;
import java.util.Observable;


public class GameState extends Observable {

    private int rows;

    private int columns;

    private List<List<Character>> village;

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

    public List<List<Character>> getVillage() {
        return village;
    }

    public void setVillage(List<List<Character>> village) {
        this.village = village;
    }

    public List<String> getPrompt() {
        return prompt;
    }

    public void setPrompt(List<String> prompt) {
        this.prompt = prompt;
        setChanged();
        notifyObservers();
    }

    public String villageToString()  {
        StringBuilder stringBuilder = new StringBuilder();
        for (List<Character> characters : village) {
            for (Character character : characters) {
                stringBuilder.append(character);
                stringBuilder.append(System.getProperty("line.separator"));
            }
        }
        return stringBuilder.toString();
    }

}
