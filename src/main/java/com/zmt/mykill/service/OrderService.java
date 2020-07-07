package com.zmt.mykill.service;

import com.zmt.mykill.entity.po.Order;

import java.util.List;

public interface OrderService {
    List<Order> orderLists(String userId);
    int pay(String code);
    int close(String code);

}
