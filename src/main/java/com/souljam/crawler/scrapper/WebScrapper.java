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
				
				String title = null;
				Element description = null;
				Element published_time = null;
				Element link = null;
				Elements content = null;
				StringBuffer sb = new StringBuffer();

				if (site_url.startsWith("https://www.autodaily.co.kr")){
					title = doc.title(); // doc.selectFirst("meta[name=title]"); 
					link = doc.selectFirst("meta[property=og:url]"); 
					published_time = doc.selectFirst("meta[property=article:published_time]"); 
					description = doc.selectFirst("meta[property=og:description]");
					content = doc.select("div[id=article-view-content-div] > p"); 
					log.info(content.text()); 
				}
				
				
				cv.setTitle(title);
				cv.setLink(link.attr("content").toString());
				cv.setDescription(description.attr("content").toString());
				cv.setDate(published_time.attr("content").toString());
				cv.setContent(content.text()); 
				
				log.info(cv.toString());
				
				crawlerRepository.save(cv);
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
