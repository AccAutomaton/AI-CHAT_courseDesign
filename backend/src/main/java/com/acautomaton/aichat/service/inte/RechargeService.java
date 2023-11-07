package com.acautomaton.aichat.service.inte;

import com.acautomaton.aichat.entity.Recharge;

import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public interface RechargeService {
    Recharge addRecharge(Recharge recharge);
    void deleteRechargeById(Integer id);
    void updateRechargeById(Recharge recharge);
    Recharge getRechargeById(Integer id);
    List<Recharge> getRechargesByUid(Integer uid, String mode);
    List<Recharge> getRechargesForRoot(Integer uid, String mode);
}
