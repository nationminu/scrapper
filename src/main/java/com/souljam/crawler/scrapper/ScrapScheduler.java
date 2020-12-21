package com.souljam.crawler.scrapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.souljam.crawler.domain.CrawlerVO;
import com.souljam.crawler.domain.DomainVO;
import com.souljam.crawler.domain.SiteVO;
import com.souljam.crawler.repository.CrawlerRepository;
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
