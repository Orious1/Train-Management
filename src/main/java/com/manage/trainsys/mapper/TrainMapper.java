package com.manage.trainsys.mapper;

import com.manage.trainsys.entity.Ticket;
import com.manage.trainsys.entity.Train;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TrainMapper {
    @Select("SELECT * FROM train WHERE train_number=#{train_number}")
    Train findTrainByNumber(@Param("train_number") String train_number);

    @Select("SELECT * FROM ticket_mag WHERE train_number=#{train_number}")
    List<Ticket> findTicketByNumber(@Param("train_number") String train_number);

    @Select("SELECT * FROM train")
    List<Train> findAllTrain();

    @Select("SELECT * FROM train WHERE ${originField}=#{origin}  AND ${destinationField}=#{destination} AND ${departure_timeField} LIKE CONCAT('%',#{departure_time},'%')")
    List<Train> findTrainByConditions(@Param("origin") String origin, @Param("originField") String originField,
                                      @Param("destination") String destination, @Param("destinationField") String destinationField,
                                      @Param("departure_time") String departure_time, @Param("departure_timeField") String departure_timeField);

    @Update("UPDATE ticket_mag SET ticket_left=ticket_left-1 WHERE train_number=#{train_number} AND ticket_level=#{ticket_level}")
    boolean ticketLeftDecline(@Param("train_number") String train_number, @Param("ticket_level") String ticket_level);

    @Select("SELECT * FROM train WHERE ${originField}=#{origin}  AND ${destinationField}=#{destination} AND ${train_numberField}=#{train_number}")
    List<Train> findTrainByConditionsOther(@Param("origin") String origin, @Param("originField") String originField,
                                           @Param("destination") String destination, @Param("destinationField") String destinationField,
                                           @Param("train_number") String train_number, @Param("train_numberField") String train_numberField);

    @Insert("INSERT INTO train VALUES(#{train_number},#{departure_time},#{arrive_time},#{origin},#{destination})")
    int insertTrain(@Param("train_number") String train_number, @Param("departure_time") String departure_time,
                    @Param("arrive_time") String arrive_time, @Param("origin") String origin, @Param("destination") String destination);

    @Insert("INSERT INTO ticket_mag VALUES(#{ticket_id},#{ticket_level},#{price},#{ticket_left},#{train_number})")
    int insertTicket(@Param("ticket_id") String ticket_id, @Param("ticket_level") String ticket_level,
                     @Param("price") double price, @Param("ticket_left") int ticket_left,
                     @Param("train_number") String train_number);

    @Select("SELECT ticket_id FROM ticket_magdtl WHERE user_id=#{user_id}")
    List<String> findUserTickets(@Param("user_id") String user_id);

    @Select("SELECT *FROM ticket_mag WHERE ticket_id=#{ticket_id}")
    Ticket findTicket(@Param("ticket_id")String ticket_id);

    @Insert("INSERT INTO ticket_magdtl VALUES(#{ticket_id},#{user_id})")
    int insertUserTicket(@Param("ticket_id")String ticket_id,@Param("user_id")String user_id);
}
