package com.souljam.crawler.scrapper;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.souljam.crawler.domain.UrlVO;
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
		//crawlerWatch.start("totalTasks");
		try {
			crawlerWatch.start("templateTasks");
			templateScaper template = new templateScaper(siteRepository);
			List<UrlVO> domains = template.generate();
			crawlerWatch.stop();

			crawlerWatch.start("scraperTasks");
			WebScraper scraper = new WebScraper(crawlerRepository);
			scraper.save(domains);
			crawlerWatch.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//crawlerWatch.stop();
		log.info("it takes {}", crawlerWatch.prettyPrint());
	}

}
