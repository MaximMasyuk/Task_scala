name := "CSVParserAndLog4g"

version := "0.1"

scalaVersion := "2.13.1"
libraryDependencies ++= Seq(
  "org.apache.logging.log4j" % "log4j-api-scala_2.12" % "11.0",
  "org.apache.logging.log4j" % "log4j-api" % "2.11.0",
  "org.apache.logging.log4j" % "log4j-core" % "2.11.0" % Runtime
)
libraryDependencies += "com.univocity" % "univocity-parsers" % "2.8.4"

libraryDependencies += "commons-io" % "commons-io" % "2.6"
