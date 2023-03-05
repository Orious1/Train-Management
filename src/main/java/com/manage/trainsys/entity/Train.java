package com.manage.trainsys.entity;

import lombok.Data;

@Data
public class Train {
    private String train_number;
    private String departure_time;
    private String arrive_time;
    private String origin;
    private String destination;
}
