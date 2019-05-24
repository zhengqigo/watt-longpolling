package cn.fuelteam.watt.longpolling.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.fuelteam.watt.longpolling.persistence.model.Notification;
import cn.fuelteam.watt.longpolling.persistence.service.NotificationService;

@Component
public class DatabaseListener {

    @Value("${cluster.nodeid}")
    private String nodeId;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EventSimulator eventSimulator;

    @Scheduled(fixedRate = 5000)
    public void checkNotifications() {
        if (notificationService.contains(nodeId)) {
            List<Notification> notifications = notificationService.getAndRemove(nodeId);
            eventSimulator.simulateOutgoingNotification(notifications);
        }
    }
}