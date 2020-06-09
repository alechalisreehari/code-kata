package com.sreehari.content.rest;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class ContentHelper {

	private final Logger _log = Logger.getLogger(ContentHelper.class.getName());

	/**
	 * Method to scroll to the given URL home page.
	 * @param url
	 * @param updateContent
	 * @return Optional<String> returns HTML or empty 
	 */
	public Optional<String> crawl(String url, boolean updateContent) {
		try {
			Document doc = getDocument(url);
			return Optional.ofNullable(updateContent ? modify(doc) : doc.html());
		} catch (IOException e) {
			_log.log(Level.SEVERE,
					String.format("Crawler failed to process url - '%s', with an exception - '%s'", url, e));
		}
		return Optional.empty();
	}

	/**
	 * Method which parses HTML with given CSS selectors and randomize the identified names
	 * @param doc
	 * @return String modified names HTML
	 */
	private String modify(Document doc) {
		Elements results = getDescription(doc);
		Elements names = getNames(results);
		modifyNames(names);
		return doc.html();
	}

	/**
	 * Randomly updates the names.
	 * @param names List of names identified in the given HTML.
	 */
	private void modifyNames(Elements names) {
		Random rand = new Random();
		names.stream().forEach(name -> name.text(names.get(rand.nextInt(names.size())).text()));
	}

	/**
	 * A CSS selector to identify the names from the last paragraph element.
	 * @param results
	 * @return
	 */
	private Elements getNames(Elements results) {
		return results.select("p:last-child>strong");
	}

	/**
	 * A CSS selector that identifies the description DIV element.
	 * @param doc
	 * @return
	 */
	private Elements getDescription(Document doc) {
		return doc.select("div.et_pb_blurb_description:last-child");
	}

	/**
	 * Method connects to the given url and parses the response.
	 * @param url URL to crawl
	 * @return Document a tree structured DOM
	 * @throws IOException
	 */
	private Document getDocument(String url) throws IOException {
		return Jsoup.connect(url).get();
	}
}
