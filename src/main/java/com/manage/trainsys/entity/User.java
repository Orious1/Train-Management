package com.manage.trainsys.entity;

import lombok.Data;

@Data
public class User {
    private String user_id;
    private String name;
    private String account;
    private String password;
    private String identity_id;
    private String phone_number;
    private String email;
    private String live_position;
    private String remark;
}
