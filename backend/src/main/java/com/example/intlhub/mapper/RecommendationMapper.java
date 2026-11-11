package com.example.intlhub.mapper;

import com.example.intlhub.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

// AI-assisted: SQL for tag-based recommendations. All values are bound with #{}.
@Mapper
public interface RecommendationMapper {

    // A post matches when it shares at least one tag with the user. Own and inactive posts are left out.
    String FROM = "FROM post p JOIN user u ON u.id = p.user_id "
            + "JOIN post_tag pt ON pt.post_id = p.id "
            + "JOIN user_tag ut ON ut.tag_id = pt.tag_id AND ut.user_id = #{userId} "
            + "WHERE p.status = 'ACTIVE' AND p.user_id <> #{userId} ";

    // COUNT(*) per post = number of shared tags. More shared tags first, then newest first.
    @Select("SELECT p.*, u.username AS author_name " + FROM
            + "GROUP BY p.id, u.username "
            + "ORDER BY COUNT(*) DESC, p.created_at DESC, p.id DESC LIMIT #{size} OFFSET #{offset}")
    List<Post> recommend(@Param("userId") Long userId,
                         @Param("size") int size,
                         @Param("offset") int offset);

    @Select("SELECT COUNT(DISTINCT p.id) " + FROM)
    long count(@Param("userId") Long userId);
}