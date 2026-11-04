package com.example.intlhub.mapper;

import com.example.intlhub.entity.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

// AI-assisted: SQL for the message table. All values are bound with #{}.
@Mapper
public interface MessageMapper {

    String VIEW = "SELECT m.*, u.username AS sender_name FROM message m "
            + "JOIN user u ON u.id = m.sender_id ";

    @Insert("INSERT INTO message(sender_id, receiver_id, content) "
            + "VALUES(#{senderId}, #{receiverId}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Message message);

    @Select(VIEW + "WHERE m.id = #{id}")
    Message findById(@Param("id") Long id);

    // Privacy rule: only messages addressed to this user are ever returned.
    @Select(VIEW + "WHERE m.receiver_id = #{userId} "
            + "ORDER BY m.created_at DESC, m.id DESC LIMIT #{size} OFFSET #{offset}")
    List<Message> findInbox(@Param("userId") Long userId,
                            @Param("size") int size,
                            @Param("offset") int offset);

    @Select("SELECT COUNT(*) FROM message WHERE receiver_id = #{userId}")
    long countInbox(@Param("userId") Long userId);
}