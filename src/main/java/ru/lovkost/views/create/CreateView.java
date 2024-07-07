package ru.lovkost.views.create;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
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
import ru.lovkost.services.NotificationService;
import ru.lovkost.services.PasswordService;
import ru.lovkost.services.PwManagerService;
import ru.lovkost.services.SiteService;
import ru.lovkost.views.MainLayout;


@PageTitle("Create")
@Route(value = "create", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class CreateView extends Composite<VerticalLayout> {

    private final PwManagerService service;
    private final SiteService siteService;
    private String key;

    public CreateView(PwManagerService service, SiteService siteService) {
        this.service = service;
        this.siteService = siteService;
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
            service.createPw(encrypt(login.getValue()),encrypt(password.getValue()),site,ttlField.getValue());
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


    @SneakyThrows
    private String encrypt(String value){
        return PasswordService.encrypt(value,key);
    }

}
