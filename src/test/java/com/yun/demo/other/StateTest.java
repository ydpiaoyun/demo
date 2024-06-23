package com.yun.demo.other;

import com.yun.demo.state.Events;
import com.yun.demo.state.States;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;

import javax.annotation.Resource;

@SpringBootTest
public class StateTest {

    @Resource
    StateMachine<States, Events> stateMachine;

    @Test
    public void stateTest() {
        stateMachine.start();
        stateMachine.sendEvent(Events.ONLINE);
        stateMachine.sendEvent(Events.PUBLISH);
        stateMachine.sendEvent(Events.ROLLBACK);
    }
}