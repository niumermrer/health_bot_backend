package com.chatbot.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "feedback")
@Data
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "FEEDBACKID", type = IdType.AUTO)
    private Integer feedbackId;

    @TableField("USERID")
    private Integer userId;

    @TableField("CONTENT")
    private String content;

    @TableField("EMAIL")
    private String email;

    @TableField("RATING")
    private Integer rating;

    @TableField("CREATETIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField("UPDATETIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @TableField("COMMENTS")
    private String comments;
}
