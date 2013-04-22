<!-- 
 * 	XTractor : an algorithmic text extractor from web pages demo
 *  Website: http://xtractor.herokuapp.com
 *  Author: Saurav Mohapatra (mohaps@gmail.com) 
 *  
 *  Copyright (c) 2013, Saurav Mohapatra
 *  All rights reserved.
 -->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=${extracted.charset}">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="description" content="">
<meta name="viewport" content="width=device-width">
<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,900"
	rel="stylesheet" type="text/css" />

<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

<link rel="stylesheet" href="/css/normalize.css">
<link rel="stylesheet" href="/css/main.css">
<script src="/js/vendor/modernizr-2.6.2.min.js"></script>
<script>
	var pageUrl = document.location;
	var pageUrlEscaped = escape(pageUrl);
</script>
<title>[<c:out value="${extracted_title}" />] via XTractor
</title>
</head>
<body>
	<!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->
	<div align="center">
		<div class="mypage" align="left">
			<!-- Add your site or application content here -->
			<div class="header">
				<div align="center">
					<a href="/"><img src="/images/xtractor_logo_header.png"
						alt="XTractor - algorithmic text extraction from webpages"
						style="border: 0px;"></a>
				</div>
				<div class="topnav" align="center"
					style="margin-top: 2px; padding-top: 4px; padding-bottom: 4px; margin-bottom: 6px; border-bottom: 1px dashed #434343; border-top: 1px dashed #434343">

					<a href="/"> Home</a> | <a href="/#whatisit">What is it?</a> | <a
						href="/#howitworks">How does it work?</a> | <a href="/#credits">Who
						built this?</a> | <a
						href="https://news.ycombinator.com/item?id=5580719">Discuss on
						Hacker New</a> | <a href="http://tldrzr.herokuapp.com">You might
						also like...</a>
				</div>

			</div>
			<div class="content">
				<div class="content_section">
					<div class="extracted_title">
						<strong><a href="<c:out value="${extracted.link}"/>"><c:out
									value="${extracted_title}" /></a></strong>
					</div>
					<c:if test="${extracted.image != null}">
						<div align="center" class="extracted_image_container">
							<a href="<c:out value="${extracted.image}"/>"><img
								src="<c:out value="${extracted.image}"/>"
								class="extracted_image" style="border: 0px;" /></a>
						</div>
					</c:if>
					<div class="extracted_summary">
						<strong>Generated Summary</strong><em>(via <a
							href="http://tldrzr.herokuapp.com">TL;DRzr</a>)
						</em> <span style="font-size: 0.9em;">from [<a
							href="<c:out value="${extracted.link}"/>">original article</a>] | <a
							href="/xtractor?url=<c:out value="${encoded_page_url}"/>">permalink</a> 
							
							</span>
						<blockquote style="color: #FF0303; font-style: italic;">
							<c:out value="${extracted.summary}" />
						</blockquote>
					</div>
					<div class="extracted_text">
						<c:forEach var="extractedParagraph"
							items="${extracted_paragraphs}">
							<div class="extracted_paragraph">
								<c:out value="${extractedParagraph}" />
							</div>
						</c:forEach>
					</div>

					<div class="extracted_footer" align="right">
						<a href="<c:out value="${extracted.link}"/>">original source</a> |
						extracted in ${extracted.timeTakenMillis} milliseconds | <a
							href="/xtractor?url=<c:out value="${encoded_page_url}"/>">permalink</a>
					</div>
				</div>

				<div>
					<form name="xtractor" method="GET" action="/xtractor/"
						accept-charset="utf-8">
						<input type="text" style="width: 99%" name="url"
							value="<c:out value="${extracted.link}"/>"> <input
							type="submit" value="Extract Text" style="margin: 2px;">
					</form>
				</div>
				<div class="footer">
					<div>
						<a href="/"><strong>&lt;&lt;</strong> back to XTractor</a>
					</div>
					<a name="credits" />
					<p>
						<a href="http://xtractor.herokuapp.com">XTractor</a> is a weekend
						project/quick hack demo created by <a
							href="mailto:mohaps@gmail.com">Saurav Mohapatra</a>. If you like
						this, you might also like my other hack/weekend project <a
							href="http://tldrzr.herokuapp.com">TL;DRzr</a> - <em>an
							algorithmic summary generator</em>.
					</p>
					<p>
						Still very much wet paint/work in progress. Comments/Crits are
						appreciated. Contact author via <a href="mailto:mohaps@gmail.com">mohaps
							AT gmail DOT com</a> or my <a href="http://mohaps.com">blog</a> or my
						twitter feed : <a href="http://twitter.com/mohaps">@mohaps</a>.
					</p>
				</div>
			</div>
		</div>
	</div>

	<!--  script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="js/vendor/jquery-1.9.1.min.js"><\/script>')</script>
        <script src="js/plugins.js"></script>
        <script src="js/main.js"></script -->

	<!-- Google Analytics: change UA-XXXXX-X to be your site's ID. -->
	<script>
		<script>(function(i, s, o, g, r, a, m) {
			i['GoogleAnalyticsObject'] = r;
			i[r] = i[r] || function() {
				(i[r].q = i[r].q || []).push(arguments)
			}, i[r].l = 1 * new Date();
			a = s.createElement(o), m = s.getElementsByTagName(o)[0];
			a.async = 1;
			a.src = g;
			m.parentNode.insertBefore(a, m)
		})(window, document, 'script',
				'//www.google-analytics.com/analytics.js', 'ga');

		ga('create', 'UA-1728759-9', 'herokuapp.com');
		ga('send', 'pageview');
	</script>
</body>
</html>