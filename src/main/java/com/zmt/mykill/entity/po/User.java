package com.zmt.mykill.entity.po;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    private String id;
    private String userName;
    private String password;
    private String phone;
    private String email;
    private boolean isActive;
    private Timestamp createTime;
    private Timestamp updateTime;
}
