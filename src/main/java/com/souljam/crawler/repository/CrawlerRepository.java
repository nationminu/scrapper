package com.souljam.crawler.repository;

import java.util.List;
 
import org.springframework.data.repository.Repository;

import com.souljam.crawler.domain.CrawlerVO; 
 
public interface CrawlerRepository extends Repository<CrawlerVO, Integer> {
    List<CrawlerVO> findByDate(String date); 
}
