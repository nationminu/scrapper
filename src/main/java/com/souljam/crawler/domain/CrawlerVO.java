package com.souljam.crawler.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
	private String date;
	//@Field(type = FieldType.Text)
	private String content;
	//@Field(type = FieldType.Text)
	private String link;

}
