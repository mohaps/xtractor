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

