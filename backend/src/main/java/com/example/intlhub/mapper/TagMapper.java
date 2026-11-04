package com.example.intlhub.mapper;

import com.example.intlhub.entity.Tag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

// AI-assisted: SQL for the tag and user_tag tables. All values are bound with #{}.
@Mapper
public interface TagMapper {

    @Select("SELECT id, name FROM tag ORDER BY id")
    List<Tag> findAll();

    @Select("<script>SELECT COUNT(*) FROM tag WHERE id IN "
            + "<foreach collection='ids' item='id' open='(' separator=',' close=')'>#{id}</foreach></script>")
    long countByIds(@Param("ids") List<Long> ids);

    @Select("SELECT t.id, t.name FROM tag t JOIN user_tag ut ON ut.tag_id = t.id "
            + "WHERE ut.user_id = #{userId} ORDER BY t.id")
    List<Tag> findByUser(@Param("userId") Long userId);

    @Delete("DELETE FROM user_tag WHERE user_id = #{userId}")
    int clearUserTags(@Param("userId") Long userId);

    @Insert("INSERT INTO user_tag(user_id, tag_id) VALUES(#{userId}, #{tagId})")
    int addUserTag(@Param("userId") Long userId, @Param("tagId") Long tagId);
}