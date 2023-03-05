package com.manage.trainsys.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.manage.trainsys.entity.Ticket;
import com.manage.trainsys.entity.Train;
import com.manage.trainsys.mapper.TrainMapper;
import com.manage.trainsys.service.TrainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TrainServiceImpl implements TrainService {
    @Resource
    TrainMapper trainMapper;

    @Override
    public Train searchTrain(String train_number) {
        Train train = trainMapper.findTrainByNumber(train_number);
        if (ObjectUtils.isEmpty(train)) throw new RuntimeException("车次号错误");
        return train;
    }

    @Override
    public List<Train> searchAllTrain() {
        return trainMapper.findAllTrain();
    }

    @Override
    public List<Train> searchTrainByConditions(String origin, String destination, String departure_time) {
        String originField = "1";
        String destinationField = "1";
        String departure_timeField = "1";
        System.out.println(departure_time);
        if (origin == "" && destination != "" && departure_time != "") {
            origin = "1";
            destinationField = "destination";
            departure_timeField = "departure_time";
        } else if (origin != "" && destination == "" && departure_time != "") {
            originField = "origin";
            destination = "1";
            departure_timeField = "departure_time";
        } else if (origin != "" && destination != "" && departure_time == "") {
            originField = "origin";
            destinationField = "destination";
            departure_time = "1";
        } else if (origin == "" && destination == "" && departure_time != "") {
            origin = "1";
            destination = "1";
            departure_timeField = "departure_time";
        } else if (origin == "" && destination != "" && departure_time == "") {
            origin = "1";
            destinationField = "destination";
            departure_time = "1";
        } else if (origin != "" && destination == "" && departure_time == "") {
            originField = "origin";
            destination = "1";
            departure_time = "1";
        } else if (origin != "" && destination != "" && departure_time != "") {
            originField = "origin";
            destinationField = "destination";
            departure_timeField = "departure_time";
        } else {
            return trainMapper.findAllTrain();
        }
        System.out.println("起点:" + origin + " 起点字段:" + originField + "\n终点:" + destination + " 终点字段:" + destinationField + "\n出发时间: " + departure_time + " 出发时间字段:" + departure_timeField);
        return trainMapper.findTrainByConditions(origin, originField, destination, destinationField, departure_time, departure_timeField);
    }

    @Override
    public List<Train> searchTrainByConditionsOther(String origin, String destination, String train_number) {
        String originField = "1";
        String destinationField = "1";
        String train_numberField = "1";
        if (origin == "" && destination != "" && train_number != "") {
            origin = "1";
            destinationField = "destination";
            train_numberField = "train_number";
        } else if (origin != "" && destination == "" && train_number != "") {
            originField = "origin";
            destination = "1";
            train_numberField = "train_number";
        } else if (origin != "" && destination != "" && train_number == "") {
            originField = "origin";
            destinationField = "destination";
            train_number = "1";
        } else if (origin == "" && destination == "" && train_number != "") {
            origin = "1";
            destination = "1";
            train_numberField = "train_number";
        } else if (origin == "" && destination != "" && train_number == "") {
            origin = "1";
            destinationField = "destination";
            train_number = "1";
        } else if (origin != "" && destination == "" && train_number == "") {
            originField = "origin";
            destination = "1";
            train_number = "1";
        } else if (origin != "" && destination != "" && train_number != "") {
            originField = "origin";
            destinationField = "destination";
            train_numberField = "train_number";
        } else {
            return trainMapper.findAllTrain();
        }
        System.out.println("起点:" + origin + " 起点字段:" + originField + "\n终点:" + destination + " 终点字段:" + destinationField + "\n车次号: " + train_number + " 车次号字段:" + train_numberField);
        return trainMapper.findTrainByConditionsOther(origin, originField, destination, destinationField, train_number, train_numberField);
    }

    @Override
    public List<Ticket> searchTicket(String train_number) {
        List<Ticket> tickets = trainMapper.findTicketByNumber(train_number);
        if (ObjectUtils.isEmpty(tickets)) throw new RuntimeException("车次号错误，查询不到票");
        return tickets;
    }

    @Override
    public String buyOneTicket(String train_number, String ticket_level,String user_id) {
        boolean flag = trainMapper.ticketLeftDecline(train_number, ticket_level);
        if (flag) {
            switch (ticket_level) {
                case "一等座":
                    train_number += "_1";
                    break;
                case "二等座":
                    train_number += "_2";
                    break;
                case "商务座":
                    train_number += "_3";
                    break;
            }
            trainMapper.insertUserTicket(train_number,user_id);
            return "购买成功";
        }
        else {
            return "未找到车次号或者相应等级的座位购买失败";
        }
    }

    @Override
    public int insertTrain(Map<String, Object> map) {
        LinkedHashMap m = (LinkedHashMap) map.get("params");
        LinkedHashMap oneSeat = (LinkedHashMap) m.get("oneSeat");
        LinkedHashMap twoSeat = (LinkedHashMap) m.get("twoSeat");
        LinkedHashMap businessSeat = (LinkedHashMap) m.get("businessSeat");
        String train_number = (String) m.get("addTrainNum");
        Train train=trainMapper.findTrainByNumber(train_number);
        if (!ObjectUtils.isEmpty(train)) throw new RuntimeException("车次号已经存在");
        String departure_time = (String) m.get("addStartTime");
        String arrive_time = (String) m.get("addArriveTime");
        String origin = (String) m.get("addOrigin");
        String destination = (String) m.get("addDestination");
        String ticket_id1=train_number+"_1";
        String ticket_id2=train_number+"_2";
        String ticket_id3=train_number+"_3";
        String ticket_level1="一等座";
        String ticket_level2="二等座";
        String ticket_level3="商务座";
        Double price1=Double.parseDouble((String) oneSeat.get("price"));
        Double price2=Double.parseDouble((String) twoSeat.get("price"));
        Double price3=Double.parseDouble((String) businessSeat.get("price"));
        int ticket_left1=Integer.parseInt((String) oneSeat.get("count"));
        int ticket_left2=Integer.parseInt((String) twoSeat.get("count"));
        int ticket_left3=Integer.parseInt((String) businessSeat.get("count"));
        // System.out.println(price1+" "+price2+" "+price3+" "+ticket_left1+" "+ticket_left2+" "+ticket_left3);
        int a=trainMapper.insertTrain(train_number,departure_time,arrive_time,origin,destination);
        int b=trainMapper.insertTicket(ticket_id1,ticket_level1,price1,ticket_left1,train_number);
        int c=trainMapper.insertTicket(ticket_id2,ticket_level2,price2,ticket_left2,train_number);
        int d=trainMapper.insertTicket(ticket_id3,ticket_level3,price3,ticket_left3,train_number);
        return a+b+c+d;
    }

    @Override
    public List<JSONObject> findUserTickets(String user_id) {
        List<String> ticketIdList=trainMapper.findUserTickets(user_id);
        List<Ticket> listTickets=new ArrayList<>();
        List<Train> listTrain=new ArrayList<>();
        for (String i: ticketIdList){
            listTickets.add(trainMapper.findTicket(i));
            listTrain.add(trainMapper.findTrainByNumber(trainMapper.findTicket(i).getTrain_number()));
        }
        String jsonTickets= JSON.toJSONString(listTickets);
        String jsonTrain=JSON.toJSONString(listTrain);
        JSONArray jsonArrayTicket=JSONArray.parseArray(jsonTickets);
        JSONArray jsonArrayTrain=JSONArray.parseArray(jsonTrain);
        List<JSONObject> list=new ArrayList<>();
        for (int i=0;i<jsonArrayTicket.size();i++){
            JSONObject jsonTrainWithTickets=new JSONObject();
            jsonTrainWithTickets.putAll(jsonArrayTicket.getJSONObject(i));
            jsonTrainWithTickets.putAll(jsonArrayTrain.getJSONObject(i));
            list.add(jsonTrainWithTickets);
        }
       return list;
    }
}
