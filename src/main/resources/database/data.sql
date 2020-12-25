set names utf8;

--CREATE TABLE `crawler` (
--  `id` varchar(36) NOT NULL,
--  `content` text DEFAULT NULL,  
--  `scrapeddate` varchar(25) DEFAULT NULL,
--  `date` varchar(25) DEFAULT NULL,
--  `description` varchar(255) DEFAULT NULL,
--  `link` varchar(255) DEFAULT NULL,
--  `title` text DEFAULT NULL,
--  PRIMARY KEY (`id`)
--) ENGINE=InnoDB CHARSET=utf8;

--CREATE TABLE `domains` (
--  `id` varchar(36) NOT NULL,
--  `domain_id` binary(16) DEFAULT NULL,
--  `datetime` datetime DEFAULT NULL,
--  `href` varchar(255) DEFAULT NULL,
--  `title` text DEFAULT NULL, 
--  PRIMARY KEY (`id`)
--) ENGINE=InnoDB CHARSET=utf8;

CREATE TABLE `sites` (
  `id` varchar(36) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARSET=utf8;

insert into sites values(UUID(),'오토데일리 뉴스','https://www.autodaily.co.kr/news/articleList.html?sc_section_code=S1N1&view_type=sm');
insert into sites values(UUID(),'오토데일리 자율주행','https://www.autodaily.co.kr/news/articleList.html?sc_section_code=S1N3&view_type=sm');
insert into sites values(UUID(),'오토데일리 모빌리티','https://www.autodaily.co.kr/news/articleList.html?sc_section_code=S1N4&view_type=sm');
insert into sites values(UUID(),'오토데일리 모바일','https://www.autodaily.co.kr/news/articleList.html?sc_section_code=S1N5&view_type=sm');
insert into sites values(UUID(),'오토데일리 AI','https://www.autodaily.co.kr/news/articleList.html?sc_section_code=S1N6&view_type=sm');
insert into sites values(UUID(),'오토데일리 친환경','https://www.autodaily.co.kr/news/articleList.html?sc_section_code=S1N7&view_type=sm');
insert into sites values(UUID(),'오토데일리 전자/IT','https://www.autodaily.co.kr/news/articleList.html?sc_section_code=S1N2&view_type=sm');
insert into sites values(UUID(),'Intelligent Transport news','https://www.intelligenttransport.com/transport-news/');
insert into sites values(UUID(),'Intelligent Transport articles ','https://www.intelligenttransport.com/transport-articles/');
insert into sites values(UUID(),'medium Next Mobility Lab','https://medium.com/nextmobility');
insert into sites values(UUID(),'medium Mobility Insightsz','https://medium.com/mobility-insights');
insert into sites values(UUID(),'이코노미스트 Mobilista','http://jmagazine.joins.com/economist/list/021301');
insert into sites values(UUID(),'New Atlas TRANSPORT','https://newatlas.com/transport/');