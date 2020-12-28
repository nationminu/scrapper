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

import lombok.Data;

@Data
@Entity
@Table(name = "urls")
public class UrlVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(generator = "uuid2")
//	@GenericGenerator(name = "uuid2", strategy = "uuid2")
//	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
	private String id;

//	@GeneratedValue(generator = "uuid2")
//	@GenericGenerator(name = "uuid2", strategy = "uuid2")
//	@Column(columnDefinition = "BINARY(16)")
	private String domainId;
	private Date datetime;
	private String title;
	private String href;

}
