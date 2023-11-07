package com.acautomaton.aichat.service.impl;


import com.acautomaton.aichat.entity.UserDetail;
import com.acautomaton.aichat.mapper.UserDetailMapper;
import com.acautomaton.aichat.service.inte.UserDetailService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImpl extends ServiceImpl<UserDetailMapper, UserDetail> implements UserDetailService {
    @Override
    public List<UserDetail> getUsers() {
        QueryWrapper<UserDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("uid");
        return this.list(queryWrapper);
    }
}
