package com.acautomaton.aichat.service.impl;

import com.acautomaton.aichat.entity.Recharge;
import com.acautomaton.aichat.mapper.RechargeMapper;
import com.acautomaton.aichat.service.inte.RechargeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RechargeServiceImpl extends ServiceImpl<RechargeMapper, Recharge> implements RechargeService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Recharge addRecharge(Recharge recharge) {
        this.save(recharge);
        return recharge;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRechargeById(Integer id) {
        this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRechargeById(Recharge recharge) {
        this.saveOrUpdate(recharge);
    }

    @Override
    public Recharge getRechargeById(Integer id) {
        QueryWrapper<Recharge> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<Recharge> getRechargesByUid(Integer uid, String mode) {
        QueryWrapper<Recharge> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        queryWrapper.orderByDesc("create_time");
        switch (mode) {
            case "out" -> queryWrapper.lt("value", 0);
            case "in" -> queryWrapper.gt("value", 0);
        }
        return this.list(queryWrapper);
    }

    @Override
    public List<Recharge> getRechargesForRoot(Integer uid, String mode) {
        QueryWrapper<Recharge> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(uid != 0, "uid", uid);
        queryWrapper.orderByDesc("create_time");
        switch (mode) {
            case "out" -> queryWrapper.lt("value", 0);
            case "in" -> queryWrapper.gt("value", 0);
        }
        return this.list(queryWrapper);
    }
}
