package com.mohaps.fetch;

import java.io.InputStream;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.nio.client.HttpAsyncClient;

import de.jetwick.snacktory.SHelper;

public class Fetcher {

	private HttpAsyncClient client;
	private boolean enforcePageOrFeed;
	public Fetcher() throws Exception {
		this(true);
	}
	public Fetcher(boolean enforcePageOrFeed) throws Exception {
		this.enforcePageOrFeed = enforcePageOrFeed;
		client = new DefaultHttpAsyncClient();
		client.start();
	}

	public void shutdown() {
		if (client != null) {
			synchronized (this) {
				if (client != null) {
					try {
						client.shutdown();
					} catch (Exception ex) {
					} finally {
						client = null;
					}
				}
			}
		}
	}

	public boolean isActive() {
		return client != null;
	}

	protected String cleanupUrl(String urlString) {
		String cleanUrl = SHelper.getUrlFromUglyFacebookRedirect(urlString);
		if(cleanUrl != null) {
			cleanUrl = SHelper.getUrlFromUglyGoogleRedirect(cleanUrl);
		} else {
			cleanUrl = SHelper.getUrlFromUglyGoogleRedirect(urlString);
		}
		return cleanUrl == null ? urlString : cleanUrl;
	}

	public FetchResult fetch(String urlString) throws Exception {
		return fetch(urlString, 0);
	}

	public FetchResult fetch(String urlString, long timeoutMillis)
			throws Exception {
		if(!isActive()){ throw new FetchException("Fetcher is not active!"); }
		String actualUrl = cleanupUrl(urlString);
		HttpGet get = new HttpGet(actualUrl);
		long startTime = System.currentTimeMillis();
		Future<HttpResponse> responseFuture = client.execute(get, null);
		if (responseFuture == null) {
			throw new FetchException("Failed to execute get request for ["
					+ urlString + "]");
		}
		HttpResponse response;
		if (timeoutMillis > 0) {
			try {
				response = responseFuture.get(timeoutMillis,
						TimeUnit.MILLISECONDS);
			} catch (TimeoutException ex) {
				responseFuture.cancel(true);
				throw new FetchException("fetch for [" + urlString
						+ "] timed out", ex);
			}
		} else {
			response = responseFuture.get();
		}

		long timeTakenMillis = System.currentTimeMillis() - startTime;

		if (response == null) {
			throw new FetchException("failed to get response while fetching [" + urlString + "]");
		} else {
			int httpStatusCode = response.getStatusLine().getStatusCode();
			long contentLength = getContentLength(response);
			String contentType = getContentType(response);//.split(";")[0];
			String charset = getCharset(contentType);
			contentType = contentType.split(";")[0];
			boolean htmlPage = false;
			boolean feedPage = false;
			if(contentType.equals("text/html") || contentType.equals("text/plain")) {
				htmlPage = true;
			} else {
				feedPage = contentType.endsWith("xml");
			}
			if(this.enforcePageOrFeed ) { 
				if(!htmlPage && !feedPage) {
					throw new FetchException("Fetched content from ["+urlString+"] has content type "+contentType+" which is neither an html or text page nor a valid xml feed");
				}
			}
			
			FetchResult.ContentFlavor flavor = FetchResult.ContentFlavor.CUSTOM;
			if(htmlPage) {
				flavor = FetchResult.ContentFlavor.PAGE;
			} else if(feedPage) {
				flavor = FetchResult.ContentFlavor.FEED;
			}
			byte[] content = getContent(response);
			if (contentLength <= 0 && content != null) {
				contentLength = content.length;
			}
			
			
			return new FetchResult(urlString, actualUrl, httpStatusCode,
					contentLength, contentType, flavor, charset, timeTakenMillis,
					content);
		}

	}

	public HeadResult fetchHead(String urlString, long timeoutMillis) throws Exception {
		if(!isActive()){ throw new FetchException("Fetcher is not active!"); }
		String actualUrl = cleanupUrl(urlString);
		HttpHead get = new HttpHead(actualUrl);
		Future<HttpResponse> responseFuture = client.execute(get, null);
		if (responseFuture == null) {
			throw new FetchException("Failed to execute head request for ["
					+ urlString + "]");
		}
		HttpResponse response;
		if (timeoutMillis > 0) {
			try {
				response = responseFuture.get(timeoutMillis,
						TimeUnit.MILLISECONDS);
			} catch (TimeoutException ex) {
				responseFuture.cancel(true);
				throw new FetchException("fetch head for [" + urlString
						+ "] timed out", ex);
			}
		} else {
			response = responseFuture.get();
		}
		
		if (response == null) {
			throw new FetchException("failed to get response while fetching head for [" + urlString + "]");
		} else {
			int httpStatusCode = response.getStatusLine().getStatusCode();
			if(httpStatusCode == 200) {
				long contentLength = getContentLength(response);
				String contentType = getContentType(response);
				return HeadResult.NewSuccess(urlString, httpStatusCode, contentType, contentLength);
			} else {
				return HeadResult.NewFailed(urlString, httpStatusCode);
			}
		}
	}
	
	protected long getContentLength(HttpResponse response) {
		HttpEntity entity = response.getEntity();
		if (entity == null) {
			return -1;
		} else {
			return entity.getContentLength();
		}
	}

	protected String getContentType(HttpResponse response) {
		HttpEntity entity = response.getEntity();
		if (entity == null) {
			return Constants.DEFAULT_CONTENT_TYPE;
		} else {
			Header header = entity.getContentType();
			if (header != null) {
				return header.getValue();
			} else {
				return Constants.DEFAULT_CONTENT_TYPE;
			}
		}
	}

	protected String getCharset(String contentType) {
		if (contentType != null && contentType.length() > 0) {
			String[] tokens = contentType.split(";");
			for (int i = 1; i < tokens.length; i++) {
				String token = tokens[i];
				String[] nv = token.split("=");
				if (nv[0].trim().equalsIgnoreCase("charset")) {
					return nv[1].toUpperCase();
				}
			}
		}
		return Constants.DEFAULT_CHARSET;
	}

	protected byte[] getContent(HttpResponse response) throws Exception {
		InputStream in = response.getEntity().getContent();
		byte[] buf = new byte[in.available()];
		int offset = 0;
		while(offset < buf.length) {
			int bread = in.read(buf, offset, buf.length - offset);
			if(bread > 0) {
				offset += bread;
			} else {
				break;
			}
		}
		if(offset < buf.length) {
			throw new FetchException("Failed to get "+buf.length+" bytes of content, could retrieve only "+offset+" bytes!");
		}
		return buf;
	}

}
