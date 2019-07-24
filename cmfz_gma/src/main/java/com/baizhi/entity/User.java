package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baizhi.annotation.UserAnnotation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Excel(name="用户编号")
    @UserAnnotation(name = "用户编号")
   private String id;
    @Excel(name="手机号码")
    @UserAnnotation(name = "手机号码")
    private String phone;
    @Excel(name="用户密码")
    @UserAnnotation(name = "用户密码")
   private String password;
    @Excel(name="用户法号")
    @UserAnnotation(name = "用户法号")
   private String dharmaName;
    @Excel(name="用户省份")
    @UserAnnotation(name = "用户省份")
   private String province;
    @Excel(name="用户城市")
    @UserAnnotation(name = "用户城市")
   private String city;
    @Excel(name="用户性别")
    @UserAnnotation(name = "用户性别")
   private String gender;
    @Excel(name="个人签名")
    @UserAnnotation(name = "个人签名")
   private String personalSign;
    @Excel(name="用户头像")
    @UserAnnotation(name = "用户头像")
   private String profile;
    @Excel(name="用户状态")
    @UserAnnotation(name = "用户状态")
   private String status;
    @Excel(name="创建时间",format = "yyyy-MM-dd")
    @UserAnnotation(name = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date registTime;
}
