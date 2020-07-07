package com.zmt.mykill.mapper;

import com.zmt.mykill.entity.po.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Select("select * from `order_item` where user_id = #{userId} order by status")
    List<Order> selectOrderByUserId(String userId);

    @Select("select count(1) from `order_item` where user_id = #{userId} and kill_id = #{killId}")
    Integer selectIfExist(String userId, int killId);

    @Select("select * from `order_item` where code = #{code}")
    Order selectOrderByCode(String code);

    @Insert("insert into `order_item` values (#{code},#{itemId},#{killId},#{userId},#{status},#{createTime,jdbcType=TIMESTAMP},#{updateTime,jdbcType=TIMESTAMP})")
    int insertNewOrder(Order order);

    @Update("update `order_item` set status = #{newStatus} where code = #{code} and status = #{oldStatus}")
    int updateStatus(int oldStatus,int newStatus, String code);

    @Update("update `order_item` set status = #{newStatus} where  kill_id = #{killId} and status = #{oldStatus}")
    int updateStatusByKillId(int oldStatus,int newStatus, int killId);

    @Select("select count(1) from `order_item` where kill_id = #{killId} and status = 1")
    Integer selectSuccess( int killId);

}
