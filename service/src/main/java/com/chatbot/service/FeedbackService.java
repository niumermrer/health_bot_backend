package com.chatbot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chatbot.config.Result;
import com.chatbot.pojo.Feedback;
import com.chatbot.pojo.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FeedbackService extends IService<Feedback> {

    Feedback saveFeedback(Feedback feedback, HttpServletRequest request);

    List<Feedback> getAllFeedback();
}
