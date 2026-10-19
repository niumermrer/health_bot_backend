package com.chatbot.mapper;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chatbot.pojo.User;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    User login(String phone, String password);

    User selectByUsername(String username);

    User selectByPhone(String phone);

    User selectByPhoneR(String phone);

    User selectByPhoneAndName(String phone, String username);

    User selectByPhoneAndNameR(String phone, String username);

    Boolean updateUserById(Integer userid, String phone, String username, Integer status, String avatar, String email, String gender, Integer age, String contract, String description, String SQ1, String SQ2, String SQ3, String AS1, String AS2, String AS3, Date birthDate, Timestamp updateTime);

    Boolean updateAvatarByPhone(String phone, String avatar);

    JSONObject selectChatMessageByUserId(Integer userid);

    Boolean updateChatMessageById(Integer userid, String chatMessage);
}
