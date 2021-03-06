package com.example.company.component;


import static java.lang.String.format;

import com.example.company.component.listener.GameListener;
import com.example.company.component.listener.InsertHeightListener;
import com.example.company.component.listener.InsertWidthListener;
import com.example.company.component.listener.ResumeListener;
import com.example.company.component.listener.SaveListener;
import com.example.company.framework.ioc.AutoWire;
import com.example.company.fsm.Event;
import com.example.company.fsm.State;
import com.example.company.fsm.StateMachine;
import com.example.company.fsm.Transition;
import com.example.company.service.GameService;
import com.example.company.service.PersistenceService;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GameStateMachine {

    private StateMachine stateMachine;

    @AutoWire
    private GameService gameService;

    @AutoWire
    private PersistenceService persistenceService;

    public void setUpMachine() {
        State start = new State("start");
        State end = new State("exit");
        State create = new State("create");
        State rowState = new State("row");
        State columnState = new State("column");
        State gameState = new State("game");
        Set<State> states = new HashSet<State>() {{
            add(start);
            add(create);
            add(end);
            add(rowState);
            add(columnState);
            add(gameState);
        }};

        Transition createTransition = Transition.builder()
            .source(start).target(create).event(new Event('1'))
            .name("create")
            .build();

        Transition resumeTransition = Transition.builder()
            .source(start).target(gameState).event(new Event('2'))
            .name("resume")
            .actionListener(new ResumeListener(persistenceService))
            .build();

        Transition exitTransition01 = Transition.builder()
            .source(start).target(end).event(new Event('3'))
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

        Transition exitTransition04 = Transition.builder()
            .source(columnState).target(end).event(new Event('2'))
            .name("exit")
            .build();
        Transition exitTransition05 = Transition.builder()
            .source(gameState).target(end).event(new Event('2'))
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
            .actionListener(new InsertHeightListener(gameService))
            .build();

        Transition gameTransition = Transition.builder()
            .source(columnState).target(gameState).event(new Event('1'))
            .name("start game")
            .build();

        Transition upTransition = Transition.builder()
            .source(gameState).target(gameState).event(new Event('e'))
            .name("up")
            .actionListener(new GameListener(gameService))
            .build();

        Transition leftTransition = Transition.builder()
            .source(gameState).target(gameState).event(new Event('s'))
            .name("left")
            .actionListener(new GameListener(gameService))
            .build();

        Transition rightTransition = Transition.builder()
            .source(gameState).target(gameState).event(new Event('d'))
            .name("right")
            .actionListener(new GameListener(gameService))
            .build();

        Transition downTransition = Transition.builder()
            .source(gameState).target(gameState).event(new Event('x'))
            .name("down")
            .actionListener(new GameListener(gameService))
            .build();

        Transition saveTransition = Transition.builder()
            .source(gameState).target(end).event(new Event('1'))
            .name("save")
            .actionListener(new SaveListener(persistenceService))
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
            .withTransition(gameTransition)
            .withTransition(exitTransition04)
            .withTransition(upTransition)
            .withTransition(downTransition)
            .withTransition(leftTransition)
            .withTransition(rightTransition)
            .withTransition(exitTransition05)
            .withTransition(saveTransition)
            .withTransition(resumeTransition)
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
