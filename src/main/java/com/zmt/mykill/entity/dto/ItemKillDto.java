package com.zmt.mykill.entity.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ItemKillDto {
    private int itemId;
    private int total;
    private Timestamp startTime;
    private Timestamp endTime;
}
