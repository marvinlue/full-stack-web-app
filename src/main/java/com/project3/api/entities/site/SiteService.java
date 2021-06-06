package com.project3.api.entities.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteService {
private SiteRepository siteRepository;

    @Autowired
    public SiteService(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }


    public void addNewSite(Site site) {
        siteRepository.save(site);
    }

    public void deleteSite(Long sId) {
        siteRepository.deleteById(sId);
    }

    public Site getSiteByID(Long sId) {
        return siteRepository.getById(sId);
    }

    public Site getSiteByName(String name) {
        return siteRepository.findSiteBySiteName(name);
    }

    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }
}
