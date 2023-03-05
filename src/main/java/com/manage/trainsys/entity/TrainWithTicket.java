package com.manage.trainsys.entity;

import lombok.Data;

import java.util.List;

@Data
public class TrainWithTicket {
    private Train train;
    private List<Ticket> listTickets;
}
