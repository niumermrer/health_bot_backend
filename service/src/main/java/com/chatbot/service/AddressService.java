package com.chatbot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chatbot.mapper.AddressMapper;
import com.chatbot.mapper.UserAddressMapper;
import com.chatbot.pojo.Address;
import com.chatbot.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface AddressService extends IService<Address> {

    public Boolean addAddressAndUpdateUserAddress(Address address, Integer id) throws Exception;

}
