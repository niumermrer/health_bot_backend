package com.chatbot.controller;


import com.alibaba.fastjson2.JSONObject;
import com.chatbot.config.Result;
import com.chatbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/API/USER")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public Result login(@RequestBody JSONObject jsonObj){
      return  userService.login(jsonObj);
    }

    @PostMapping("/register")
    public Result register(@RequestBody JSONObject jsonObj){
        return  userService.register(jsonObj);
    }

    @GetMapping("/getUserInfo")
    public Result getUserInfo(HttpServletRequest request){
        return  userService.getUserInfo(request);
    }

    @PostMapping("/CheckUserIsExist")
    public Result CheckUserIsExist(@RequestBody JSONObject jsonObj){
        return  userService.CheckUserIsExist(jsonObj);
    }

    @PostMapping("/CheckAccount")
    public Result ResetAccount(@RequestBody JSONObject jsonObj){
        return  userService.CheckAccount(jsonObj);
    }

    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody JSONObject jsonObj){
        return  userService.updatePassword(jsonObj);
    }

    @PostMapping("/updateUserInfo")
    public Result updateUserInfo(@RequestBody JSONObject jsonObj, HttpServletRequest request)throws Exception{
        return  userService.updateUserInfo(jsonObj, request);
    }

    @PostMapping("/updateAvatar")
    public Result updateAvatar(@RequestBody JSONObject jsonObj, HttpServletRequest request){
        return  userService.updateAvatar(jsonObj, request);
    }

    @PostMapping("/resetPwd")
    public Result resetPwd(@RequestBody JSONObject jsonObj, HttpServletRequest request){
        return  userService.resetPwd(jsonObj, request);
    }

    @PostMapping("/getChatMessage")
    public Result getChatMessage(@RequestBody JSONObject jsonObj){
        return  userService.getChatMessage(jsonObj);
    }

    @PostMapping("/resetChatMessage")
    public Result resetChatMessage(@RequestBody JSONObject jsonObj){
        return  userService.resetChatMessage(jsonObj);
    }

    @PostMapping("/updateChatMessage")
    public Result updateChatMessage(@RequestBody JSONObject jsonObj){
        return  userService.updateChatMessage(jsonObj);
    }


}
