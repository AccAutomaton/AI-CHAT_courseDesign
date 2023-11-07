package com.acautomaton.aichat.service.impl.vipLevel;

import com.acautomaton.aichat.entity.vipLevel.VipLevelDescription;
import com.acautomaton.aichat.mapper.VipLevelDescriptionMapper;
import com.acautomaton.aichat.service.inte.vipLevel.VipLevelDescriptionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VipLevelDescriptionServiceImpl extends ServiceImpl<VipLevelDescriptionMapper, VipLevelDescription> implements VipLevelDescriptionService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public VipLevelDescription addDescription(VipLevelDescription vipLevelDescription) {
        this.save(vipLevelDescription);
        return vipLevelDescription;
    }

    @Override
    public void deleteDescriptionById(Integer id) {
        this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDescriptionById(VipLevelDescription vipLevelDescription) {
        this.saveOrUpdate(vipLevelDescription);
    }

    @Override
    public VipLevelDescription getDescriptionById(Integer id) {
        QueryWrapper<VipLevelDescription> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        return this.getOne(queryWrapper);
    }

    @Override
    public VipLevelDescription getDescriptionByName(String name) {
        QueryWrapper<VipLevelDescription> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("description", name);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<VipLevelDescription> getDescriptions() {
        QueryWrapper<VipLevelDescription> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        return this.list(queryWrapper);
    }
}
