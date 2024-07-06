package ru.lovkost.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PwManagerRepository extends JpaRepository<UserPw, Long> {
    List<UserPw> findUserPwBySite(Site site);
}
