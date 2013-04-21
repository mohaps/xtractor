package com.mohaps.fetch;

public class HeadResult {
	private boolean success;
	private String url;
	private String contentType;
	private long contentLength;
	private int statusCode;
	private HeadResult() {
		
	}
	public static HeadResult NewSuccess(String url, int statusCode, String contentType, long contentLength) {
		HeadResult result = new HeadResult();
		result.url = url;
		result.success = true;
		result.contentType = contentType;
		result.contentLength = contentLength;
		result.statusCode = statusCode;
		return result;
	}
	public static HeadResult NewFailed(String url, int statusCode) {
		HeadResult result = new HeadResult();
		result.url = url;
		result.success = false;
		result.statusCode = statusCode;
		return result;
	}
	@Override
	public String toString() {
		return "HeadResult [success=" + success + ", url=" + url
				+ ", contentType=" + contentType + ", contentLength="
				+ contentLength + ", statusCode=" + statusCode + "]";
	}
	public boolean isSuccess() {
		return success;
	}
	public String getUrl() {
		return url;
	}
	public String getContentType() {
		return contentType;
	}
	public long getContentLength() {
		return contentLength;
	}
	public int getStatusCode() {
		return statusCode;
	}
}
