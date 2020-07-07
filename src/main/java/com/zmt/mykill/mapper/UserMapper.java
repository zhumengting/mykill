package com.zmt.mykill.mapper;

import com.zmt.mykill.entity.dto.UserRegistDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user(user_name,password,phone,email,create_time) " +
            "values(#{userName},#{password},#{phone},#{email},CURRENT_TIMESTAMP)")
    int insertUser(UserRegistDto user);


    @Select("select count(1) from user where user_name = #{userName} and password = #{password} and is_active=1")
    int selectUser(String userName, String password);

}
