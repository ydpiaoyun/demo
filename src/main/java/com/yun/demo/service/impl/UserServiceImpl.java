package com.yun.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.yun.demo.entity.User;
import com.yun.demo.enums.CacheConstants;
import com.yun.demo.enums.StateEnum;
import com.yun.demo.mapper.UserMapper;
import com.yun.demo.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    /**
     * value：缓存key的前缀。
     * key：缓存key的后缀。
     * sync：设置如果缓存过期是不是只放一个请求去请求数据库，其他请求阻塞，默认是false（根据个人需求）。
     * unless：不缓存空值,这里不使用，会报错
     * 查询用户信息类
     * 如果需要加自定义字符串，需要用单引号
     * 如果查询为null，也会被缓存
     */
//    @Cacheable(value = CacheConstants.GET_USER,key = "'user'+#userId",sync = true)
    @Cacheable(value = CacheConstants.GET_USER,key = "#userId",sync = true)
    public User getUserByUserId(Integer userId){
        User user = userMapper.selectById(userId);
        System.out.println("查询了数据库");
        return user;
    }

    @Override
    public List<User> queryUsers(String name, Integer minAge, Integer maxAge) {
       return lambdaQuery().like(StringUtils.isNotBlank(name),User::getName, name)
                .ge(minAge != null,User::getAge, minAge)
                .le(maxAge != null,User::getAge, maxAge)
                .list();
    }

    @Override
    public void updateAgeById(String id, Integer age) {
        Integer orgAge = getById(id).getAge();
        Integer nowAge = orgAge + age;
        lambdaUpdate().set(User::getAge,nowAge)
                .set(nowAge>150,User::getStatus, StateEnum.FREEZE)
                .eq(User::getId,id)
                .eq(User::getAge,orgAge)   // 乐观锁
                .update();
    }

    @Override
    public User getByStatic(Long id) {
       return Db.lambdaQuery(User.class).eq(User::getId, id).one();
    }


}
