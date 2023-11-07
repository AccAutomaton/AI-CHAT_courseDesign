package com.acautomaton.aichat.service.inte.vipLevel;

import com.acautomaton.aichat.entity.vipLevel.UserVipLevel;

public interface UserVipLevelService {

    UserVipLevel addLevel(UserVipLevel userVipLevel);
    void updateLevelByUid(UserVipLevel userVipLevel);
    UserVipLevel getLevelByUid(Integer uid);
}
