package ru.lovkost.views.signup;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import lombok.AllArgsConstructor;
import ru.lovkost.data.entity.User;
import ru.lovkost.services.UserService;
import ru.lovkost.views.signin.LoginView;

@Route("registration")
@PageTitle("Registration")
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {

    private final UserService userService;

    public RegistrationView(UserService userService) {

        this.userService = userService;
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 title = new H1("Registration");

        RouterLink routerLink = new RouterLink("Login", LoginView.class);

        TextField username = new TextField("Username");
        username.setMinLength(5);
        username.setRequired(true);

        TextField name = new TextField("Name");
        name.setMinLength(5);
        name.setRequired(true);

        PasswordField password = new PasswordField("Password");
        password.setMinLength(6);
        password.setRequired(true);

        PasswordField passwordConfirm = new PasswordField("Confirm password");

        Button register = new Button("Register",buttonClickEvent -> {
            if(!password.getValue().trim().equals(passwordConfirm.getValue().trim())) {
                passwordConfirm.setErrorMessage("Passwords do not match");
                passwordConfirm.setInvalid(true);
                return;
            }
                var isRegistredSucceed = userService.registration(username.getValue().trim(),name.getValue().trim(),password.getValue().trim());
                if(!isRegistredSucceed) {
                    username.setErrorMessage("Username already exists");
                    username.setInvalid(true);
                    return;
                }
            UI.getCurrent().navigate(LoginView.class);
        });


        add(title,username, name, password, passwordConfirm, register,routerLink);

    }
}
