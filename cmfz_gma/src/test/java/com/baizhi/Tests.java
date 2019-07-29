package com.baizhi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Tests {
    @Test
    public void test1(){
        //获取安全管理器
        IniSecurityManagerFactory iniSecurityManagerFactory = new IniSecurityManagerFactory();
        SecurityManager securityManager = iniSecurityManagerFactory.getInstance();
        //指明使用的哪个安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        //获取主体
        Subject subject = SecurityUtils.getSubject();
        //获取令牌token
        UsernamePasswordToken token = new UsernamePasswordToken("gma","123456");
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        boolean authenticated = subject.isAuthenticated();
        System.out.println(authenticated);
        boolean b = subject.hasRole("vip");
        if (b){
            System.out.println("可以有vip的所有权限");
        }else{
            System.out.println("不是vip");
        }
        boolean c = subject.isPermitted("carousel:add");
        if (c){
            System.out.println("当前用户有对轮播图的添加权限");
        }else{
            System.out.println("不能添加轮播图");
        }
        //UnknownAccountException   不知道的账户
        //IncorrectCredentialsException   凭证信息不正确
    }


}
