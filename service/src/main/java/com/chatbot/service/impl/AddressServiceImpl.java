package com.chatbot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chatbot.mapper.AddressMapper;
import com.chatbot.pojo.Address;
import com.chatbot.pojo.UserAddress;
import com.chatbot.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {
    @Autowired
    private AddressMapper addressMapper;

    public Boolean addAddressAndUpdateUserAddress(Address address, Integer id) throws Exception{
        // 插入地址记录到address表中
        try {
            addressMapper.insert(address);
            UserAddress userAddress = new UserAddress();
            userAddress.setUserId(id);
            userAddress.setAddressId(address.getAddressId());
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
