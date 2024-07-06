package ru.lovkost.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.lovkost.data.Site;
import ru.lovkost.data.SiteRepository;

import java.net.URI;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SiteService {
    private final SiteRepository _repository;

    public List<Site> getSites() {
        return _repository.findAll();
    }

    @SneakyThrows
    public Site findSiteByUrlFromClient(String url) {
        var uri = new URI(url).getHost();
        var site =_repository.findSiteByUrl(uri);
        if(site == null) {
            var newSite = new Site();
            newSite.setUrl(uri);
            site = _repository.save(newSite);
        }
        return site;
    }
    @SneakyThrows
    public Site findSiteByUrl(String url) {
        var site =_repository.findSiteByUrl(url);
        return site;
    }
}
