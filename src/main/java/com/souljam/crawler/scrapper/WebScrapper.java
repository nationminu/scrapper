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
 
	public void save(List<DomainVO> domains) throws IOException {
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
				String description = null;
				String published_time = null;
				String link = null;
				String content = null; 
				
				// autodaily
				if (site_url.startsWith("https://www.autodaily.co.kr")){
					title = doc.title();
					link = doc.selectFirst("meta[property=og:url]").attr("content").toString(); 
					published_time = doc.selectFirst("meta[property=article:published_time]").attr("content").toString(); 
					description = doc.selectFirst("meta[property=og:description]").attr("content").toString();
					content = doc.select("div[id=article-view-content-div] > p").text(); 
					  
					cv.setTitle(title);
					cv.setLink(link);
					cv.setDescription(description);
					cv.setDate(published_time);
					cv.setContent(content); 					
				}

				// intelligenttransport
				if (site_url.startsWith("https://www.intelligenttransport.com")){
					title = doc.title();
					link = doc.selectFirst("meta[property=og:url]").attr("content").toString();  
					description = doc.selectFirst("meta[property=og:description]").attr("content").toString();
					content = doc.select("div[id=fullLeft] > main > article.single > p").text(); 
					
					String[] p_date = doc.selectFirst("div[id=meta2] > p").text().split(" ");
					published_time = String.format("%04d-%02d-%02dT00:00:00+09:00",Integer.parseInt(p_date[3]),Integer.parseInt(convertchartonumonths(p_date[2])),Integer.parseInt(p_date[1]));
							//p_date[3]+ "-" + convertchartonumonths(p_date[2]) + "-" + String.format("%02d",Integer.parseInt(p_date[1]))+"T00:00:00+09:00";
					
					cv.setTitle(title);
					cv.setLink(link);
					cv.setDescription(description);
					cv.setDate(published_time);
					cv.setContent(content); 
				}

				// medium
				if (site_url.startsWith("https://medium.com")){					
					title = doc.title();
					link = doc.selectFirst("meta[property=og:url]").attr("content").toString();   
					published_time = doc.selectFirst("meta[property=article:published_time]").attr("content").toString(); 
					description = doc.selectFirst("meta[property=og:description]").attr("content").toString();
					content = doc.select("article").text();
					
					cv.setTitle(title);
					cv.setLink(link);
					cv.setDescription(description);
					cv.setDate(published_time);
					cv.setContent(content);   
				}

				// jmagazine
				if (site_url.startsWith("http://jmagazine.joins.com")){					
					title = doc.select("div.tit_area > h3").text();
					link = doc.selectFirst("meta[property=og:url]").attr("content").toString();   
					published_time = doc.selectFirst("meta[property=article:published_time]").attr("content").toString(); 
					description = doc.select("div.con_area > span").isEmpty() == true ? "":doc.select("div.con_area > span").text();
					content = doc.select("div.con_area").text(); 
					
					cv.setTitle(title);
					cv.setLink(link);
					cv.setDescription(description);
					cv.setDate(published_time);
					cv.setContent(content);   
				}

				// newatlas 
				if (site_url.startsWith("https://newatlas.com")){					
					title = doc.title();
					link = doc.selectFirst("meta[property=og:url]").attr("content").toString();   
					published_time = doc.selectFirst("meta[property=article:modified_time]").attr("content").toString(); 
					description = doc.selectFirst("meta[name=description]").attr("content").toString(); 
					content = doc.select("div.ArticlePage-articleBody").text(); 
					
					cv.setTitle(title);
					cv.setLink(link);
					cv.setDescription(description);
					cv.setDate(published_time);
					cv.setContent(content);   
				}
				
				crawler.add(cv);
				//crawlerRepository.save(cv);
				log.debug(cv.toString()); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//CrawlerVO cv = new CrawlerVO(); 
			log.info(record + " " + d.getDomainId() + " " + d.getHref());
			record++;
		} 
		crawlerRepository.save(crawler);
	}
	
	
	public static String convertchartonumonths(String m){
		String month = null;
        
        switch(m){
        	case "January":
        		month="1";
        	case "February":
        		month="2";
        	case "March":
        		month="3";
        	case "April":
        		month="4";
        	case "May":
        		month="5";
        	case "June":
        		month="6";
        	case "July":
        		month="7";
        	case "August":
        		month="8";
        	case "September":
        		month="9";
        	case "October":
        		month="10";
        	case "November":
        		month="11";
        	case "December":
        		month="12";
        		break;
        	default:
        		break;
        }
        
        return month;
    }

}
