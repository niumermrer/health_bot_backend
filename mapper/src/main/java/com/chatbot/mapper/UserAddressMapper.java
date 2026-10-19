package com.chatbot.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chatbot.pojo.UserAddress;

import java.util.List;


public interface UserAddressMapper extends BaseMapper<UserAddress>{

   UserAddress selectByUserId(Integer userId);

}
