package ru.lovkost.services;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class NotificationService {
    public static void show(String message, NotificationVariant variant){
        Notification notification = new Notification(message);
        notification.addThemeVariants(variant);
        notification.setDuration(5000);
        notification.open();
    }
}
