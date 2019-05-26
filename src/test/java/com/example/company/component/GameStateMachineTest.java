package com.example.company.component;

import static com.example.company.framework.util.RandomUtils.nextBoolean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.company.fsm.Event;
import com.example.company.fsm.State;
import com.example.company.fsm.StateMachine;
import com.example.company.fsm.Transition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Tests for {@link GameStateMachine}.F
 */

@RunWith(MockitoJUnitRunner.class)
public class GameStateMachineTest {

    @Mock
    private StateMachine stateMachine;
    @InjectMocks
    private GameStateMachine gameStateMachine;

    @Test
    public void givenAStateMachineWithMultipleTransitionsWhenGameStateMachineOptionsThenEventsAreSorted() {
        Event event01 = new Event('b');
        Event event02 = new Event('a');
        Transition transition01 = Transition.builder().name("AAAA").event(event01).build();
        Transition transition02 = Transition.builder().name("BBB").event(event02).build();

        when(stateMachine.getCurrentTransitions()).thenReturn(Stream.of(transition01, transition02).collect(Collectors.toSet()));

        List<String> options =  gameStateMachine.getOptions();

        assertThat(options).containsExactly("a. BBB", "b. AAAA");
    }

    @Test
    public void givenACurrentStateFinalThenItIsReturned () {
        boolean isFinal = nextBoolean();
        State state = new State("test");
        state.setFinal(isFinal);
        when(stateMachine.getCurrent()).thenReturn(state);

        assertThat(gameStateMachine.isFinal()).isEqualTo(isFinal);
    }

    @Test
    public void whenEventIsCalledThenItIsFiredToFSM() {
        Event event = new Event('a');
        when(stateMachine.fire(any(Event.class))).thenReturn(new State("test"));

        gameStateMachine.event('a');

        verify(stateMachine).fire(event);
    }
}
