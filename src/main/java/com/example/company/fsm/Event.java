package com.example.company.fsm;

import java.util.Objects;


public class Event {
    public Character getCharacter() {
        return character;
    }

    private final Character character;

    public Event(Character character) {
        this.character = character;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(character, event.character);
    }

    @Override
    public int hashCode() {
        return Objects.hash(character);
    }
}
