package com.souljam.crawler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import com.souljam.crawler.domain.SiteVO;
import com.souljam.crawler.repository.SiteRepository;

import lombok.extern.slf4j.Slf4j;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class HomeController {
	
	@Value("${spring.application.name}")
	private String appName;

    @Autowired
    private SiteRepository siteRepository;
    
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@RequestMapping(method = RequestMethod.GET, path = "/all")
	public ResponseEntity<Object> list() throws Exception{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Retry-After", "3600");

		Map<String, String> map = new HashMap<String, String>();

		List<SiteVO> sv = null;
		
		try {
			sv = siteRepository.findAll();
			log.info(sv.toString());
		} catch (Exception e) {
			log.error(e.toString());
		}
 
    	return new ResponseEntity<>(sv, headers, HttpStatus.OK);   
	}
}
