package com.zmt.mykill.service.impl;

import com.zmt.mykill.entity.dto.ItemKillDto;
import com.zmt.mykill.entity.po.ItemKill;
import com.zmt.mykill.entity.po.Order;
import com.zmt.mykill.mapper.ItemKillMapper;
import com.zmt.mykill.mapper.ItemMapper;
import com.zmt.mykill.mapper.OrderMapper;
import com.zmt.mykill.service.KillService;
import com.zmt.mykill.service.RedisService;
import com.zmt.mykill.utils.CommonException;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Service
public class KillServiceImpl implements KillService {
    @Autowired
    private ItemKillMapper itemKillMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private DistributedAtomicLong distributedAtomicLong;

    private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void newItemKill(ItemKillDto itemKillDto) throws CommonException {
        try {
            ItemKill itemKill = getItemKill(itemKillDto);
            //修改item库存
            itemMapper.updateNumber(itemKillDto.getItemId(), itemKillDto.getTotal());
            logger.info("修改mysql库存成功，itemId{}", itemKill.getItemId());
            //获取库存
            int stock = itemMapper.selectStock(itemKillDto.getItemId());
            logger.info("修改mysql库存成功，当前库存{}", stock);
            if (stock < 0) throw new CommonException("库存不足");
            //写入数据库
            itemKillMapper.insertItemKill(itemKill);
            logger.info("新建itemkill成功，itemId{}，killId{}", itemKill.getItemId(), itemKill.getId());
            redisService.storeKill(itemKill);
            logger.info("redis写入成功，killId{}", itemKill.getId());
        } catch (Exception e) {
            logger.error("新建秒杀项目失败，{}", e.getMessage());
            throw new CommonException("新建秒杀项目失败");
        }

    }

    @Override
    public Set<Object> getKillIds() {
        Set<String> ids = redisService.getkillIds();
        Set<Object> itemKills = new HashSet<>();
        for (String id : ids) {
            Object killStock = redisService.getKillStock(Integer.parseInt(id));
            if (killStock == null) {
                changeStock(Integer.parseInt(id));
            } else {
                itemKills.add(killStock);
            }
        }
        return itemKills;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeStock(int killId) {
        int val = itemKillMapper.updateItemKillAvailable(killId, 0);
        if (val == 1) {
            orderMapper.updateStatusByKillId(0, 2, killId);
            int success = orderMapper.selectSuccess(killId);
            ItemKill itemKill = itemKillMapper.selectItemDetail(killId);
            itemMapper.updateNumber(itemKill.getItemId(), success - itemKill.getTotal());
            logger.info("itemId:{},秒杀结束，增加库存{}", itemKill.getItemId(), itemKill.getTotal() - success);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean newOrder(Order order) throws Exception {
        distributedAtomicLong.increment();
        String code = String.valueOf(distributedAtomicLong.get().preValue());
        order.setCode(code);
        if (redisService.decreaseKillStock(order.getKillId())) {
            orderMapper.insertNewOrder(order);
            return true;
        }
        return false;
    }

    private ItemKill getItemKill(ItemKillDto itemKillDto) {
        ItemKill itemKill = new ItemKill();
        itemKill.setItemId(itemKillDto.getItemId());
        itemKill.setStartTime(itemKillDto.getStartTime());
        itemKill.setEndTime(itemKillDto.getEndTime());
        itemKill.setTotal(itemKillDto.getTotal());
        itemKill.setCreateTime(new Timestamp(System.currentTimeMillis()));
        itemKill.setIsActive(1);
        return itemKill;
    }
}
