package com.example.company.fsm;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.company.component.listener.InsertWidthListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * Tests for {@link StateMachine}.F
 */
@RunWith(MockitoJUnitRunner.class)
public class StateMachineTest {
    private StateMachine stateMachine;
    private Transition createTransition, exitTransition01;
    private State create;

    @Before
    public void setUp() {
        State start = new State("start");
        State end = new State("exit");
        create = new State("create");
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

         createTransition = Transition.builder()
            .source(start).target(create).event(new Event('1'))
            .name("create")
            .build();

        exitTransition01 = Transition.builder()
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

        Transition exitTransition04 = Transition.builder()
            .source(columnState).target(end).event(new Event('2'))
            .name("exit")
            .build();
        Transition exitTransition05 = Transition.builder()
            .source(gameState).target(end).event(new Event('1'))
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
            .build();

        Transition gameTransition = Transition.builder()
            .source(columnState).target(gameState).event(new Event('1'))
            .name("start game")
            .build();

        Transition upTransition = Transition.builder()
            .source(gameState).target(gameState).event(new Event('e'))
            .name("up")
            .build();

        Transition leftTransition = Transition.builder()
            .source(gameState).target(gameState).event(new Event('s'))
            .name("left")
            .build();

        Transition rightTransition = Transition.builder()
            .source(gameState).target(gameState).event(new Event('d'))
            .name("right")
            .build();

        Transition downTransition = Transition.builder()
            .source(gameState).target(gameState).event(new Event('x'))
            .name("down")
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
            .build();
    }


    @Test
    public void givenAFSMWithACurrentStateThenAllPossibleTransitionsAreReturned() {

        assertThat(stateMachine.getCurrentTransitions()).containsExactlyInAnyOrder(createTransition, exitTransition01);

    }

    @Test
    public void givenAFSMWithACurrentStateThenFireAnEventWillRetrunTheNextState() {

        assertThat(stateMachine.fire(new Event('1'))).isEqualTo(create);

    }

}
