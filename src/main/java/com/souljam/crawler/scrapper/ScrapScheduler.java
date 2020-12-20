package com.souljam.crawler.scrapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.souljam.crawler.domain.DomainVO;
import com.souljam.crawler.domain.SiteVO;
import com.souljam.crawler.repository.DomainRepository;
import com.souljam.crawler.repository.SiteRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScrapScheduler {

	@Autowired
	private SiteRepository siteRepository;
	
	@Autowired
	private DomainRepository domainRepository;

	@Scheduled(cron = "${schedluer.job.cron}")
	public void run() { 
		StopWatch crawlerWatch = new StopWatch("CrawlerThreads");
		crawlerWatch.start("crawlerTasks");
		
		List<SiteVO> sites = get_site();
		List<DomainVO> domains = get_domains(sites);
				
		crawlerWatch.stop(); 
		log.info("it takes {} seconds", crawlerWatch.getTotalTimeSeconds());
		sites.clear();
		domains.clear();
	}
	
	public List<SiteVO> get_site() {
		
		List<SiteVO> sites = null; 

		try {
			sites = siteRepository.findAll();   
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return sites;
	}
	
	public List<DomainVO> get_domains(List<SiteVO> sites) {
	
		Date date = new Date(); 
		List<DomainVO> domains = new ArrayList<DomainVO>();
		Document doc;

		try { 
		    for(SiteVO site : sites){  
				log.info("Connect to : {} , {}", site.getId(), site.getUrl());
				
				doc = Jsoup.connect(site.getUrl()).timeout(50000).get();  

				log.debug("Parsing of HTML Document ... A tags");
				Elements elements = doc.select("a");
				int record=0;
				for (Element element : elements) {
					DomainVO domain = new DomainVO();
					
					String link = element.attr("href").toString();
					String title = element.text().toLowerCase();
					String cls = element.attr("class").toString();
					
					if(link.startsWith(getDomainName(link))){
						
					}
					if(link.startsWith(getDomainName("/"))){
						
					}
					
					if (title.contains("광고") || title.contains("구독") || title.contains("검색")
						|| title.contains("register") || title.contains("cookie policy") || title.contains("privacy policy")
						|| title.matches("(.*)jtbc|footer|footer(.*)")) 
						continue;
					
					if (link.equals("") || link.equals("#") || link.equals("/") || link.equals("/rss/")
						|| link.startsWith("mailto") || link.startsWith("tel:") || cls.startsWith("#") || cls.startsWith("line-height-3-2x")
						|| link.startsWith("javascript:") || link.contains("bit.ly") || link.contains("naver.me")  || link.contains("goo.gl")
						|| link.contains("twitter") || link.contains("instagram") || link.contains("facebook")
						|| link.contains("linkedin") || link.contains("flipboard") || link.matches("medium.com/@")
						|| link.matches("(.*)jtbc|footer|footer(.*)")) 
						continue;
					
					if(link.startsWith("/") || link.startsWith("?")){
						link = getDomainName(site.getUrl()) + link;
					}
					
					if(!link.startsWith(getDomainName(site.getUrl()))) {
						continue;
					}
					
					domain.setDomainId(site.getId());
					domain.setTitle(title);
					domain.setHref(link);
					domain.setDatetime(date);
					
					record++;					
					domains.add(domain);
 					log.debug("{}. Gathering URL of HTML Document: {} , {}", record , title , link); 	
				}

				log.debug("Parsing finished total {} : {} , {}", record, site.getId(), site.getUrl() );
		    }
			// save cache data
		    //domainRepository.saveAll(domains);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return domains;
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
