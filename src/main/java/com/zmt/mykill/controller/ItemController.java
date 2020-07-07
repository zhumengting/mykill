package com.zmt.mykill.controller;

import com.zmt.mykill.entity.po.Item;
import com.zmt.mykill.response.Result;
import com.zmt.mykill.response.ResultCode;
import com.zmt.mykill.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;


    @GetMapping("/all")
    public Result allItems() {
        List<Item> items = null;
        try {
            items = itemService.getAllItem();
        } catch (Exception e) {
            return Result.failure(ResultCode.RESULE_DATA_NONE);
        }

        return Result.success(items);
    }

    @GetMapping("/single")
    public Result singleItem(@RequestParam("id") Integer id) {
        Item item = null;
        try {
            item = itemService.getItem(id);
        } catch (Exception e) {
            return Result.failure(ResultCode.RESULE_DATA_NONE);
        }

        return Result.success(item);
    }
}
