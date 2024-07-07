package ru.lovkost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lovkost.data.entity.Site;

import javax.annotation.Nullable;

public interface SiteRepository extends JpaRepository<Site, Long> {
   @Nullable
   Site findSiteByUrl(String url);
}
