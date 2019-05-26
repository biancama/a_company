package com.example.company.fsm;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class StateMachine {

    private State current;
    private State initial;
    private Set<State> finals;
    private final Set<State> states;
    private Set<Transition> transitions;

    private StateMachine(Set<State> states, StateMachineBuilder stateMachineBuilder) {
        this.states = states;
        initial = stateMachineBuilder.initial;
        current = initial;
        finals = stateMachineBuilder.finals;
        transitions = stateMachineBuilder.transitions;
    }

    public static StateMachineBuilder builder(Set<State> states) {
        return new StateMachineBuilder(states);
    }

    public Set<Transition> getCurrentTransitions() {
        return transitions.stream().filter(t-> t.getSource().equals(current)).collect(Collectors.toSet());
    }

    public State getCurrent() {
        return current;
    }

    public synchronized State fire(final Event event) {
        Transition transition = getCurrentTransitions().stream()
            .filter(t -> t.getEvent().equals(event))
            .findAny().orElseThrow(() -> new RuntimeException("Fired an event not possible for the current state"));

        current = transition.getTarget();

        transition.getActionListener()
            .ifPresent(action -> action.accept(transition.getEvent()));

        return current;
    }

    public static class StateMachineBuilder {
        private State initial;
        private Set<State> finals = new HashSet<>();
        private final Set<State> states;
        private Set<Transition> transitions = new HashSet<>();

        public StateMachineBuilder(Set<State> states) {
            this.states = states;
        }

        public StateMachineBuilder withInitialState(State state) {
            initial = state;
            return this;
        }

        public StateMachineBuilder addFinalState(State state) {
            state.setFinal(true);
            finals.add(state);
            return this;
        }

        public StateMachineBuilder withTransition(Transition transition) {
            transitions.add(transition);
            return this;
        }

        public StateMachine build() {
            return new StateMachine(states, this);
        }
    }


}

