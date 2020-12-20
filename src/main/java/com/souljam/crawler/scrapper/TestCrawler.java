package com.souljam.crawler.scrapper;
 
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired; 
 
import com.souljam.crawler.domain.SiteVO;
import com.souljam.crawler.repository.SiteRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestCrawler {

	@Autowired
	private static SiteRepository siteRepository;
 
 
	public static void main(String[] args) throws URISyntaxException {

		log.info("- crawler start");
		
		List<SiteVO> sv = null;

		try {
			sv = siteRepository.findAll(); 
			

		    for(SiteVO site : sv){
		           System.out.println(site.getTitle());
		    }
			
		} catch (Exception e) {
			log.error(e.toString());
		}

		log.info("- crawler done ");
	}

	public static String getDomainName(String url) throws URISyntaxException {
		URI uri = new URI(url);
		String domain = uri.getScheme() + "://" + uri.getHost();
		return domain.startsWith("www.") ? domain.substring(4) : domain;
	}

	public static String getPath(String url) throws URISyntaxException {
		URI uri = new URI(url);
		String path = uri.getPath();
		return path;
	}
}
