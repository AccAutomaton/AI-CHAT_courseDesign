package com.acautomaton.aichat.service.impl.vipLevel;

import com.acautomaton.aichat.entity.vipLevel.UserVipLevel;
import com.acautomaton.aichat.mapper.UserVipLevelMapper;
import com.acautomaton.aichat.service.inte.vipLevel.UserVipLevelService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserVipLevelServiceImpl extends ServiceImpl<UserVipLevelMapper, UserVipLevel> implements UserVipLevelService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserVipLevel addLevel(UserVipLevel userVipLevel) {
        this.save(userVipLevel);
        return userVipLevel;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLevelByUid(UserVipLevel userVipLevel) {
        this.saveOrUpdate(userVipLevel);
    }

    @Override
    public UserVipLevel getLevelByUid(Integer uid) {
        QueryWrapper<UserVipLevel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        return this.getOne(queryWrapper);
    }
}
