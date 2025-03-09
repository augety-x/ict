package com.ftt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ftt.annotation.AutoFill;
import com.ftt.entity.User;
import com.ftt.enumeration.OperationType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where username=#{username}")
    User getByUsername(String username) ;

    // 插入用户

    @Insert("INSERT INTO user (username, password, created_at, updated_at,status) VALUES (#{username}, #{password}, #{createdAt}, #{updatedAt}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "user_id")
    @AutoFill(value = OperationType.INSERT)
    void myinsert(User user);
    // 更新上次登录时间
    @Update("UPDATE user SET last_login = #{lastLogin} WHERE user_id = #{userId}")
    void updateLastLoginTime(@Param("userId") Integer user_id, @Param("lastLogin") LocalDateTime lastLogin);
}


