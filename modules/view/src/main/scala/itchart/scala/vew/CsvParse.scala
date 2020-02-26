package itchart.scala.vew

import java.io.{BufferedWriter, File, FileWriter}
import java.text.SimpleDateFormat
import java.util.Date

import com.univocity.parsers.common.processor.RowListProcessor
import com.univocity.parsers.csv.{CsvParser, CsvParserSettings}
import itchart.scala.core.ComputingMetrics.{countUseBike, mailAndFeMail, month, time}
import itchart.scala.core.ModelClasses.{BikeTravelData, BikeTravelTime, Config}
import org.apache.logging.log4j.LogManager
import scopt.OParser

import scala.collection.mutable.ArrayBuffer


object CsvParse extends App {
  val builder = OParser.builder[Config]
  val parser1 = {
    import builder._
    OParser.sequence(
      programName("scopt"),
      head("scopt", "4.x"),
      // option -f, --foo
      opt[String]('f', "foo")
        .action((x, c) => c.copy(foo = x))
        .text("foo is an integer property"),
      // more options here...
    )
  }
  // OParser.parse returns Option[Config]
  OParser.parse(parser1, args, Config()) match {
    case Some(config) =>
    // do something
    case _ =>
    // arguments are bad, error message will have been displayed
  }


  val format: SimpleDateFormat = new SimpleDateFormat("\"MM/dd/yyyy hh:mm:ss\"")
  private val logger = LogManager.getLogger("CSVProceeding")

  def parseCsv(fileName: String): Seq[BikeTravelData] = {
    logger.info("start parse csv")
    val parserSettings = new CsvParserSettings()
    parserSettings.setLineSeparatorDetectionEnabled(true)
    val rowProcessor = new RowListProcessor
    parserSettings.setRowProcessor(rowProcessor)
    parserSettings.setHeaderExtractionEnabled(true);


    val parser = new CsvParser(parserSettings)
    parser.parse(new File(fileName))
    val headers: Map[String, Int] = rowProcessor.getHeaders.zipWithIndex.toMap
    logger.info("Headers and indexes of parsed file : {}", headers)
    import collection.JavaConverters._

    val rows = rowProcessor.getRows.asScala.toSeq

    logger.info(s"rows size ${rows.size}")
    val result = rows.map(line => {

      val lineData = line(0).split(",")
      val tripDuration: Option[String] = headers.get("tripduration").map(index => lineData(index))
      val startTime: Option[String] = headers.get("starttime").map(index => lineData(index))
      val stopTime: Option[String] = headers.get("stoptime").map(index => lineData(index))
      val startStationId: Option[String] = headers.get("start station id").map(index => lineData(index))
      val starStationName: Option[String] = headers.get("start station name").map(index => lineData(index))
      val startStationLatitude: Option[String] = headers.get("start station latitude").map(index => lineData(index))
      val startStationLongitude: Option[String] = headers.get("start station longitude").map(index => lineData(index))
      val endStationId: Option[String] = headers.get("end station id").map(index => lineData(index))
      val endStationName: Option[String] = headers.get("end station name").map(index => lineData(index))
      val endStationLatitude: Option[String] = headers.get("end station latitude").map(index => lineData(index))
      val endStationLongitude: Option[String] = headers.get("end station longitude").map(index => lineData(index))
      val bikeId: Option[String] = headers.get("bikeid").map(index => lineData(index))
      val userType: Option[String] = headers.get("usertype").map(index => lineData(index))
      val birthYear: Option[String] = headers.get("birth year").map(index => lineData(index))
      val gender: Option[String] = headers.get("gender").map(index => lineData(index))


      BikeTravelData(tripDuration, startTime, stopTime, startStationId, starStationName, startStationLatitude, startStationLongitude,
        endStationId, endStationName, endStationLatitude, endStationLongitude, bikeId, userType, birthYear, gender)

    })

    logger.info("end parse csv")


    result
  }

  val bikeTravelData: Seq[BikeTravelData] = parseCsv("Task.csv")


  val bikeTravelTime: Seq[BikeTravelTime] = transformStringToDate(bikeTravelData)
  val all: ArrayBuffer[Int] = ArrayBuffer(bikeTravelData.size, time(bikeTravelTime).toInt, countUseBike(bikeTravelData).size)

  writeFile1("general-stats.csv", all, mailAndFeMail(bikeTravelData))
  writeFile2("usage-stats.csv", month(bikeTravelTime))
  writeFile3(" bike-stats.csv", countUseBike(bikeTravelData))
  logger.info("End program")


  def transformStringToDate(bikeId: Seq[BikeTravelData]): Seq[BikeTravelTime] = {
    logger.info("Start transformStringToDate function ")

    val result = bikeId.map(line => {
      val c1: Option[Date] = line.startTime.filter(element => !element.isEmpty).flatMap(x => {
        try {
          Option(format.parse(x))
        } catch {
          case x: Exception => {
            logger.error("error massage :", x)

            None
          }
        }
      })

      val c2: Option[Date] = line.stopTime.filter(element => !element.isEmpty).flatMap(x => {
        try {
          Option(format.parse(x))
        } catch {
          case x: Exception => {
            logger.error("error massage :{}", x)
            None
          }
        }


      })


      c1.zip(c2)
    }).filter(x => x.isDefined).map(_.get).map(dto => {
      BikeTravelTime(dto._1, dto._2)
    })

    result

  }

  def writeFile1(fileName: String, lines: ArrayBuffer[Int], sex: Seq[(Option[String], Int)]): Unit = {
    logger.info("Start writeFile function ")

    val fail = new File(fileName)
    val bw = new BufferedWriter(new FileWriter(fail))
    for (line <- lines) {
      bw.write(line + "\n")
    }
    for ((k, v) <- sex) {
      bw.write(k.toString + "," + v.toString + "\n")
    }
    bw.close()
  }

  def writeFile2(fileName: String, lines: Seq[(Int, Int)]): Unit = {
    logger.info("Start writeFile2 function ")

    val fail = new File(fileName)
    val bw = new BufferedWriter(new FileWriter(fail))
    for ((k, v) <- lines) {
      bw.write(k.toString + "," + v.toString + "\n")
    }
    bw.close()
  }

  def writeFile3(fileName: String, lines: Seq[(Option[String], Int)]): Unit = {
    logger.info("Start writeFile3 function ")

    val fail = new File(fileName)
    val bw = new BufferedWriter(new FileWriter(fail))
    for ((k, v) <- lines) {
      bw.write(k.toString + "," + v.toString + "\n")
    }
    bw.close()
  }


}
