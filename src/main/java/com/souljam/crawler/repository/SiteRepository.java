package com.souljam.crawler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.souljam.crawler.domain.SiteVO;
 
@RepositoryRestResource(exported = false) 
public interface SiteRepository extends JpaRepository<SiteVO, String> {  
}