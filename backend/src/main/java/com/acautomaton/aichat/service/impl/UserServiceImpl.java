package com.acautomaton.aichat.service.impl;

import com.acautomaton.aichat.entity.User;
import com.acautomaton.aichat.mapper.UserMapper;
import com.acautomaton.aichat.service.inte.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getUserByUid(String uid) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        return this.getOne(queryWrapper);
    }

    @Override
    public User getUserByUid(Integer uid) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        return this.getOne(queryWrapper);
    }

    @Override
    public User getUserByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return this.getOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User registerUser(User user) {
        this.save(user);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user) {
        this.saveOrUpdate(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBalanceByUid(User user) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("uid", user.getUid());
        updateWrapper.set("balance", user.getBalance());
        updateWrapper.set("update_time", user.getUpdateTime());
        this.update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserByUid(User user) {
        this.removeById(user);
    }
}
