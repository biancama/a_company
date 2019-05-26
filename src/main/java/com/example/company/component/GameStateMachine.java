package com.example.company.component;


import static java.lang.String.format;

import com.example.company.component.listener.InsertWidthListener;
import com.example.company.fsm.Event;
import com.example.company.fsm.State;
import com.example.company.fsm.StateMachine;
import com.example.company.fsm.Transition;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GameStateMachine {

    private final StateMachine stateMachine;

    public GameStateMachine() {
        State start = new State("start");
        State end = new State("exit");
        State create = new State("create");
        State rowState = new State("row");
        State columnState = new State("column");
        Set<State> states = new HashSet<State>() {{
            add(start);
            add(create);
            add(end);
            add(rowState);
            add(columnState);
        }};

        Transition createTransition = Transition.builder()
            .source(start).target(create).event(new Event('1'))
            .name("create")
            .build();

        Transition exitTransition01 = Transition.builder()
            .source(start).target(end).event(new Event('2'))
            .name("exit")
            .build();

        Transition exitTransition02 = Transition.builder()
            .source(create).target(end).event(new Event('2'))
            .name("exit")
            .build();

        Transition exitTransition03 = Transition.builder()
            .source(rowState).target(end).event(new Event('2'))
            .name("exit")
            .build();

        Transition rowTransition = Transition.builder()
            .source(create).target(rowState).event(new Event('1'))
            .name("insert Village width")
            .actionListener(new InsertWidthListener())
            .build();

        Transition columnTransition = Transition.builder()
            .source(rowState).target(columnState).event(new Event('1'))
            .name("insert Village height")
            .actionListener(new InsertWidthListener())
            .build();


        stateMachine = StateMachine.builder(states)
            .withInitialState(start)
            .addFinalState(end)
            .withTransition(createTransition)
            .withTransition(exitTransition01)
            .withTransition(rowTransition)
            .withTransition(exitTransition02)
            .withTransition(columnTransition)
            .withTransition(exitTransition03)
            .build();
    }

    public List<String> getOptions() {
        return stateMachine.getCurrentTransitions().stream().sorted(Comparator.comparing(o -> o.getEvent().getCharacter()))
            .map(t -> format("%c. %s", t.getEvent().getCharacter().charValue(), t.getName()))
            .collect(Collectors.toList());
    }

    public boolean isFinal() {
        return stateMachine.getCurrent().isFinal();
    }

    public State event(Character c) {
        return stateMachine.fire(new Event(c));
    }
}
