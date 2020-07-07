package com.zmt.mykill.service;

import com.zmt.mykill.entity.po.ItemKill;

import java.util.Set;

public interface RedisService {
    void storeKill(ItemKill itemKill);
    Object getKillStock(int killItemId);
    boolean decreaseKillStock(int killItemId);
    Set<String> getkillIds();
}
