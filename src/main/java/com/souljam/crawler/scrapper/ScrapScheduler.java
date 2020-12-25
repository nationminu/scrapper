package com.souljam.crawler.scrapper;
 
import java.util.List; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
 
import com.souljam.crawler.domain.DomainVO; 
import com.souljam.crawler.repository.CrawlerRepository; 
import com.souljam.crawler.repository.SiteRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScrapScheduler {

	@Autowired
	private SiteRepository siteRepository;
 
	@Autowired
	private CrawlerRepository crawlerRepository;

	@Scheduled(cron = "${schedluer.job.cron}")
	public void run() {
		StopWatch crawlerWatch = new StopWatch("CrawlerThreads");
		crawlerWatch.start("crawlerTasks");
		try {
			WebCrawler wc = new WebCrawler(siteRepository);
			List<DomainVO> domains = wc.scrap();

			WebScrapper ws = new WebScrapper(crawlerRepository);
			ws.save(domains);
			
			// jdbcTemplate.execute("SELECT 1 FROM sites");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		crawlerWatch.stop();
		log.info("it takes {} seconds", crawlerWatch.getTotalTimeSeconds());

	}

}
