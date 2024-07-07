package ru.lovkost.views.list;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.WebStorage;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import lombok.SneakyThrows;
import ru.lovkost.data.entity.User;
import ru.lovkost.data.entity.UserPw;
import ru.lovkost.data.entity.UserPwGrid;
import ru.lovkost.security.AuthenticatedUser;
import ru.lovkost.services.PasswordService;
import ru.lovkost.services.PwManagerService;
import ru.lovkost.services.SiteService;
import ru.lovkost.views.MainLayout;

import java.util.List;
import java.util.Optional;


@PageTitle("List")
@Route(value = "pws", layout = MainLayout.class)
@Uses(Icon.class)
@PermitAll
public class ListView extends Composite<VerticalLayout> {

    private final PwManagerService servicePws;
    private final SiteService serviceSite;
    private AuthenticatedUser authenticatedUser;
    private String key;

    public ListView(PwManagerService service, SiteService serviceSite, AuthenticatedUser authenticatedUser) {
        this.servicePws = service;
        this.serviceSite = serviceSite;
        this.authenticatedUser = authenticatedUser;
        WebStorage.getItem("key", value -> {
            key = value;
        });
        Accordion accordion = new Accordion();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        accordion.setWidth("100%");
        setAccordionSampleData(accordion);
        accordion.close();
        getContent().add(accordion);
    }

    private void setAccordionSampleData(Accordion accordion) {

        Optional<User> maybeUser = authenticatedUser.get();
        var user = maybeUser.get();
        var allSitesIdByUser = servicePws.findSiteByUser(user);

        for (var site : allSitesIdByUser) {
            accordion.add(site.getUrl(),new Grid<>(UserPw.class));
        }
        accordion.addOpenedChangeListener(openedChangeEvent -> {
            Grid<UserPwGrid> usersPwByOpenAccrod = new Grid<>(UserPwGrid.class);
            if (openedChangeEvent.getOpenedPanel().isPresent()) {
                var openedPanel = openedChangeEvent.getOpenedPanel();
                String panelName = openedPanel.get().getSummaryText();
            var site = serviceSite.findSiteByUrl(panelName);
            var pws = servicePws.findBySiteId(site);
            var showPws = decryptUserPwIfKeyExists(pws);
            usersPwByOpenAccrod.setItems(showPws);
            openedChangeEvent.getOpenedPanel().get().removeAll();
            openedChangeEvent.getOpenedPanel().get().add(usersPwByOpenAccrod);
        }
        });
    }

    private List<UserPwGrid> decryptUserPwIfKeyExists(List<UserPw> pws) {
        if (key != null) {
            return pws.stream().map(e->  new UserPwGrid(decrypt(e.getLogin()),decrypt(e.getPassword()),e.getTtl()))
                    .toList();
        }
        return pws.stream().map(e->  new UserPwGrid(e.getLogin(),e.getPassword(),e.getTtl()))
                .toList();

    }

    @SneakyThrows
    private String decrypt(String value){
        return PasswordService.decrypt(value,key);
    }
}




