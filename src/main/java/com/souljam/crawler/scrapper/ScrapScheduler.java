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

		List<DomainVO> distinctElements = domains.stream().filter(distinctByKey(p -> p.getHref()))
				.collect(Collectors.toList());

		//log.info(distinctElements.toString());
		int record=0;
		for (DomainVO d : distinctElements){
			record++;
			log.info(record + " " + d.getDomainId() + " " + d.getHref());
		}
		
		crawlerWatch.stop();
		log.info("it takes {} seconds", crawlerWatch.getTotalTimeSeconds());
		sites.clear();
		domains.clear();
	}

	// Utility function
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
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
			for (SiteVO site : sites) {
				
				String site_url = site.getUrl();

				log.info("Connect to : {} , {}", site.getId(), site_url);
				
				doc = Jsoup.connect(site_url).timeout(50000).get();

				log.debug("Parsing of HTML Document ... A tags");
				Elements elements = doc.select("a");
				int record = 0;
				for (Element element : elements) {
					DomainVO domain = new DomainVO();
					
					
					String link = element.attr("href").toString();
					String title = element.text().toLowerCase();
					String cls = element.attr("class").toString();
					String style = element.attr("style").toString();

					if (link.startsWith("/") || link.startsWith("?")) {
						link = getDomainName(site_url) + link;
					}
					if (link.startsWith("./") || link.startsWith("?")) {
						link = getDomainName(site_url) + link.substring(-1);
					}
					
					if (site_url.startsWith("https://www.autodaily.co.kr") && link.startsWith("https://www.autodaily.co.kr/news/articleView.html")) {
						
					} else if (site_url.startsWith("https://www.intelligenttransport.com") && 
							(link.matches("https://www.intelligenttransport.com/transport-news/(.*)") || link.matches("https://www.intelligenttransport.com/transport-articles/(.*)"))) {
						
						if (link.equals("https://www.intelligenttransport.com/transport-news/") || link.equals("https://www.intelligenttransport.com/transport-articles/")) continue;
						
					} else if (site_url.startsWith("https://medium.com") && 
							(link.matches("https://medium.com/nextmobility/(.*)") || link.matches("https://medium.com/mobility-insights/(.*)"))) {

						if (link.equals("https://medium.com/nextmobility/") || link.equals("https://medium.com/mobility-insights/")) continue;

						if (link.equals("https://medium.com/nextmobility/about") || link.equals("https://medium.com/nextmobility/latest")
								 || link.equals("https://medium.com/nextmobility/archive")  || link.equals("https://medium.com/nextmobility/settings#aurora-beta")) continue;
						
					} else if (site_url.startsWith("http://jmagazine.joins.com/")) {
						
					} else if (site_url.startsWith("https://newatlas.com/transport/")) {

					} else {
						continue;
					}					

//					if (title.equals("전체기사") || title.equals("신문사소개") || title.equals("기사제보") || title.equals("불편신고") || title.equals("불편신고")
//						|| title.equals("개인정보취급방침") || title.equals("청소년보호정책") || title.equals("이메일무단수집거부")
//						|| title.contains("광고") || title.contains("구독") || title.contains("검색")
//						|| title.contains("register") || title.contains("cookie policy") || title.contains("privacy policy")
//						|| title.matches("(.*)jtbc|footer|footer(.*)")) 
//						continue;
//					
//					if (link.equals("") || link.equals("#") || link.equals("/") || link.equals("/rss/")
//						|| link.startsWith("mailto") || link.startsWith("tel:") || cls.startsWith("#") || cls.startsWith("line-height-3-2x")
//						|| link.startsWith("javascript:") || link.contains("bit.ly") || link.contains("naver.me")  || link.contains("goo.gl")
//						|| link.contains("twitter") || link.contains("instagram") || link.contains("facebook")
//						|| link.contains("linkedin") || link.contains("flipboard") || link.matches("medium.com/@")
//						|| link.matches("(.*)jtbc|footer|footer(.*)")) 
//						continue;


					if (!link.startsWith(getDomainName(site_url))) {
						continue;
					}
					if (link.startsWith(getDomainName(link))) {

					}
					if (link.startsWith(getDomainName("/"))) {

					}

					domain.setDomainId(site.getId());
					domain.setTitle(title);
					domain.setHref(link);
					domain.setDatetime(date);

					record++;
					domains.add(domain);
					log.info("{}. Gathering URL of HTML Document: {} , {}", record, title, link);
				}

				log.debug("Parsing finished total {} : {} , {}", record, site.getId(), site_url);
			}
			// save cache data
			// domainRepository.saveAll(domains);
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
