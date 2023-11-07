package com.acautomaton.aichat.service.inte;

import com.acautomaton.aichat.entity.User;

public interface UserService {
    User getUserByUid(String uid);
    User getUserByUid(Integer uid);
    User getUserByUsername(String username);
    User registerUser(User user);
    void updateUser(User user);
    void updateBalanceByUid(User user);
    void deleteUserByUid(User user);
}
