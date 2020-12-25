package com.souljam.crawler.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.souljam.crawler.domain.SiteVO;

@RepositoryRestResource(exported = false) 
public interface SiteRepository extends JpaRepository<SiteVO, String> {   
}