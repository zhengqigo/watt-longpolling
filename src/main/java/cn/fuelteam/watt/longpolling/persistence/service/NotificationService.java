package cn.fuelteam.watt.longpolling.persistence.service;

import java.util.List;

import cn.fuelteam.watt.longpolling.persistence.model.Notification;

public interface NotificationService {

    public List<Notification> get(final String nodeId);

    public List<Notification> getAndRemove(final String nodeId);

    public boolean contains(final String nodeId);

    public Notification save(final Notification notification);

    public void flush();

}