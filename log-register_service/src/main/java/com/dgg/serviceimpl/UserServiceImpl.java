package com.dgg.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dgg.api.IUserService;
import com.dgg.dao.UserMapper;
import com.dgg.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public User queryByusername(String name) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",name);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public void addUser(User user) {
        System.out.println(user);
        int count = userMapper.insert(user);
    }

    @Override
    public User queryByuser(User user) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",user.getUsername());
        queryWrapper.eq("password",user.getPassword());
        User u = userMapper.selectOne(queryWrapper);
        return u;
    }

    @Override
    public int updatePassword(User user) {
        int count = userMapper.updateById(user);
        return count;
    }
}
