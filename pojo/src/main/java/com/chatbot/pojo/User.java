package com.chatbot.pojo;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName(value ="users")
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "USERID", type = IdType.AUTO)
    private Integer userid;

    @TableField("USERNAME")
    private String username;

    @TableField("PASSWORD")
    private String password;

    @TableField("PHONE")
    private String phone;

    @TableField("AVATAR")
    private String avatar;

    @TableField("EMAIL")
    private String email;

    @TableField("STATUS")
    private Integer status;

    @TableField("GENDER")
    private String gender;

    @TableField("AGE")
    private Integer age;

    @TableField("CONTRACT")
    private String contract;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("BIRTHDATE")
    private Date birthDate;

    @TableField("DESCRIPTION")
    private String description;

    @TableField("ADDRESS")
    private String address;

    @TableField("SQ1")
    private String SQ1;

    @TableField("SQ2")
    private String SQ2;

    @TableField("SQ3")
    private String SQ3;

    @TableField("AS1")
    private String AS1;

    @TableField("AS2")
    private String AS2;

    @TableField("AS3")
    private String AS3;

    @TableField("CREATETIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField("UPDATETIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @TableField("CHATMESSAGE")
    private String chatMessage;
}
