package com.sreehari.content.rest;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crawl")
public class ContentCrawler {
	
	@Autowired
	private ContentHelper contentHelper;
	
	/**
	 * Service method to crawl to the given url.
	 * @param _url
	 * @return
	 */
	@RequestMapping("/content")
	public String getContent(@RequestParam(name="url", required =false) String _url) {
		return contentHelper.crawl(_url, Boolean.FALSE).orElseThrow(unhandledException());
	}

	

	/**
	 * Service method to crawl and update the names identified.
	 * @param _url
	 * @return
	 */
	@RequestMapping("/updatecontent")
	public String modifiedContent(@RequestParam(name="url", required =false) String _url) {
		return contentHelper.crawl(_url, Boolean.TRUE).orElseThrow(unhandledException());
	}
	
	private Supplier<? extends UnknownError> unhandledException() {
		return ()->new UnknownError("Unable to process your request...");
	}
}
