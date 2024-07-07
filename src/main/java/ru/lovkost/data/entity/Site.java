package ru.lovkost.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @OneToMany
    private List<UserPw> pws = new ArrayList<UserPw>();

    @Override
    public String toString() {
        return url;
    }
}
