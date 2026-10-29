package com.example.intlhub.mapper;

import com.example.intlhub.entity.Post;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

// AI-assisted: SQL for the favorite table. All values are bound with #{}.
@Mapper
public interface FavoriteMapper {

    // The pair (user_id, post_id) is the primary key. A second insert changes nothing.
    @Insert("INSERT INTO favorite(user_id, post_id) VALUES(#{userId}, #{postId}) "
            + "ON DUPLICATE KEY UPDATE user_id = user_id")
    int add(@Param("userId") Long userId, @Param("postId") Long postId);

    @Delete("DELETE FROM favorite WHERE user_id = #{userId} AND post_id = #{postId}")
    int remove(@Param("userId") Long userId, @Param("postId") Long postId);

    @Select(PostMapper.VIEW + "JOIN favorite f ON f.post_id = p.id "
            + "WHERE f.user_id = #{userId} AND p.status = 'ACTIVE' "
            + "ORDER BY f.created_at DESC, p.id DESC LIMIT #{size} OFFSET #{offset}")
    List<Post> findByUser(@Param("userId") Long userId,
                          @Param("size") int size,
                          @Param("offset") int offset);

    @Select("SELECT COUNT(*) FROM favorite f JOIN post p ON p.id = f.post_id "
            + "WHERE f.user_id = #{userId} AND p.status = 'ACTIVE'")
    long countByUser(@Param("userId") Long userId);
}