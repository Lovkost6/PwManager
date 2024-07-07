package ru.lovkost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lovkost.data.entity.Site;
import ru.lovkost.data.entity.User;
import ru.lovkost.data.entity.UserPw;

import java.util.List;

public interface PwManagerRepository extends JpaRepository<UserPw, Long> {
    List<UserPw> findUserPwBySite(Site site);
    List<UserPw> findUserPwByOwner(User user);
}
