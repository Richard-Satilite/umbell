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
        String sql = "INSERT INTO Notification (name, message, read, user_email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, notification.getName());
            pstmt.setString(2, notification.getMessage());
            pstmt.setBoolean(3, notification.isRead());
            pstmt.setString(4, notification.getUserEmail());

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
        String sql = "SELECT * FROM Notification WHERE id = ?";
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
        String sql = "SELECT n.* FROM Notification n " +
                    "JOIN User u ON n.user_email = u.email " +
                    "WHERE u.id = ?";
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
        String sql = "SELECT n.* FROM Notification n " +
                    "JOIN User u ON n.user_email = u.email " +
                    "WHERE u.id = ? AND n.read = FALSE";
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
        String sql = "UPDATE Notification SET name = ?, message = ?, read = ?, user_email = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, notification.getName());
            pstmt.setString(2, notification.getMessage());
            pstmt.setBoolean(3, notification.isRead());
            pstmt.setString(4, notification.getUserEmail());
            pstmt.setLong(5, notification.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating notification: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM Notification WHERE id = ?";
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
        String sql = "UPDATE Notification SET read = TRUE WHERE id = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error marking notification as read: " + e.getMessage());
        }
    }

    @Override
    public List<Notification> findUnreadByUserEmail(String userEmail) {
        String sql = "SELECT * FROM Notification WHERE user_email = ? AND read = FALSE";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, userEmail);
            ResultSet rs = stmt.executeQuery();
            
            List<Notification> notifications = new ArrayList<>();
            while (rs.next()) {
                Notification notification = new Notification(
                    rs.getString("name"),
                    rs.getString("message"),
                    rs.getString("user_email")
                );
                notification.setId(rs.getLong("id"));
                notification.setRead(rs.getBoolean("read"));
                notifications.add(notification);
            }
            return notifications;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Notification> findByUserEmail(String userEmail) {
        String sql = "SELECT * FROM Notification WHERE user_email = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, userEmail);
            ResultSet rs = stmt.executeQuery();
            
            List<Notification> notifications = new ArrayList<>();
            while (rs.next()) {
                Notification notification = new Notification(
                    rs.getString("name"),
                    rs.getString("message"),
                    rs.getString("user_email")
                );
                notification.setId(rs.getLong("id"));
                notification.setRead(rs.getBoolean("read"));
                notifications.add(notification);
            }
            return notifications;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private Notification mapRowToNotification(ResultSet rs) throws SQLException {
        Notification notification = new Notification(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("message"),
                rs.getBoolean("read"),
                rs.getString("user_email")
        );
        notification.setCreatedAt(LocalDateTime.now());
        return notification;
    }
} 