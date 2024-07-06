package ru.lovkost.views.settings;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.WebStorage;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.lovkost.services.PasswordService;
import ru.lovkost.views.MainLayout;

import java.security.NoSuchAlgorithmException;

@PageTitle("Settings")
@Route(value = "settings", layout = MainLayout.class)
public class Settings extends Composite<VerticalLayout> {
    public Settings() {
        TextField key = new TextField("Key");
        Button save = new Button("Save",buttonClickEvent -> {
            if (!key.getValue().isBlank()) {
                WebStorage.setItem("key", key.getValue());
                key.clear();
                Notification notification = new Notification()
                .show("Сохранено успешно");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                return;
            }
            Notification notification = new Notification().show("Ну пусто же, ну(");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        });
        PasswordField value = new PasswordField("GeneratedKey");
        value.setReadOnly(true);
        Button generate = new Button("Generate",buttonClickEvent -> {
            try {
                var generatedKey = PasswordService.generateKey();
                value.setValue(generatedKey);
                Notification notification = new Notification().show("Обязетельно скопируйте ключ и сохраните.");
                notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        });
        getContent().add(key);
        getContent().add(save);
        getContent().add(generate);
        getContent().add(value);
    }
}
