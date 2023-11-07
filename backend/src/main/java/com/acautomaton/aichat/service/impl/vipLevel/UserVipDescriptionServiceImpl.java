package com.acautomaton.aichat.service.impl.vipLevel;

import com.acautomaton.aichat.entity.vipLevel.UserVipDescription;
import com.acautomaton.aichat.mapper.UserVipDescriptionMapper;
import com.acautomaton.aichat.service.inte.vipLevel.UserVipDescriptionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserVipDescriptionServiceImpl extends ServiceImpl<UserVipDescriptionMapper, UserVipDescription> implements UserVipDescriptionService {
    @Override
    public UserVipDescription getUserVipDescriptionByUid(Integer uid) {
        QueryWrapper<UserVipDescription> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        return this.getOne(queryWrapper);
    }
}
