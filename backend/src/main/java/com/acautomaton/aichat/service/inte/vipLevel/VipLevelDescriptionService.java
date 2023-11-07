package com.acautomaton.aichat.service.inte.vipLevel;

import com.acautomaton.aichat.entity.vipLevel.VipLevelDescription;

import java.util.List;

public interface VipLevelDescriptionService {
    VipLevelDescription addDescription(VipLevelDescription vipLevelDescription);
    void deleteDescriptionById(Integer id);
    void updateDescriptionById(VipLevelDescription vipLevelDescription);
    VipLevelDescription getDescriptionById(Integer id);
    VipLevelDescription getDescriptionByName(String name);
    List<VipLevelDescription> getDescriptions();
}
