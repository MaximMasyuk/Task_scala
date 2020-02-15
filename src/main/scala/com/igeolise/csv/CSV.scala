package com.igeolise.csv

import java.io.{BufferedWriter, File, FileWriter}
import java.text.SimpleDateFormat
import java.util.Date

import com.univocity.parsers.common.processor.RowListProcessor
import com.univocity.parsers.csv.{CsvParser, CsvParserSettings}
import org.apache.logging.log4j.LogManager

import scala.collection.immutable.ListMap
import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, Map}

object CSV extends App {

  val logger = LogManager.getLogger("CSVProceeding")
  var mail = 0
  var All = ArrayBuffer[Int]()
  var Manth = mutable.Map[Int, Int]()
  var bike = mutable.Map[String, Int]()
  var allbikes = Set[String]()
  var femail = 0

  var date = 0
  var date1 = new Date()
  var date10 = new Date()
  var date11 = new Date()

  var date2 = new Date()

  logger.info("start project")

  def parseCsv(fileName: String): Unit = {
    logger.info("start parse csv")
    val parserSettings = new CsvParserSettings()
    parserSettings.setLineSeparatorDetectionEnabled(true)
    val rowProcessor = new RowListProcessor
    parserSettings.setRowProcessor(rowProcessor)
    parserSettings.setHeaderExtractionEnabled(true);


    val parser = new CsvParser(parserSettings)
    parser.parse(new File(fileName))
    val headers = rowProcessor.getHeaders


    val rows = rowProcessor.getRows

    for (lines <- 0 until headers.size) {
      for (line <- 0 until rows.size()) {

        val get = rows.get(line)(0).split(",")


        if (headers(lines) == "starttime") {
          manth(get(lines))
          date10 = faunddate(get(lines))

        }

        if (headers(lines) == "stoptime") {
          date11 = faunddate(get(lines))
        }


        if (headers(lines) == "starttime") {
          val date10 = faunddate(get(lines))
          manth(get(lines))
        }


        if (headers(lines) == "gender") {
          countMail(get(lines))
          countFemail(get(lines))


        }
        if (headers(lines) == "bikeid") {
          countusebike(get(lines))
          countbikeid(get(lines))
        }
        timt_trevel(date10, date11)

      }


    }
    logger.info("end parse csv")

    logger.debug(s"Number of men and women Result=$mail$femail")
    logger.debug(s"time of the longest trip Result=$date")
    logger.debug(s"number of trips per month Result=$Manth")
    logger.debug(s"the number of trips in decreasing number of trips Result=$bike")


    All.append(rows.size(), date, (allbikes.size), femail, mail)

    logger.info("start writing in csv file")
    writeFile1("general-stats.cvs", All)

    writeFile2("usage-stats.cvs", Manth)

    writeFile3("bike-stats.cvs", ListMap(bike.toSeq.sortWith(_._2 > _._2): _*))

    logger.info("end writing in csv file")

  }


  parseCsv("Task.csv")

  logger.info("end project")

  def faunddate(date: String): Date = {
    val format = new SimpleDateFormat("\"MM/dd/yyyy hh:mm:ss\"")
    date1 = (format.parse(date))
    return date1
  }

  def timt_trevel(date1: Date, date2: Date): Int = {
    if (date < (date2.getTime.toInt - date1.getTime.toInt)) {
      date = (date2.getTime.toInt - date1.getTime.toInt)
    }
    return date //2
  }


  def manth(date: String): Unit = {

    val format = new SimpleDateFormat("\"MM/dd/yyyy hh:mm:ss\"")
    val date1 = (format.parse(date))
    if (!Manth.contains(date1.getMonth)) {
      Manth += (date1.getMonth -> 1)
    }
    else {
      Manth(date1.getMonth) += 1
    }

  }

  def countMail(sex: String): Unit = {

    if (sex == "\"1\"") {
      mail += 1
    }

  }

  def countFemail(sex: String): Unit = {

    if (sex == "\"2\"") {
      femail += 1
    }

  }

  def countusebike(bikeid: String): Unit = {

    if (!bike.contains(bikeid)) {
      bike += (bikeid -> 1)
    }

    else {
      bike(bikeid) += 1
    }
  }

  def countbikeid(bikeid: String): Unit = {
    allbikes += bikeid


  }

  def writeFile1(fileName: String, lines: ArrayBuffer[Int]): Unit = {
    val fail = new File(fileName)
    val bw = new BufferedWriter(new FileWriter(fail))
    for (line <- lines) {
      bw.write(line + "\n")
    }
    bw.close()
  }

  def writeFile2(fileName: String, lines: Map[Int, Int]): Unit = {
    val fail = new File(fileName)
    val bw = new BufferedWriter(new FileWriter(fail))
    for ((k, v) <- lines) {
      bw.write(k.toString + "," + v.toString + "\n")
    }
    bw.close()
  }

  def writeFile3(fileName: String, lines: ListMap[String, Int]): Unit = {
    val fail = new File(fileName)
    val bw = new BufferedWriter(new FileWriter(fail))
    for ((k, v) <- lines) {
      bw.write(k.toString + "," + v.toString + "\n")
    }
    bw.close()
  }
}
