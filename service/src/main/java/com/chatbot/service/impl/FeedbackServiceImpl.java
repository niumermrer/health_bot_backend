package com.chatbot.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chatbot.config.JwtUtils;
import com.chatbot.mapper.FeedbackMapper;
import com.chatbot.mapper.UserMapper;
import com.chatbot.pojo.Feedback;
import com.chatbot.pojo.User;
import com.chatbot.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Feedback saveFeedback(Feedback feedback, HttpServletRequest request) {
        feedback.setCreateTime(new Date());
        feedback.setUpdateTime(new Date());
        String token = request.getHeader("check-token");
        DecodedJWT jwt = JwtUtils.getTokenInfo(token);
        String phone1 = jwt.getClaim("phone").asString();
        User user = userMapper.selectByPhone(phone1);
        feedback.setUserId(user.getUserid());
        feedbackMapper.insert(feedback);
        return feedback;
    }

    @Override
    public List<Feedback> getAllFeedback() {
        return feedbackMapper.selectList(null);
    }
}
