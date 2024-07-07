package ru.lovkost.security;

import com.vaadin.flow.component.page.WebStorage;
import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.lovkost.data.entity.User;
import ru.lovkost.repository.UserRepository;

import java.util.Optional;

@Component
public class AuthenticatedUser {

    @Autowired
    private UserRepository userRepository;
    private final AuthenticationContext authenticationContext;


    public AuthenticatedUser(AuthenticationContext authenticationContext, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.authenticationContext = authenticationContext;
    }





    private Optional<Authentication> getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        return Optional.ofNullable(context.getAuthentication())
                .filter(authentication -> !(authentication instanceof AnonymousAuthenticationToken));
    }

    public Optional<User> get() {
        return getAuthentication().map(authentication -> userRepository.findByUsername(authentication.getName()));
    }

    public void logout() {
        authenticationContext.logout();
        WebStorage.removeItem("key");
    }


}

