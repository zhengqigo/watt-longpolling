package cn.fuelteam.watt.longpolling.persistence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.fuelteam.watt.longpolling.persistence.dao.NotificationDao;
import cn.fuelteam.watt.longpolling.persistence.model.Notification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    private Date getTimestamp() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, -10);
        return cal.getTime();
    }

    @Override
    public List<Notification> get(final String nodeId) {
        return notificationDao.getNotifications(nodeId, getTimestamp());
    }

    @Override
    public List<Notification> getAndRemove(final String nodeId) {
        final List<Notification> notifications = get(nodeId);

        // Create a copy of the list before we delete the database entities
        final List<Notification> clonedNotifications = new ArrayList<Notification>(notifications);

        // delete the database entities so we don't repeat notification sending
        notifications.stream().forEach(node -> notificationDao.delete(node));
        flush();

        // return the copied list
        return clonedNotifications;
    }

    @Override
    public boolean contains(final String nodeId) {
        return !notificationDao.getNotifications(nodeId, getTimestamp()).isEmpty();
    }

    @Override
    public Notification save(final Notification notification) {
        return notificationDao.save(notification);
    }

    @Override
    public void flush() {
        notificationDao.flush();
    }
}