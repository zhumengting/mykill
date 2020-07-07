package com.zmt.mykill.service;

import com.zmt.mykill.entity.po.Item;

import java.util.List;

public interface ItemService {
    List<Item> getAllItem();
    Item getItem(Integer id);
}
