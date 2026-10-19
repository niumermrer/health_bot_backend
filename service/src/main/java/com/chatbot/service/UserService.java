package com.chatbot.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chatbot.pojo.User;
import com.chatbot.config.Result;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends IService<User> {

    Result login(JSONObject jsonObj);

    Result register(JSONObject jsonObj);

    Result getUserInfo(HttpServletRequest request);

    Result CheckUserIsExist(JSONObject jsonObj);

    Result CheckAccount(JSONObject jsonObj);

    Result updatePassword(JSONObject jsonObj);

    Result updateUserInfo(JSONObject jsonObj, HttpServletRequest request) throws Exception;

    Result updateAvatar(JSONObject jsonObj, HttpServletRequest request);

    Result resetPwd(JSONObject jsonObj, HttpServletRequest request);

    Result getChatMessage(JSONObject jsonObj);

    Result resetChatMessage(JSONObject jsonObj);

    Result updateChatMessage(JSONObject jsonObj);
}
