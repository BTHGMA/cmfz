package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String , Object> queryAll(Integer page, Integer rows);

    void changeUser(User user);
    List<User> queryByeasyPoi(User user);
    void addByeasyPoi(User user);
    Object login(User user);
    User queryByPhone(User user);
    Object addByUser(User user);
    User queryById(User user);
    Map<String,List<String>> countRegist();

    Map<String, Object> userDistribution();
    List<User> queryByall();

}
