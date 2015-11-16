name := "xtractor"

version := "0.0.1"

organization := "com.mohaps"

scalaVersion := "2.10.4"

scalacOptions := Seq(
  "-encoding", "UTF-8", "-Xlint", "-deprecation", "-unchecked", "-feature"
)

// ignores javadoc errors
javacOptions in doc ++= Seq("-Xdoclint:none", "-source", "1.6")

libraryDependencies ++= Seq(
  "org.apache.httpcomponents" % "httpasyncclient" % "4.0-beta3",
  "org.apache.opennlp" % "opennlp-tools" % "1.5.3",
  "org.eclipse.jetty" % "jetty-servlet" % "7.6.0.v20120127",
  "org.eclipse.jetty" % "jetty-webapp" % "7.6.0.v20120127",
  "org.jsoup" % "jsoup" % "1.7.2",
  "org.mortbay.jetty" % "jsp-2.1-glassfish" % "2.1.v20100127",
  "com.rosaloves" % "bitlyj" % "2.0.0",
  "javax.servlet" % "servlet-api" % "2.5"
)
