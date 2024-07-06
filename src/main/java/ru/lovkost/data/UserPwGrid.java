package ru.lovkost.data;

import java.time.LocalDate;

public record UserPwGrid(String login, String password, LocalDate ttl) {

}
