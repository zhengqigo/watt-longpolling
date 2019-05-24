package cn.fuelteam.watt.longpolling.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.fuelteam.watt.longpolling.persistence.model.Notification;
import cn.fuelteam.watt.longpolling.persistence.service.NotificationService;

@Component
public class EventSimulator {

    private final BlockingQueue<LongPollingSession> longPollingQueue = new ArrayBlockingQueue<LongPollingSession>(100);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PayloadUtil payloadUtil;

    @Value("${cluster.nodenames}")
    private String[] nodeNames;

    private String createMergedMapKey(final Long userId, final String nodeId) {
        return String.format("%d-%s", userId, nodeId);
    }

    private List<Notification> mergeNotificationPayloads(final List<Notification> notifications) {
        final Map<String, Notification> mergedNotifications = new ConcurrentHashMap<String, Notification>();
        notifications.stream().forEach(nodeNotification -> {
            final String key = createMergedMapKey(nodeNotification.getUserId(), nodeNotification.getNodeId());
            if (mergedNotifications.containsKey(key)) {
                final Notification notification = mergedNotifications.get(key);
                notification.setPayload(payloadUtil.appendPayload(notification.getPayload(), nodeNotification.getPayload()));
                mergedNotifications.put(key, notification);
            }
            mergedNotifications.putIfAbsent(key, nodeNotification);
        });
        return new ArrayList<Notification>(mergedNotifications.values());
    }

    // Simulated event handler
    public void simulateIncomingNotification(final long userId) {
        final String randomData = payloadUtil.getMessagePayload(); // keep the same payload data per cluster node
        for (final String node : nodeNames) { // create a notification per cluster node
            final Notification notification = new Notification();
            notification.setUserId(userId);
            notification.setTimestamp(new Date());
            notification.setNodeId(node);
            notification.setPayload(randomData);
            notificationService.save(notification);
        }
        notificationService.flush(); // force the changes to the DB
    }

    public void simulateOutgoingNotification(final List<Notification> notifications) {
        for (final Notification nodeNotification : mergeNotificationPayloads(notifications)) {
            getPollingQueue().stream().filter(e -> e.getUserId() == nodeNotification.getUserId())
                    .forEach((final LongPollingSession lps) -> {
                        try {
                            lps.getDeferredResult().setResult(nodeNotification.getPayload());
                        } catch (Exception e) {
                            throw new RuntimeException();
                        }
                    });
        }
        getPollingQueue().removeIf(e -> e.getDeferredResult().isSetOrExpired());
    }

    public BlockingQueue<LongPollingSession> getPollingQueue() {
        return longPollingQueue;
    }
}