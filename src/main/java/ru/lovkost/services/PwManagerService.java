package ru.lovkost.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lovkost.repository.PwManagerRepository;
import ru.lovkost.data.entity.Site;
import ru.lovkost.data.entity.UserPw;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PwManagerService {
    private final PwManagerRepository repository;


    public List<UserPw> findALl() {
        return repository.findAll();
    }

    public List<UserPw> findBySiteId(Site site) {
        return repository.findUserPwBySite(site);
    }


    public void createPw(UserPw userPw) {
        repository.save(userPw);
    }

    public void createPw(String login, String password, Site site, String ttl) {
        var newPw = new UserPw();
        newPw.setLogin(login);
        newPw.setPassword(password);
        newPw.setSite(site);
        var timeNow =  LocalDateTime.now();
        switch (ttl){
            case "1 день" -> timeNow = timeNow.plusDays(1);
            case "1 неделя" -> timeNow = timeNow.plusWeeks(1);
            case "1 месяц" -> timeNow = timeNow.plusMonths(1);
            case "6 месяцев" -> timeNow = timeNow.plusMonths(6);
            case "1 год" -> timeNow = timeNow.plusYears(1);
            case "2 года" ->timeNow =  timeNow.plusYears(2);
            case "Без ограничений" ->  timeNow = null;

        }

        newPw.setTtl(timeNow != null ? timeNow.toLocalDate() : null);
        repository.save(newPw);
    }

}
