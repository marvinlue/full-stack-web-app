package com.project3.api.entities.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SiteService {
private final SiteRepository siteRepository;

    @Autowired
    public SiteService(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    public void addNewSite(Site site) {
        String name = site.getSiteName();
        if (getSiteByName(name) != null) {
            throw new IllegalStateException("Site with name " + name + " already exists!");
        }
        siteRepository.save(site);
    }

    public void deleteSite(Long sId) {
        siteRepository.deleteById(sId);
    }

    public Site getSiteByID(Long sId) {
        Optional<Site> siteById = siteRepository.findSiteBySid(sId);
        if (!siteById.isPresent()) {
            throw new IllegalStateException("Site with id " + sId + " does not exist!");
        }
        return siteById.get();
    }

    public Site getSiteByName(String name) {
        return siteRepository.findSiteBySiteName(name);
    }

    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }
}
