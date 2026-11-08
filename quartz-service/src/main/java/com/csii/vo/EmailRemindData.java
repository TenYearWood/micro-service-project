package com.csii.vo;

import lombok.Data;

@Data
public class EmailRemindData {

    private Integer id;

    private String address;

    private String username;

    private String title;

    private String content;

    private String cronExpression;
}
