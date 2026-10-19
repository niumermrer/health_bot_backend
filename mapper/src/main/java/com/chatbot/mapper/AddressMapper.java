package com.chatbot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chatbot.pojo.Address;

public interface AddressMapper extends BaseMapper<Address> {


    Address selectByUid(String uid);

    Address selectByAddressId(String addressId);

    Boolean updateByAddressId(String addressId, String province, String city, String district, String detail, String uId);

}
