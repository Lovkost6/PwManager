package ru.lovkost.data;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nullable;

public interface SiteRepository extends JpaRepository<Site, Long> {
   @Nullable
   Site findSiteByUrl(String url);
}
