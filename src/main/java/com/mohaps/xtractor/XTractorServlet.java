package com.mohaps.xtractor;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mohaps.fetch.FetchResult;
import com.mohaps.fetch.Fetcher;

import de.jetwick.snacktory.SHelper;

public class XTractorServlet extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(XTractorServlet.class.getName());
	private static Fetcher fetcher;
	static {
		try {
			fetcher = new Fetcher();
		} catch (Exception ex) {
			LOG.log(Level.SEVERE, "Failed to initialize fetcher", ex);
		}
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if (pathInfo == null || pathInfo.length() == 0 || pathInfo.equals("/")) {
			String pageUrl = req.getParameter("url");
			if (pageUrl != null && pageUrl.length() > 0) {
				extractAndShowPage(pageUrl, req, resp);
			} else {
				showHomePage(req, resp);
			}
		} else {
			resp.sendError(404, "Path " + pathInfo + " not found");
		}
    }
	
	protected void extractAndShowPage(String pageUrl, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		
		try {
			ExtractorResult result = fetchAndExtractFromUrl(pageUrl);
			String text2 = SHelper.replaceSmartQuotes(result.getText());
			String[] paras = text2.split("\\n");
			String fixed_title = SHelper.replaceSmartQuotes(result.getTitle());
			req.setAttribute("extracted_title", fixed_title);
			req.setAttribute("extracted", result);
			req.setAttribute("extracted_paragraphs", paras);
			req.getRequestDispatcher("/xtractor.jsp").forward(req, resp);
		} catch (Exception ex) {
			resp.sendError(500, "Failed to extract from url "+pageUrl+". error = "+ex.getLocalizedMessage());
			ex.printStackTrace();
		}
		
	}
	protected void showHomePage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		resp.sendRedirect("/");
	}

	private ExtractorResult fetchAndExtractFromUrl(String urlString)
			throws Exception {
		FetchResult fResult;
		fResult = fetcher.fetch(urlString);
		if (fResult != null && fResult.isPage()) {
			Extractor extractor = new Extractor(fetcher);
			ExtractorResult eResult = extractor.extract(fResult.getContent(),
					fResult.getCharset(), fResult.getActualUrl());

			return eResult;
		} else if (fResult.isFeed()) {
			throw new Exception("Only pages supported for now. Feed parsing not implemented yet!");
		} else {
			throw new Exception("Extraction failed for " + fResult);
		}
	}
    
}
