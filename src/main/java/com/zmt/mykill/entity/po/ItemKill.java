package com.zmt.mykill.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class ItemKill implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private int itemId;
    private int total;
    private Timestamp startTime;
    private Timestamp endTime;
    private int isActive;
    private Timestamp createTime;
    private Timestamp updateTime;
}
