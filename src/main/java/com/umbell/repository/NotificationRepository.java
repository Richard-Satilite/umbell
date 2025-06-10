package com.umbell.repository;

import com.umbell.models.Notification;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository {
    Notification save(Notification notification);
    Optional<Notification> findById(Long id);
    List<Notification> findByUserId(Long userId);
    List<Notification> findUnreadByUserId(Long userId);
    void update(Notification notification);
    void delete(Long id);
    void markAsRead(Long id);
} 