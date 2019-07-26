package com.baizhi.dao;

import com.baizhi.entity.Admin;

import java.util.List;

public interface AdminDao {
    List<Admin> selectall();
    Admin login(String username);

}
