package com.souljam.crawler.scrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.souljam.crawler.domain.CrawlerVO;
import com.souljam.crawler.domain.DomainVO;
import com.souljam.crawler.repository.CrawlerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WebScrapper {
	
	private CrawlerRepository crawlerRepository;

	public WebScrapper(CrawlerRepository crawlerRepository) {
		this.crawlerRepository =  crawlerRepository;
	}

	public void save(DomainVO dv) throws Exception {
		
	}

	public void save(List<DomainVO> domains) {
		// TODO Auto-generated method stub
		 
		List<CrawlerVO> crawler = new ArrayList<CrawlerVO>();
		Document doc;
		
		int record = 0;
		for (DomainVO d : domains) {
			
			String site_url = d.getHref();
			CrawlerVO cv  = new CrawlerVO();
			try {
				doc = Jsoup.connect(site_url).timeout(50000).get();
				Element title = doc.selectFirst("title");
				Element description = doc.selectFirst("meta[property=og:description]");
				Element published_time = doc.selectFirst("meta[property=article:published_time]"); 
				
				cv.setTitle(title.attr("text").toString());
				cv.setContent(description.attr("content").toString());
				cv.setDate(published_time.attr("content").toString());
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//CrawlerVO cv = new CrawlerVO(); 
			log.info(record + " " + d.getDomainId() + " " + d.getHref());
			record++;
		}

	}
}
