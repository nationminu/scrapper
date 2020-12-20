package com.souljam.crawler.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import lombok.Data;

/**
 * rockPM Configuration
 * @author wonsunlee
 * @since Nov 11, 2019
 */
@Configuration
@PropertySources({
	@PropertySource(value="classpath:crawler.properties"),
	@PropertySource(value="file:${user.dir}/conf/crawler.properties", ignoreResourceNotFound=true),
	@PropertySource(value="file:${crawler.config}", ignoreResourceNotFound=true)
})
@Data
public class ReloadConfig {
	/**
	 * Scouter Web IP (Default: 127.0.0.1)
	 */
	@Value("${schedluer.job.cron:0 */1 * * * }")
	private String [] schedluerJob; 
}