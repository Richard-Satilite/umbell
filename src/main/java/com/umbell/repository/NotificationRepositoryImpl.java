package com.umbell.repository;

import com.umbell.models.Notification;
import com.umbell.utils.DatabaseUtil;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NotificationRepositoryImpl implements NotificationRepository {

    @Override
    public Notification save(Notification notification) {
        String sql = "INSERT INTO notifications (user_id, message, is_read, created_at) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setLong(1, notification.getUserId());
            pstmt.setString(2, notification.getMessage());
            pstmt.setBoolean(3, notification.isRead());
            pstmt.setString(4, notification.getCreatedAt().toString());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating notification failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    notification.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating notification failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving notification: " + e.getMessage());
        }
        return notification;
    }

    @Override
    public Optional<Notification> findById(Long id) {
        String sql = "SELECT * FROM notifications WHERE id = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRowToNotification(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding notification by ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Notification> findByUserId(Long userId) {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                notifications.add(mapRowToNotification(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding notifications by user ID: " + e.getMessage());
        }
        return notifications;
    }

    @Override
    public List<Notification> findUnreadByUserId(Long userId) {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE user_id = ? AND is_read = FALSE ORDER BY created_at DESC";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                notifications.add(mapRowToNotification(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding unread notifications by user ID: " + e.getMessage());
        }
        return notifications;
    }

    @Override
    public void update(Notification notification) {
        String sql = "UPDATE notifications SET user_id = ?, message = ?, is_read = ?, created_at = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, notification.getUserId());
            pstmt.setString(2, notification.getMessage());
            pstmt.setBoolean(3, notification.isRead());
            pstmt.setString(4, notification.getCreatedAt().toString());
            pstmt.setLong(5, notification.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating notification: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM notifications WHERE id = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting notification: " + e.getMessage());
        }
    }

    @Override
    public void markAsRead(Long id) {
        String sql = "UPDATE notifications SET is_read = TRUE WHERE id = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error marking notification as read: " + e.getMessage());
        }
    }

    private Notification mapRowToNotification(ResultSet rs) throws SQLException {
        return new Notification(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("message"),
                rs.getBoolean("is_read"),
                LocalDateTime.parse(rs.getString("created_at"))
        );
    }
} 