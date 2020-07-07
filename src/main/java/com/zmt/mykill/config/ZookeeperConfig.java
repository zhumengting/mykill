package com.zmt.mykill.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.RetryNTimes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZookeeperConfig {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperConfig.class);

    @Value("${zookeeper.address}")
    private    String connectString;

    @Value("${zookeeper.timeout}")
    private  int timeout;


    @Bean
    public CuratorFramework curatorFramework(){
        CuratorFramework curatorFramework= CuratorFrameworkFactory.builder()
                .connectString(connectString)
                //重试策略
                .retryPolicy(new RetryNTimes(5,1000))
                .build();
        curatorFramework.start();
        return curatorFramework;
    }

    @Bean
    public DistributedAtomicLong distributedAtomicLong(CuratorFramework curatorFramework){
        DistributedAtomicLong distAtomicLong = new DistributedAtomicLong(curatorFramework,"/atomic-long",new RetryNTimes(5,1000));
        return distAtomicLong;
    }
}

