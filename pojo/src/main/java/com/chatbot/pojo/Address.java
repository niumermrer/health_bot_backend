package com.chatbot.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value ="address")
@Data
public class Address {

    @TableField("ADDRESSID")
    private String addressId;;

    @TableField("PROVINCE")
    private String province;

    @TableField("CITY")
    private String city;

    @TableField("DISTRICT")
    private String district;

    @Override
    public String toString() {
        return "Address{" +
                "addressId:'" + addressId + '\'' +
                ", province:'" + province + '\'' +
                ", city:'" + city + '\'' +
                ", district:'" + district + '\'' +
                '}';
    }
}
