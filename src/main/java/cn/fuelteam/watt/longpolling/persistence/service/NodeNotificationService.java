package cn.fuelteam.watt.longpolling.persistence.service;

import java.util.List;

import cn.fuelteam.watt.longpolling.persistence.model.NodeNotification;

public interface NodeNotificationService {

    public List<NodeNotification> getNotifications(final String nodeId);

    public List<NodeNotification> getAndRemoveNotifications(final String nodeId);

    public boolean containsNotifications(final String nodeId);

    public NodeNotification save(final NodeNotification node);

    public void flush();

}
