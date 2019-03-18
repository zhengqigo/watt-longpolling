package cn.fuelteam.watt.longpolling.longpolling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.fuelteam.watt.longpolling.persistence.service.NodeNotificationService;

@Component
public class LongPollingDatabaseListener {

    @Value("${cluster.nodeid}")
    private String nodeId;

    @Autowired
    private NodeNotificationService dbService;

    @Autowired
    private LongPollingEventSimulator simulator;

    @Scheduled(fixedRate = 5000)
    public void checkNotifications() {
        if (dbService.containsNotifications(nodeId)) {
            simulator.simulateOutgoingNotification(dbService.getAndRemoveNotifications(nodeId));
        }
    }
}
