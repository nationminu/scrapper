package com.souljam.crawler.domain;

import java.io.Serializable;  
import javax.persistence.Id;  
import org.springframework.data.elasticsearch.annotations.Document; 

import lombok.Data;

@Data
@Document(indexName = "crawler")
public class CrawlerVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Id id;
	//@Field(type = FieldType.Text)
	private String title;
	//@Field(type = FieldType.Text)
	private String description;
	//@Field(type = FieldType.Date)
	private String scrapeddate;
	//@Field(type = FieldType.Date)
	private String date;
	//@Field(type = FieldType.Text)
	private String content;
	//@Field(type = FieldType.Text)
	private String link;

}
