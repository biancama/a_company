package com.example.company.fsm;


import java.util.Optional;
import java.util.function.Consumer;

final public class Transition {

    private String name;
    private State source;

    private Optional<Consumer<Event>> actionListener;

    public Optional<Consumer<Event>> getActionListener() {
        return actionListener;
    }

    public String getName() {
        return name;
    }

    public State getSource() {
        return source;
    }

    public State getTarget() {
        return target;
    }

    public Event getEvent() {
        return event;
    }

    private State target;
    private Event event;

    private Transition(TransitionBuilder transitionBuilder) {
        name = transitionBuilder.name;
        source = transitionBuilder.source;
        target = transitionBuilder.target;
        event = transitionBuilder.event;
        actionListener = Optional.ofNullable(transitionBuilder.actionListener);
    }

    public static TransitionBuilder builder() {
        return new TransitionBuilder();
    }
    public static class TransitionBuilder {
        private String name;
        private State source;
        private State target;
        private Event event;
        private Consumer<Event> actionListener;

        public TransitionBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TransitionBuilder source(State source) {
            this.source = source;
            return this;
        }

        public TransitionBuilder target(State target) {
            this.target = target;
            return this;
        }

        public TransitionBuilder event(Event event) {
            this.event = event;
            return this;
        }
        public TransitionBuilder actionListener(Consumer<Event> actionListener) {
            this.actionListener = actionListener;
            return this;
        }

        public Transition build() {
            return new Transition(this);
        }
    }
}
