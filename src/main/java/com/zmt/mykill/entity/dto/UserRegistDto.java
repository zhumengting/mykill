package com.zmt.mykill.entity.dto;

import lombok.Data;

@Data
public class UserRegistDto {
    private String userName;
    private String password;
    private String phone;
    private String email;
}
