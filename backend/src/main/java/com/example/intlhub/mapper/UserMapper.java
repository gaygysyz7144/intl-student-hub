package com.example.intlhub.mapper;

import com.example.intlhub.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

// AI-assisted: SQL for the user table. #{} placeholders are bound as parameters (no SQL injection).
@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user(username, email, password_hash, nationality, city) "
            + "VALUES(#{username}, #{email}, #{passwordHash}, #{nationality}, #{city})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(@Param("id") Long id);

    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    @Select("SELECT * FROM user WHERE email = #{email}")
    User findByEmail(@Param("email") String email);
}
