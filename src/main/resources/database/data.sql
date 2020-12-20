 CREATE TABLE `crawler` (
  `id` binary(16) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARSET=utf8;

 CREATE TABLE `domains` (
  `id` binary(16) NOT NULL,
  `domain_id` binary(16) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  `href` varchar(255) DEFAULT NULL,
  `title` text DEFAULT NULL, 
  PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARSET=utf8;

CREATE TABLE `sites` (
  `id` binary(16) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARSET=utf8;
 
insert into sites values(unhex(replace(uuid(), '-', '')),'autodaily','https://www.autodaily.co.kr/news/articleList.html?sc_section_code=S1N1&view_type=sm');
insert into sites values(unhex(replace(uuid(), '-', '')),'autodaily','https://www.autodaily.co.kr/news/articleList.html?sc_section_code=S1N3&view_type=sm');
insert into sites values(unhex(replace(uuid(), '-', '')),'autodaily','https://www.autodaily.co.kr/news/articleList.html?sc_section_code=S1N4&view_type=sm');
insert into sites values(unhex(replace(uuid(), '-', '')),'autodaily','https://www.autodaily.co.kr/news/articleList.html?sc_section_code=S1N5&view_type=sm');
insert into sites values(unhex(replace(uuid(), '-', '')),'autodaily','https://www.autodaily.co.kr/news/articleList.html?sc_section_code=S1N6&view_type=sm');
insert into sites values(unhex(replace(uuid(), '-', '')),'autodaily','https://www.autodaily.co.kr/news/articleList.html?sc_section_code=S1N7&view_type=sm');
insert into sites values(unhex(replace(uuid(), '-', '')),'autodaily','https://www.autodaily.co.kr/news/articleList.html?sc_section_code=S1N2&view_type=sm');
insert into sites values(unhex(replace(uuid(), '-', '')),'intelligenttransport','https://www.intelligenttransport.com/transport-news/');
insert into sites values(unhex(replace(uuid(), '-', '')),'intelligenttransport','https://www.intelligenttransport.com/transport-articles/');
insert into sites values(unhex(replace(uuid(), '-', '')),'medium','https://medium.com/nextmobility');
insert into sites values(unhex(replace(uuid(), '-', '')),'medium','https://medium.com/mobility-insights');
insert into sites values(unhex(replace(uuid(), '-', '')),'joins','http://jmagazine.joins.com/economist/list/021301');
insert into sites values(unhex(replace(uuid(), '-', '')),'newatlas','https://newatlas.com/transport/');
