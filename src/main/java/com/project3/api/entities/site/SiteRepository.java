package com.project3.api.entities.site;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<Site,Long> {
    Site findSiteBySiteName(String siteName);
    Optional<Site> findSiteBySid(Long id);
}