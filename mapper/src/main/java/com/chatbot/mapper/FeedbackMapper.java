package com.chatbot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chatbot.pojo.Feedback;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback> {
    int insert(Feedback feedback);
    List<Feedback> selectAll();
}
