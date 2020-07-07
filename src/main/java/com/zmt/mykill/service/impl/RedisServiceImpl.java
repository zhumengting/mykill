package com.zmt.mykill.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zmt.mykill.entity.po.ItemKill;
import com.zmt.mykill.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {
    private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private final String ITEM_KILL_PREFIX = "ITEM_KILL_PREFIX_";

    private final String ITEM_KILL_SET_IDS = "ITEM_KILL_ZSET_IDS";

    @Override
    public void storeKill(ItemKill itemKill) {
        long time = itemKill.getEndTime().getTime() - System.currentTimeMillis();
        if (time > 0){
            redisTemplate.opsForValue().set(
                    ITEM_KILL_PREFIX + itemKill.getId(),
                    itemKill, time, TimeUnit.MILLISECONDS);
            stringRedisTemplate.opsForZSet().add(ITEM_KILL_SET_IDS,String.valueOf(itemKill.getId()),itemKill.getStartTime().getTime());
        }

    }

    @Override
    public Object getKillStock(int killItemId) {
        Object value = redisTemplate.opsForValue().get(ITEM_KILL_PREFIX + killItemId);
        if (value == null) {
            stringRedisTemplate.opsForZSet().remove(ITEM_KILL_SET_IDS,String.valueOf(killItemId));
            return null;
        }
        return value;
    }

    @Override
    public boolean decreaseKillStock(int killItemId) {
        Object value = redisTemplate.opsForValue().get(ITEM_KILL_PREFIX + killItemId);
        if (value != null) {
            ItemKill kill = JSONObject.parseObject(JSONObject.toJSONString(value), ItemKill.class);
            if(kill.getTotal() >= 1){
                kill.setTotal(kill.getTotal()-1);
                long time = kill.getEndTime().getTime() - System.currentTimeMillis();
                redisTemplate.opsForValue().set(
                        ITEM_KILL_PREFIX + kill.getId(),
                        kill, time, TimeUnit.MILLISECONDS);
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<String> getkillIds() {
        return stringRedisTemplate.opsForZSet().range(ITEM_KILL_SET_IDS,0,-1);
    }
}
