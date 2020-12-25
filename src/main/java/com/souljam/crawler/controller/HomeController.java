package com.souljam.crawler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.souljam.crawler.domain.SiteVO;
import com.souljam.crawler.repository.SiteRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
public class HomeController {

	@Value("${spring.application.name}")
	private String appName;

	@Autowired
	private SiteRepository siteRepository;

	@RequestMapping(value = "/sites", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<Object> list() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Retry-After", "3600");

		List<SiteVO> sv = null;

		try {
			sv = siteRepository.findAll();
			log.info(sv.toString());
		} catch (Exception e) {
			log.error(e.toString());
		}

		return new ResponseEntity<>(sv, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/sites/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<Object> get(@PathVariable String id) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Retry-After", "3600");
		Optional<SiteVO> sv = null;
		try {
			sv = siteRepository.findById(id);
			log.info(sv.toString());
		} catch (Exception e) {
			log.error(e.toString());
		}

		return new ResponseEntity<>(sv, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/sites", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public ResponseEntity<Map<Object, Object>> insert(@RequestBody SiteVO sv) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Retry-After", "3600");

		Map<Object, Object> map = new HashMap<>();

		try {
			if (sv.getId() == null)
				sv.setId(UUID.randomUUID().toString());
			siteRepository.save(sv);
			map.put("result", "OK");
		} catch (Exception e) {
			log.error(e.toString());
			map.put("result", "NOK");
			return new ResponseEntity<>(map, headers, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(map, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/sites/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<Map<Object, Object>> update(@PathVariable String id, @RequestBody SiteVO sv) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Retry-After", "3600");

		Map<Object, Object> map = new HashMap<>();

		try {
			siteRepository.save(sv);
			map.put("result", "OK");
		} catch (Exception e) {
			log.error(e.toString());
			map.put("result", "NOK");
			return new ResponseEntity<>(map, headers, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(map, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/sites/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public ResponseEntity<Map<Object, Object>> delete(@PathVariable String id) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Retry-After", "3600");

		Map<Object, Object> map = new HashMap<>();

		try {
			siteRepository.deleteById(id);
			map.put("result", "OK");
		} catch (Exception e) {
			log.error(e.toString());
			map.put("result", "NOK");
			return new ResponseEntity<>(map, headers, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(map, headers, HttpStatus.ACCEPTED);
	}
}
