package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin implements Serializable {
    private String admin_id;
    private String username;
    private String password;
    private List<Role> roles;

}
