package com.chatbot.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value ="user_address")
@Data
public class UserAddress {

    @TableId(value = "UID", type = IdType.AUTO)
    private Integer uId;

    @TableField("USERID")
    private Integer userId;

    @TableField("ADDRESSID")
    private String addressId;

}
