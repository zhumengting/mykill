package com.zmt.mykill.entity.po;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Order {
    private String code;
    private int itemId;
    private int killId;
    private String userId;
    private int status;
    private Timestamp createTime;
    private Timestamp updateTime;
}
