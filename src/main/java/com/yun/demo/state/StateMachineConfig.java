package com.yun.demo.state;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

/**
 @description: 完成状态机的配置，
 包括：（1）状态机的初始状态和所有状态；（2）状态之间的转移规则
*/
@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states.withStates().initial(States.DRAFT).states(EnumSet.allOf(States.class));
//        states.withStates().initial(States.DRAFT).end(States.PUBLISH_DONE).states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions.withExternal()
            .source(States.DRAFT).target(States.PUBLISH_TODO)
            .event(Events.ONLINE)
            .and()
            .withExternal()
            .source(States.PUBLISH_TODO).target(States.PUBLISH_DONE)
            .event(Events.PUBLISH)
            .and()
            .withExternal()
            .source(States.PUBLISH_DONE).target(States.DRAFT)
            .event(Events.ROLLBACK);
    }
}