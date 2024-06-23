package com.yun.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.demo.annotation.RequestLog;
import com.yun.demo.entity.User;
import com.yun.demo.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * ClassName: DemoController <br/>
 * Description: <br/>
 * date: 2023/5/27 16:01<br/>
 *
 * @author zxy<br />
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @GetMapping("{currentPage}/{pageSize}")
    public Page<User> pageSearch(@PathVariable("currentPage") long currentPage, @PathVariable("pageSize") long pageSize) {
        Page<User> pageData = userService.page(Page.of(currentPage, pageSize));
        // 判断当前页是否大于总页数，如果大于则将当前页设置为总页数并重新获取分页数据
        if(currentPage > pageData.getPages()){
            pageData = userService.page(Page.of(pageData.getPages(), pageSize));
        }
        return pageData;
    }

    @GetMapping("/desensi/{id}")
    @RequestLog("解密用户，Version = {{ @userController.version }}，用户ID={{ #id }}，UserAgent={{ #request.getHeader('User-Agent') }}")
    public User desensitization(@PathVariable Long id){
        return userService.getById(id);
    }
}
