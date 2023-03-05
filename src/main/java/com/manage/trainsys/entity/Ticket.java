package com.manage.trainsys.entity;

import lombok.Data;

@Data
public class Ticket {
    private String ticket_id;
    private String ticket_level;
    private double price;
    private int ticket_left;
    private String train_number;
}
