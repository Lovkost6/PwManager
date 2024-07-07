package ru.lovkost.views.signin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import ru.lovkost.views.signup.RegistrationView;

@PageTitle("Login")
@Route(value = "login")
public class LoginView extends LoginOverlay implements BeforeEnterObserver {


    public LoginView() {
        setAction("login");

        LoginI18n i18n = LoginI18n.createDefault();
        RouterLink registration = new RouterLink("Registration", RegistrationView.class);
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("Password Manager");
        i18n.setAdditionalInformation(null);
        setI18n(i18n);
        getFooter().add(registration);
        setForgotPasswordButtonVisible(false);
        setOpened(true);
    }
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            setError(true);
        }
    }
}
