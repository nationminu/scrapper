package com.souljam.crawler.repository;

import org.springframework.data.jpa.repository.JpaRepository; 

import com.souljam.crawler.domain.SiteVO;
  
public interface SiteRepository extends JpaRepository<SiteVO, String> {  
}