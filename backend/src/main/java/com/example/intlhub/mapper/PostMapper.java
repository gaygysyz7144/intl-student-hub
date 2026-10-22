package com.example.intlhub.mapper;

import com.example.intlhub.entity.Post;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

// AI-assisted: SQL for the post table. All values are bound with #{} (no string concatenation of user input).
@Mapper
public interface PostMapper {

    String VIEW = "SELECT p.*, u.username AS author_name FROM post p JOIN user u ON u.id = p.user_id ";

    String FILTER = "<if test='category != null'>AND p.category = #{category} </if>"
            + "<if test='city != null'>AND p.city = #{city} </if>"
            + "<if test='keyword != null'>AND (p.title LIKE CONCAT('%', #{keyword}, '%') "
            + "OR p.content LIKE CONCAT('%', #{keyword}, '%')) </if>";

    @Insert("INSERT INTO post(user_id, category, title, content, price, city) "
            + "VALUES(#{userId}, #{category}, #{title}, #{content}, #{price}, #{city})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Post post);

    @Select(VIEW + "WHERE p.id = #{id}")
    Post findById(@Param("id") Long id);

    @Select("<script>" + VIEW + "WHERE p.status = 'ACTIVE' " + FILTER
            + "ORDER BY p.created_at DESC, p.id DESC LIMIT #{size} OFFSET #{offset}</script>")
    List<Post> search(@Param("category") String category,
                      @Param("city") String city,
                      @Param("keyword") String keyword,
                      @Param("size") int size,
                      @Param("offset") int offset);

    @Select("<script>SELECT COUNT(*) FROM post p WHERE p.status = 'ACTIVE' " + FILTER + "</script>")
    long count(@Param("category") String category,
               @Param("city") String city,
               @Param("keyword") String keyword);

    @Update("UPDATE post SET category = #{category}, title = #{title}, content = #{content}, "
            + "price = #{price}, city = #{city} WHERE id = #{id}")
    int update(Post post);

    @Delete("DELETE FROM post WHERE id = #{id}")
    int delete(@Param("id") Long id);
}
