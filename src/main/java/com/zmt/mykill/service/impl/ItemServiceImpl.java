package com.zmt.mykill.service.impl;

import com.zmt.mykill.entity.po.Item;
import com.zmt.mykill.mapper.ItemMapper;
import com.zmt.mykill.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Override
    public List<Item> getAllItem() {
        return itemMapper.selectAll();
    }

    @Override
    public Item getItem(Integer id) {
        return itemMapper.selectItemDetail(id);
    }
}
