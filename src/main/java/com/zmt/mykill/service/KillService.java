package com.zmt.mykill.service;

import com.zmt.mykill.entity.dto.ItemKillDto;
import com.zmt.mykill.entity.po.Order;

import java.util.Set;
public interface KillService {
    void newItemKill(ItemKillDto itemKillDto);
    Set<Object> getKillIds();
    boolean newOrder(Order order) throws Exception;
    void changeStock(int killId);
}
