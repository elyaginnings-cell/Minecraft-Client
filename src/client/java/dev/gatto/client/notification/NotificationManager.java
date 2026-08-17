package dev.gatto.client.notification;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NotificationManager {
    public enum Type {
        INFO, SUCCESS, WARNING, ERROR
    }

    public static class Notification {
        public final String message;
        public final Type type;
        public final long createdAt;
        public final long durationMs;
        public float animation;

        public Notification(String message, Type type) {
            this.message = message;
            this.type = type;
            this.createdAt = System.currentTimeMillis();
            this.durationMs = 2500;
            this.animation = 0f;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() - createdAt > durationMs;
        }
    }

    private final List<Notification> notifications = new ArrayList<>();

    public void add(String message, Type type) {
        notifications.add(new Notification(message, type));
        if (notifications.size() > 8) {
            notifications.remove(0);
        }
    }

    public void onTick() {
        Iterator<Notification> it = notifications.iterator();
        while (it.hasNext()) {
            Notification n = it.next();
            if (n.isExpired()) {
                it.remove();
            } else {
                n.animation = Math.min(1f, n.animation + 0.15f);
            }
        }
    }

    public List<Notification> getNotifications() {
        return notifications;
    }
}
