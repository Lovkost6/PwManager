package ru.lovkost.views.create;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.WebStorage;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import jakarta.annotation.security.PermitAll;
import lombok.SneakyThrows;
import ru.lovkost.data.entity.Site;
import ru.lovkost.data.entity.User;
import ru.lovkost.security.AuthenticatedUser;
import ru.lovkost.services.NotificationService;
import ru.lovkost.services.PasswordService;
import ru.lovkost.services.PwManagerService;
import ru.lovkost.services.SiteService;
import ru.lovkost.views.MainLayout;

import java.util.Optional;


@PageTitle("Create")
@Route(value = "create", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class CreateView extends Composite<VerticalLayout> {

    private final PwManagerService service;
    private final SiteService siteService;
    private final AuthenticatedUser authenticatedUser;
    private String key;

    public CreateView(PwManagerService service, SiteService siteService, AuthenticatedUser authenticatedUser) {
        this.service = service;
        this.siteService = siteService;
        this.authenticatedUser = authenticatedUser;
        WebStorage.getItem( "key", value -> key = value);
        TextField login = new TextField("Login");
        PasswordField password = new PasswordField("Password");
        TextField url = new TextField("Site url");
        ComboBox<String> ttlField = new ComboBox<>("Ttl");
        Button save = new Button("Save",buttonClickEvent -> {
            Site site;
            try {
                site = siteService.findSiteByUrlFromClient(url.getValue());
            }catch (Exception e){
                url.setErrorMessage(e.getMessage());
                return;
            }
            if (key == null) {
                NotificationService.show("Нет ключа", NotificationVariant.LUMO_ERROR);
                return;
            }
            var user = getCurrentUser();
            service.createPw(user,encrypt(login.getValue()),encrypt(password.getValue()),site,ttlField.getValue());
            UI.getCurrent().refreshCurrentRoute(true);
            Notification.show("Успешно").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

        ttlField.setItems( "1 день", "1 неделя", "1 месяц","6 месяцев","1 год","2 года","Без ограничений");
        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("30%");
        formLayout.add(login, password, password, url,
                ttlField,save);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1));
        getContent().add(formLayout);
    }

    private User getCurrentUser() {
        Optional<User> currentUser = authenticatedUser.get();
        return currentUser.get();
    }


    @SneakyThrows
    private String encrypt(String value){
        return PasswordService.encrypt(value,key);
    }

}
