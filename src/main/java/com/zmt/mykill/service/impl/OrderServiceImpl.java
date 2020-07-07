package com.zmt.mykill.service.impl;

import com.zmt.mykill.entity.po.Order;
import com.zmt.mykill.mapper.OrderMapper;
import com.zmt.mykill.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> orderLists(String userId) {
        return orderMapper.selectOrderByUserId(userId);
    }

    @Override
    public int pay(String code) {
        return orderMapper.updateStatus(0,1,code);
    }

    @Override
    public int close(String code) {
        return orderMapper.updateStatus(0,2,code);
    }
}
