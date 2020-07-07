package com.zmt.mykill.controller;

import com.zmt.mykill.entity.po.Order;
import com.zmt.mykill.response.Result;
import com.zmt.mykill.response.ResultCode;
import com.zmt.mykill.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public Result getOrders(@RequestParam("userId") String userId) {
        List<Order> orders = null;
        try {
            orders = orderService.orderLists(userId);
        } catch (Exception e) {
            logger.error("获取用户订单列表失败，{}",e.getMessage());
            return Result.failure(ResultCode.RESULE_DATA_NONE);
        }
        return Result.success(orders);
    }
    @PostMapping("/pay")
    public Result pay(@RequestParam("orderId") String orderId) {
        try {
            int returnVal = orderService.pay(orderId);
            if(returnVal == 0){
                logger.error("秒杀商品不存在或已过期");
                return Result.failure(ResultCode.ITEM_KILL_CHANGE_STATUS_FAIL);
            }
        } catch (Exception e) {
            logger.error("支付失败，{}",e.getMessage());
            return Result.failure(ResultCode.ITEM_KILL_PAY_FAIL);
        }
        return Result.success();
    }

    @PostMapping("/close")
    public Result close(@RequestParam("orderId") String orderId) {
        try {
            int returnVal = orderService.close(orderId);
            if(returnVal == 0){
                logger.error("秒杀商品不存在或已过期");
                return Result.failure(ResultCode.ITEM_KILL_CHANGE_STATUS_FAIL);
            }
        } catch (Exception e) {
            logger.error("商品关闭失败，{}",e.getMessage());
            return Result.failure(ResultCode.ITEM_KILL_CLOSE_FAIL);
        }
        return Result.success();
    }

}
