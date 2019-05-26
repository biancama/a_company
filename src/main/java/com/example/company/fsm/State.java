package com.example.company.fsm;

import java.util.Objects;

public class State {

    private final String name;

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    private boolean isFinal;

    public State( String name) {
        this.name = name;
        this.isFinal = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(name, state.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
