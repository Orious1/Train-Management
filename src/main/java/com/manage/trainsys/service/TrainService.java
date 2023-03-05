package com.manage.trainsys.service;


import com.alibaba.fastjson.JSONObject;
import com.manage.trainsys.entity.Ticket;
import com.manage.trainsys.entity.Train;

import java.util.List;
import java.util.Map;

public interface TrainService {

    Train searchTrain(String train_number);

    List<Train> searchAllTrain();

    List<Ticket> searchTicket(String train_number);

    List<Train> searchTrainByConditions(String origin, String destination, String departure_time);

    List<Train> searchTrainByConditionsOther(String origin, String destination, String train_number);

    String buyOneTicket(String train_number, String ticket_level,String user_id);

    int insertTrain(Map<String, Object> map);

    List<JSONObject> findUserTickets(String user_id);
}
