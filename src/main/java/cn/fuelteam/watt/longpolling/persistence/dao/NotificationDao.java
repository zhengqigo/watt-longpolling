package cn.fuelteam.watt.longpolling.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.fuelteam.watt.longpolling.persistence.model.Notification;

import java.util.Date;
import java.util.List;

public interface NotificationDao extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.nodeId = :nodeId AND n.timestamp > :timestamp")
    public List<Notification> getNotifications(@Param("nodeId") final String nodeId,
            @Param("timestamp") final Date timestamp);
}