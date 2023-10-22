package com.example.rbac_homework.dao;

import com.example.rbac_homework.pojo.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageMapper {
    @Select("select * from message where message_isvalid=1")
    @Results(value = {
            @Result(property = "userId",column = "user_id"),
            @Result(property = "messageId",column = "message_id"),
            @Result(property = "content",column = "content"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "isValid",column = "message_isvalid")
    })
    List<Message> showAllMessages();

    @Insert("insert into message(user_id,content) value(#{userId},#{content})")
    void sendMessage(Message message);

    @Update("update message set message_isvalid=0 where message_id=#{messageId}")
    void deleteMessage(int messageId);
}
