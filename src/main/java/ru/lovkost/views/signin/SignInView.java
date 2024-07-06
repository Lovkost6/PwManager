package ru.lovkost.views.signin;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.lovkost.views.MainLayout;

@PageTitle("SignIn")
@Route(value = "singin", layout = MainLayout.class)
public class SignInView extends Composite<VerticalLayout> {

    public SignInView() {
        LoginForm loginForm = new LoginForm();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().add(loginForm);
    }
}
