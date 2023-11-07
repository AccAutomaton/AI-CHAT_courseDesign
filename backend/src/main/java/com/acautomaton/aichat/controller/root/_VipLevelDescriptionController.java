package com.acautomaton.aichat.controller.root;


import com.acautomaton.aichat.entity.vipLevel.VipLevelDescription;
import com.acautomaton.aichat.response.PageHelperResponse;
import com.acautomaton.aichat.response.Response;
import com.acautomaton.aichat.service.inte.vipLevel.VipLevelDescriptionService;
import com.acautomaton.aichat.utils.JudgeUnificationUtil;
import com.acautomaton.aichat.utils.exception.NullException;
import com.acautomaton.aichat.utils.exception.OtherException;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/root/vipLevel")
public class _VipLevelDescriptionController {
    VipLevelDescriptionService vipLevelDescriptionService;

    @Autowired
    public _VipLevelDescriptionController(VipLevelDescriptionService vipLevelDescriptionService) {
        this.vipLevelDescriptionService = vipLevelDescriptionService;
    }

    @PostMapping("/add")
    public Response addVipLevel(@RequestBody Map<String, String> request) {
        try {
            JudgeUnificationUtil.parameterNotNull(request, List.of("levelName"));
            String levelName = request.get("levelName");

            VipLevelDescription vipLevelDescription = vipLevelDescriptionService.getDescriptionByName(levelName);
            if (vipLevelDescription != null) {
                throw new NullException("该等级已存在");
            }

            vipLevelDescription = new VipLevelDescription(null, levelName);
            vipLevelDescriptionService.addDescription(vipLevelDescription);
            if (vipLevelDescription.getId() == null || vipLevelDescription.getId() <= 0) {
                throw new OtherException("System Error");
            }
            log.info("add vipLevelDescription " + levelName + " success");
            return Response.success();
        }
        catch (NullException | OtherException e) {
            return Response.error(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public Response deleteVipLevel(@PathVariable Integer id) {
        try {
            VipLevelDescription vipLevelDescription = vipLevelDescriptionService.getDescriptionById(id);
            if (vipLevelDescription == null) {
                throw new NullException("等级不存在");
            }
            vipLevelDescriptionService.deleteDescriptionById(id);
            return Response.success();
        }
        catch (NullException e) {
            return Response.error(e.getMessage());
        }
    }

    @PostMapping("/update/{id}")
    public Response updateVipLevel(@PathVariable Integer id, @RequestBody Map<String, String> request) {
        try {
            JudgeUnificationUtil.parameterNotNull(request, List.of("newName"));
            String newName = request.get("newName");
            VipLevelDescription vipLevelDescription = vipLevelDescriptionService.getDescriptionById(id);
            if (vipLevelDescription == null) {
                throw new NullException("要修改的等级不存在");
            }
            vipLevelDescription = vipLevelDescriptionService.getDescriptionByName(newName);
            if (vipLevelDescription != null) {
                throw new NullException("等级名称重复");
            }

            vipLevelDescription = vipLevelDescriptionService.getDescriptionById(id);
            vipLevelDescription.setDescription(newName);
            vipLevelDescriptionService.updateDescriptionById(vipLevelDescription);

            return Response.success();
        }
        catch (NullException e) {
            return Response.error(e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public Response getVipLevel(@PathVariable Integer id) {
        try {
            VipLevelDescription vipLevelDescription = vipLevelDescriptionService.getDescriptionById(id);
            if (vipLevelDescription == null) {
                throw new NullException("会员等级不存在");
            }
            return Response.success(vipLevelDescription);
        }
        catch (NullException e) {
            return Response.error(e.getMessage());
        }
    }

    @GetMapping("/get/all")
    public Response getAllWithoutPages() {
        return Response.success(vipLevelDescriptionService.getDescriptions());
    }

    @GetMapping("/get/all/{pageNum}/{pageSize}")
    public Response getAll(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        PageHelperResponse pageHelperResponse = new PageHelperResponse(
                PageHelper.startPage(pageNum, pageSize > 100 ? 100 : pageSize).doSelectPageInfo(() ->
                        vipLevelDescriptionService.getDescriptions())
        );
        return Response.success(pageHelperResponse);
    }
}
