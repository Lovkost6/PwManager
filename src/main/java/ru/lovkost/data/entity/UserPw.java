package ru.lovkost.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class UserPw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String login;
    @NonNull
    private String password;
    @NonNull
    @ManyToOne
    private Site site;
    private LocalDate ttl;

}
