package com.yearns.kafka.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderEntity implements Serializable {

    private Integer id;

    private String userId;

    //其他字段待补充

    private String username;

    private String message;

    private Integer status;

    private Date createTime;

    private Date updateTime;

}
