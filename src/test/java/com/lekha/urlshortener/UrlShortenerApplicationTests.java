package com.lekha.urlshortener;

import com.lekha.urlshortener.service.UrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UrlShortenerApplicationTests {

	@Autowired
	private UrlService urlService;

	@Test
	void contextLoads() {
	}

}
