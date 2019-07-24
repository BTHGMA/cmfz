package com.baizhi.service;

import com.baizhi.dao.AdminDao;

import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@Repository
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;


    @Override
    public Map<String, Object> login(Admin admin) {
        Map<String , Object> map = new HashMap<>();
        Admin login = adminDao.login(admin.getUsername());
        if(login==null){
            map.put("code",300);
            map.put("message","用户名不存在");
        }else if (login.getPassword().equals(admin.getPassword())){
            map.put("code",200);
            map.put("message","登陆成功");
        }else{
            map.put("code",400);
            map.put("message","密码错误");
        }
        return map;
    }
}
