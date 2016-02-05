# XTractor - heuristics based webpage text extraction demo
Demo: http://xtractor.herokuapp.com

Sample URL: Fox News review of the movie Oblivion : http://xtractor.herokuapp.com/xtractor/?url=http%3A%2F%2Fwww.foxnews.com%2Fentertainment%2F2013%2F04%2F18%2Freview-tom-cruise-tones-it-down-in-visually-stunning-oblivion%2F


Xtractor tries to guess the main body of the article and grab one significant image from the page. Then it strips off all formatting and presents the article with just the image as header, a short summary and paragraph preserved simple readable text.

I had written this close to 3 years back (https://news.ycombinator.com/item?id=5580719)

It uses snacktory for dom manipulation and tldrzr (http://github.com/mohaps/tldrzr) for summarization.


I was never happy with the code quality and always intended to get back to it. Received an email from someone who wished to use the code in an open source project. Opensourcing this under the BSD license. Feel free to hack on it and make it better. :)

While reading the code, please keep in mind that it was written within a couple of hours of hackathon style coding :) with the sole goal of getting something working end to end. If you have questions... well, it's been so long, I mightn't even remember why I did somethings the way I did. :) But feel free to drop me a line at mohaps AT gmail DOT com

Have fun! Keep hacking.

## Running the application locally

First build with:

    $mvn clean install

Then run it with:

    $java -cp target/classes:target/dependency/* com.example.Main

## Library usage example

### With Fetcher

    Fetcher fetcher = new Fetcher();
    Extractor extractor = new Extractor(fetcher);
    ISummarizer summarizer = Factory.getSummarizer();

    int summarySentenceNb = 1;
    long timeout = 1000; // 1s
    String url = "http://www.bbc.com/news/science-environment-34510869";
    FetchResult fResult = fetcher.fetch(url, timeout);
    ExtractorResult eResult = extractor.extract(fResult.getContent(), fResult.getCharset(), fResult.getActualUrl());
    String summary = summarizer.summarize(eResult.getText(), summarySentenceNb);

    System.out.print("title:  \t"+ SHelper.replaceSmartQuotes(eResult.getTitle()) + "\n");
    System.out.print("summary:\t"+ SHelper.replaceSmartQuotes(summary) + "\n");
    System.out.print("image:  \t"+ eResult.getImage() + "\n");
    System.out.print("video:  \t"+ eResult.getVideo() + "\n");
    System.out.print("body:   \t"+ SHelper.replaceSmartQuotes(eResult.getText()) + "\n");

    fetcher.shutdown(); // Have to be shutdown to finish the process

### Without Fetcher

    Extractor extractor = new Extractor();
    ISummarizer summarizer = Factory.getSummarizer();

    int summarySentenceNb = 1;
    String charset = "UTF-8"
    String url = "http://www.random.com";
    String html = "<html><body><div>Hello World</div></body></html>"
    ExtractorResult eResult = extractor.extract(content, charset, url);
    String summary = summarizer.summarize(eResult.getText(), summarySentenceNb);

    System.out.print("title:  \t"+ SHelper.replaceSmartQuotes(eResult.getTitle()) + "\n");
    System.out.print("summary:\t"+ SHelper.replaceSmartQuotes(summary) + "\n");
    System.out.print("body:   \t"+ SHelper.replaceSmartQuotes(eResult.getText()) + "\n");
