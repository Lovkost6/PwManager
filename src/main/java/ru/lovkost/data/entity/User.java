package ru.lovkost.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import ru.lovkost.data.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "application_user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String name;
    @JsonIgnore
    private String hashedPassword;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany
    private List<UserPw> sitePws = new ArrayList<>();
}
