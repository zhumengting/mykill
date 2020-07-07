package com.zmt.mykill.mapper;

import com.zmt.mykill.entity.po.ItemKill;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ItemKillMapper {
    @Select("select * from item_kill where is_active = 1 and total > 0 order by start_time desc")
    List<ItemKill> selectAvailableKillItems();

    @Select("select * from item_kill where id = #{id}")
    ItemKill selectItemDetail(int id);

    @Select("select total from item_kill where id = #{id}")
    int selectItemTotal(int id);

    @Update("update item_kill set is_active = #{isActive} where id = #{id}")
    int updateItemKillAvailable(int id, int isActive);

    @Insert("insert into item_kill(item_id,total,start_time,end_time,create_time) " +
            "values (#{itemId},#{total},#{startTime,jdbcType=TIMESTAMP},#{endTime,jdbcType=TIMESTAMP},#{createTime,jdbcType=TIMESTAMP})")
    @Options(useGeneratedKeys=true, keyProperty = "id", keyColumn = "id")
    int insertItemKill(ItemKill itemKill);

    @Update("update item_kill set item_id = #{itemId} and total = #{total} " +
            "and start_time = #{startTime,jdbcType=TIMESTAMP} and end_time = #{endTime,jdbcType=TIMESTAMP}")
    int updateItemKill(ItemKill itemKill);
}
