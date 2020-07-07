package com.zmt.mykill.entity.po;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class Item {
    private int id;
    private String name;
    private String code;
    private int stock;
    private Date purchaseTime;
    private boolean isActive;
    private Timestamp createTime;
    private Timestamp updateTime;


}
