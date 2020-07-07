package com.zmt.mykill.mapper;

import com.zmt.mykill.entity.po.Item;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ItemMapper {
    @Select("select * from item")
    List<Item> selectAll();

    @Select("select * from item where is_active = 1")
    List<Item> selectAllAvailable();

    @Select("select stock from item where id = #{id}")
    int selectStock(int id);

    @Select("select * from item where id = #{id}")
    Item selectItemDetail(int id);

    @Update("update item set is_active = 1 where id = #{id}")
    int updateItemAvailable(int id, int isActive);

    @Insert("insert into item(name,code,stock,purchase_time,create_time) " +
            "values (#{name},#{code},#{stock},#{purchaseTime,jdbcType=DATE},#{createTime,jdbcType=TIMESTAMP})")
    int insertItem(Item item);

    @Update("update item set name = #{name} and code = #{code} " +
            "and stock = #{stock} and purchase_time = #{purchaseTime,jdbcType=DATE}")
    int updateItem(Item item);

    @Update("update item set stock = stock - #{number} where id = #{id} ")
    int updateNumber(int id,int number);

}
