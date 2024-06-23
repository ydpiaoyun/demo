package com.yun.demo.other;

import com.yun.demo.event.MyEventListener;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.ResolvableType;

import java.util.HashMap;
import java.util.List;

/**
 * @author: zhangxiaoyun
 * @description:
 * @date: 2023/12/14
 */
@Slf4j
public class SpringTest {

    private HashMap<String, List<Integer>> param;
    @Test
    public void forClassTest() {
        ResolvableType resolvableType = ResolvableType.forClass(MyEventListener.class);
        log.info(resolvableType.getType().toString());
        log.info(resolvableType.resolve().getName());
    }

    @Test
    public void resolvableTypeTest() throws NoSuchFieldException {
        ResolvableType param = ResolvableType.forField(SpringTest.class.getDeclaredField("param"));
        System.out.println("从 HashMap<String, List<Integer>> 中获取 String:" + param.getGeneric(0).resolve());
        System.out.println("从 HashMap<String, List<Integer>> 中获取 List<Integer> :" + param.getGeneric(1));
        System.out.println(
                "从 HashMap<String, List<Integer>> 中获取 List :" + param.getGeneric(1).resolve());
        System.out.println("从 HashMap<String, List<Integer>> 中获取 Integer:" + param.getGeneric(1,0));
        System.out.println("从 HashMap<String, List<Integer>> 中获取父类型:" +param.getSuperType());
    }
}
