package com.manage.trainsys.controller;

import com.alibaba.fastjson.JSONObject;
import com.manage.trainsys.entity.Ticket;
import com.manage.trainsys.entity.Train;
import com.manage.trainsys.entity.TrainWithTicket;
import com.manage.trainsys.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/train")
public class TrainController {
    private TrainService trainService;

    @Autowired
    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    // 要把数据包装成json返回给前端 返回车次
    @PostMapping("/train_number")
    public Train searchTrain(String train_number) {
        try {
            return trainService.searchTrain(train_number);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // 要把数据包装成json返回给前端 返回车票信息
    @PostMapping("/tickets")
    public List<Ticket> searchTickets(String train_number) {
        try {
            return trainService.searchTicket(train_number);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @RequestMapping("/searchPart")
    public List<TrainWithTicket> searchTrainWithTicketListPart(String origin, String destination, String date) {
        List<Train> lists = trainService.searchTrainByConditions(origin, destination, date);
        return CombineTrainWithTickets(lists);
    }

    @RequestMapping("/searchPart/other")
    public List<TrainWithTicket> searchTrainWithTicketListPartOther(String origin, String destination, String train_number) {
        List<Train> lists = trainService.searchTrainByConditionsOther(origin, destination, train_number);
        return CombineTrainWithTickets(lists);
    }

    @RequestMapping("/searchAll")
    public List<TrainWithTicket> searchTrainWithTicketListAll() {
        List<Train> listsT = trainService.searchAllTrain();
        return CombineTrainWithTickets(listsT);
    }

    @RequestMapping("/buyTicket")
    public String buyTicket(String train_number, String ticket_level,String user_id) {
        return trainService.buyOneTicket(train_number, ticket_level,user_id);
    }

    private List<TrainWithTicket> CombineTrainWithTickets(List<Train> lists) {
        List<TrainWithTicket> listTWT = new ArrayList<>();
        for (Train i : lists) {
            TrainWithTicket twt = new TrainWithTicket();
            List<Ticket> listTk = trainService.searchTicket(i.getTrain_number());
            twt.setTrain(i);
            twt.setListTickets(listTk);
            listTWT.add(twt);
        }
        return listTWT;
    }

    @RequestMapping("/all")
    public List<Train> aa() {
        return trainService.searchAllTrain();
    }

    @RequestMapping("/manage/insertTrain")
    public int addTrain(@RequestBody Map<String, Object> map) {
        try{
            return trainService.insertTrain(map);
        } catch (NumberFormatException e){
            System.out.println(e);
            return 2;
        }
        catch (RuntimeException e){
            System.out.println(e);
            return 1;
        }
    }

    @RequestMapping("/search/userTickets")
    public List<JSONObject> findUserTickets(String user_id){
        return trainService.findUserTickets(user_id);
    }
}
