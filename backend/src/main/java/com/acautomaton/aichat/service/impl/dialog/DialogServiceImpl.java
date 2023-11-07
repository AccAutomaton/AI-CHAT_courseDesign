package com.acautomaton.aichat.service.impl.dialog;

import com.acautomaton.aichat.entity.dialog.Dialog;
import com.acautomaton.aichat.mapper.DialogMapper;
import com.acautomaton.aichat.service.inte.dialog.DialogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DialogServiceImpl extends ServiceImpl<DialogMapper, Dialog> implements DialogService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addDialog(Dialog dialog) {
        this.save(dialog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDialogById(Integer id) {
        this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTitleOfDialogById(Dialog dialog) {
        UpdateWrapper<Dialog> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", dialog.getId());
        updateWrapper.set("title", dialog.getTitle());
        updateWrapper.set("update_time", dialog.getUpdateTime());
        this.update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDialogListOfDialogById(Dialog dialog) {
        UpdateWrapper<Dialog> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", dialog.getId());
        updateWrapper.set("dialog_list", dialog.getDialogList());
        updateWrapper.set("update_time", dialog.getUpdateTime());
        this.update(updateWrapper);
    }

    @Override
    public Dialog getDialogById(Integer id) {
        QueryWrapper<Dialog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<Dialog> getDialogListByUid(Integer uid) {
        QueryWrapper<Dialog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        queryWrapper.select("id", "title");
        queryWrapper.orderByDesc("update_time");
        return this.list(queryWrapper);
    }

    @Override
    public List<Dialog> getDialogListForRoot() {
        QueryWrapper<Dialog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "uid", "title", "create_time", "update_time");
        queryWrapper.orderByDesc("update_time");
        return this.list(queryWrapper);
    }
}
