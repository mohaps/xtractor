package com.mohaps.fetch;

public class FetchResult {
	
	private String originalUrl;
	private String actualUrl;
	private int httpStatusCode;
	private long contentLength;
	private String contentType;
	private String charset;
	private long timeTakenMillis;
	private byte[] content;
	private ContentFlavor flavor;
	public enum ContentFlavor {
		PAGE,
		FEED,
		CUSTOM
	}
	
	public FetchResult(String originalUrl, String actualUrl, int httpStatusCode, long contentLength, String contentType, ContentFlavor flavor, String charset, long timeTakenMillis, byte[] content) {
		this.originalUrl = originalUrl;
		this.actualUrl = actualUrl;
		this.httpStatusCode = httpStatusCode;
		this.contentLength = contentLength;
		this.contentType = contentType;
		this.charset = charset;
		this.flavor = flavor;
		this.timeTakenMillis = timeTakenMillis;
		this.content = content;
	}
	public boolean isFeed() {
		return this.flavor.equals(ContentFlavor.FEED);
	}
	public boolean isPage() {
		return this.flavor.equals(ContentFlavor.PAGE);
	}
	public String getOriginalUrl() {
		return originalUrl;
	}
	public String getActualUrl() {
		return actualUrl;
	}
	public int getHttpStatusCode() {
		return httpStatusCode;
	}
	public long getContentLength() {
		return contentLength;
	}
	public String getContentType() {
		return contentType;
	}
	public String getCharset() {
		return charset;
	}
	public long getTimeTakenMillis() {
		return timeTakenMillis;
	}
	public byte[] getContent() {
		return content;
	}
	@Override
	public String toString() {
		return "FetchResult \n[\n originalUrl=" + originalUrl + ",\n actualUrl="
				+ actualUrl + ",\n httpStatusCode=" + httpStatusCode
				+ ",\n contentLength=" + contentLength + ",\n contentType="
				+ contentType + ",\n charset=" + charset+ ",\n flavor=" + flavor + ",\n timeTakenMillis="
				+ timeTakenMillis + "\n]";
	}
	
	
}
