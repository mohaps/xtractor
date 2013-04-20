package com.mohaps.xtractor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExtractorResult {
	private long timeTakenMillis;
	private String title;
	private String text;
	private String link;
	private String image;
	private String video;
	private String charset;
	private List<String> keywords;

	public ExtractorResult(String title, String text, String link,
			String image, String video, String charset, long timeTakenMillis,
			Collection<String> keywords) {
		this.timeTakenMillis = timeTakenMillis;
		this.text = text;
		this.link = link;
		this.title = title;
		this.image = image;
		this.charset = charset;
		this.keywords = new ArrayList<String>(keywords);
		this.video = video;
	}

	public String getVideo() {
		return video;
	}
	public long getTimeTakenMillis() {
		return timeTakenMillis;
	}

	public String getTitle() {
		return title;
	}

	public String getText() {
		return text;
	}

	public String getLink() {
		return link;
	}

	public String getCharset() {
		return charset;
	}

	public String getImage() {
		return image;
	}

	public Collection<String> getKeywords() {
		return this.keywords;
	}

	@Override
	public String toString() {
		return "ExtractorResult [\n"
				+" timeTakenMillis=" + timeTakenMillis
				+ ",\n title=" + title 
				+ ",\n link=" + link 
				+ ",\n image=" + image 
				+ ",\n video=" +video
				+ "]";
	}

}
