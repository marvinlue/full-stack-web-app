package com.project3.api.entities.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/site/")
public class SiteController {
    private final SiteService siteService;

    @Autowired
    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }

    @GetMapping(path = "all")
    public List<Site> getAllSites(){
        return siteService.getAllSites();
    }

    @PostMapping()
    public void addNewSite(@RequestBody Site site){
        siteService.addNewSite(site);
    }

    @DeleteMapping(path = "{sid}")
    public void deleteSiteById(@PathVariable("sid") Long sId){
        siteService.deleteSite(sId);
    }

    @GetMapping("id")
    public Site getSiteByID(@RequestParam Long sId){
        return siteService.getSiteByID(sId);
    }

    @GetMapping("name")
    public Site getSiteByName(@RequestParam String name){
        return siteService.getSiteByName(name);
    }
}
