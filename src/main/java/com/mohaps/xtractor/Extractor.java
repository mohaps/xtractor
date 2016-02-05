package com.mohaps.xtractor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mohaps.fetch.Fetcher;

import de.jetwick.snacktory.ArticleTextExtractor;
import de.jetwick.snacktory.ExtractedContent;
import de.jetwick.snacktory.OutputFormatter;

public class Extractor {

	private OutputFormatter formatter = new OutputFormatter();
	private Fetcher fetcher;
	public Extractor() {
		this.fetcher = null;
	}
	public Extractor(Fetcher fetcher) {
		this.fetcher = fetcher;
	}
	public ExtractorResult extract(byte[] content, String charset,
			String baseUrl) throws Exception {
		return extract(new ByteArrayInputStream(content), charset, baseUrl);
	}

	public ExtractorResult extract(InputStream input, String charset,
			String baseUrl) throws Exception {
		long startTime = System.currentTimeMillis();
		ArticleTextExtractor extractor = new ArticleTextExtractor(fetcher);
		Document doc = Jsoup.parse(input, charset, baseUrl);
		ExtractedContent extracted = new ExtractedContent();
		extracted.setOriginalUrl(baseUrl);
		extractor.extractContent(extracted, doc, formatter);
		// System.out.println(">> Extracted Image : "+extracted.getImageUrl());
		long elapsed = System.currentTimeMillis() - startTime;
		ExtractorResult result = new ExtractorResult(extracted.getTitle(),
				extracted.getText(), 
				baseUrl, 
				extracted.getImageUrl(), 
				extracted.getVideoUrl(),
				charset,
				elapsed, 
				extracted.getKeywords());
		return result;
	}

}
