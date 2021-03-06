package com.baizhi.service;

import com.baizhi.annotation.AopAnnotation;
import com.baizhi.dao.AdminDao;

import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Repository
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Override
    @AopAnnotation
    public List<Admin> queryAll() {
        List<Admin> admins = adminDao.selectAll();
        return admins;
    }

    @Override
    public Map<String, Object> login(Admin admin) {
        Map<String , Object> map = new HashMap<>();
        Admin selectedAdmin = adminDao.selectAdminByUsername(admin.getUsername());
        if (selectedAdmin == null){
            map.put("code",300);
            map.put("message","用户名不存在");
        }else if (selectedAdmin.getPassword().equals(admin.getPassword())){
            map.put("code",200);
            map.put("message","登陆成功");
        }else{
            map.put("code",400);
            map.put("message","密码错误");
        }
        return map;
    }

    @Override
    public Admin queryOne(String username) {
        Admin admin = adminDao.selectAdminByUsername(username);

        return admin;
    }

    @Override
    public void addAdmin(Admin admin) {
        adminDao.insertAdmin(admin);
        throw new RuntimeException("添加出了错");
    }
}
