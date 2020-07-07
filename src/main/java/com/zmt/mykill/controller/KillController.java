package com.zmt.mykill.controller;

import com.zmt.mykill.entity.dto.ItemKillDto;
import com.zmt.mykill.entity.po.ItemKill;
import com.zmt.mykill.entity.po.Order;
import com.zmt.mykill.response.Result;
import com.zmt.mykill.response.ResultCode;
import com.zmt.mykill.service.KillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Set;

@RestController
@RequestMapping("/kill")
public class KillController {
    @Autowired
    private KillService killService;

    private static final Logger logger = LoggerFactory.getLogger(KillController.class);

    @PostMapping("/new")
    public Result newKillItem(@RequestParam("itemId") int itemId,
                              @RequestParam("total") int total,
                              @RequestParam("startTime") String startTime,
                              @RequestParam("endTime") String endTime) {
        ItemKillDto itemKillDto = new ItemKillDto();
        itemKillDto.setEndTime(Timestamp.valueOf(endTime));
        itemKillDto.setStartTime(Timestamp.valueOf(startTime));
        itemKillDto.setItemId(itemId);
        itemKillDto.setTotal(total);

        try {
            killService.newItemKill(itemKillDto);
        } catch (Exception e) {
            return Result.failure(ResultCode.ITEM_KILL_CREATE_FAIL);
        }

        return Result.success();
    }
    @GetMapping("/items")
    public Result killItems() {
            Set<Object> result = null;
        try {
            result = killService.getKillIds();
        } catch (Exception e) {
            logger.error("获取秒杀列表失败，{}",e.getMessage());
            return Result.failure(ResultCode.ITEM_KILL_CREATE_FAIL);
        }

        return Result.success(result);
    }
    @PostMapping("/kill")
    public Result tryKill(@RequestParam("killId") int killId,
                          @RequestParam("itemId") int itemId,
                          @RequestParam("userId") String userId){
        try{
            Order order = new Order();
            order.setItemId(itemId);
            order.setUserId(userId);
            order.setKillId(killId);
            order.setStatus(0);
            order.setCreateTime(new Timestamp(System.currentTimeMillis()));
            if(!killService.newOrder(order)){
                return Result.failure(ResultCode.PRODUCT_KILL_FAIL);
            }
        }catch (Exception e){
            logger.error("新建用户订单失败，{}",e.getMessage());
            return Result.failure(ResultCode.PRODUCT_KILL_FAIL);
        }
        return Result.success();
    }
}
