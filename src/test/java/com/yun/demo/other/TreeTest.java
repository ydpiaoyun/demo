package com.yun.demo.other;

import com.yun.demo.vo.SysMenu;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: zhangxiaoyun
 * @description:
 * @date: 2023/9/14
 */
@Slf4j
public class TreeTest {

    @Test
    public void treeTest() {
        //查询出所有菜单
        List<SysMenu> sysMenus = genData();
        //利用父节点分组
        Map<Integer, List<SysMenu>> listMap = sysMenus.parallelStream().collect(Collectors.groupingBy(SysMenu::getPid));
        sysMenus.forEach(val -> val.setChildren(listMap.get(val.getId())));
        List<SysMenu> rootMenus = sysMenus.stream().filter(val -> val.getPid() == NumberUtils.INTEGER_ZERO).collect(Collectors.toList());
        log.info(rootMenus.toString());
    }

    private List<SysMenu> genData() {
        ArrayList<SysMenu> menus = new ArrayList<>();
        return menus;
    }

}
