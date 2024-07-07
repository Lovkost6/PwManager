package ru.lovkost.data.entity;

import java.time.LocalDate;

public record UserPwGrid(String login, String password, LocalDate ttl) {

}
