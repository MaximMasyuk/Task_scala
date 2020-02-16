package com.igeolise.csv


import java.io.{BufferedWriter, File, FileWriter}
import java.text.SimpleDateFormat
import java.util.Date

import com.univocity.parsers.common.processor.RowListProcessor
import com.univocity.parsers.csv.{CsvParser, CsvParserSettings}
import org.apache.logging.log4j.LogManager

import scala.collection.mutable.ArrayBuffer

object CSV extends App {

  private val logger = LogManager.getLogger("CSVProceeding")

  var All = ArrayBuffer[Int]()

  var seqWithData = Seq[String]()
  var bikeid = Seq[String]()
  var sex = Seq[String]()


   var date = 0
   var date10 = new Date()
  var date11 = new Date()

  val monthOfTravel = 1



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
          date10 = faunddate(get(lines))
          seqWithData = seqWithData :+ get(lines)
        }

        if (headers(lines) == "stoptime") {
          date11 = faunddate(get(lines))
        }



        if (headers(lines) == "gender") {
          sex  = sex :+ get(lines)
        }


        if (headers(lines) == "bikeid") {
          bikeid = bikeid :+ get(lines)
        }

        timt_trevel(date10, date11)

      }


    }
    logger.info("end parse csv")






    //logger.debug(s"Number of men and women Result=$mail$femail")
    logger.debug(s"time of the longest trip Result=$date")
    //logger.debug(s"number of trips per month Result=$Manth")
    //logger.debug(s"the number of trips in decreasing number of trips Result=$bike")




    All.append(rows.size(), date, (countusebike(bikeid).size))

    logger.info("start writing in csv file")
    writeFile("general-stats.cvs", All, mailAndFemail(sex))

    writeFile("usage-stats.cvs", month(seqWithData))

    writeFile3("bike-stats.cvs", countusebike(bikeid))

    logger.info("end writing in csv file")

  }


  parseCsv("Task.csv")

  logger.info("end project")

  def faunddate(date: String): Date = {
    val format = new SimpleDateFormat("\"MM/dd/yyyy hh:mm:ss\"")
    return (format.parse(date))

  }

  def timt_trevel(date1: Date, date2: Date): Int = {
    if (date < (date2.getTime.toInt - date1.getTime.toInt)) {
      date = (date2.getTime.toInt - date1.getTime.toInt)
    }
    return date //2 а
  }



  def month(seqWithData: Seq[String]): Seq[(Char,Int)] ={
    return seqWithData.groupBy(_.charAt(monthOfTravel)).mapValues(_.size).toSeq
  }//3

  def countusebike(bikeid: Seq[String]): Seq[(String,Int)] = {

    return  bikeid.groupBy(_.toString).mapValues(_.size).toSeq.sortWith(_._2 > _._2)
  }//2в и 4

  def mailAndFemail(sex: Seq[String] ): Seq[(String,Int)] = {
    return sex.groupBy(_.toString).mapValues(_.size).toSeq
  }//2г



  def writeFile(fileName: String, lines: ArrayBuffer[Int],sex: Seq[(String,Int)] ): Unit = {
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

  def writeFile(fileName: String, lines:Seq[(Char,Int)]): Unit = {
    val fail = new File(fileName)
    val bw = new BufferedWriter(new FileWriter(fail))
    for ((k, v) <- lines) {
      bw.write(k.toString + "," + v.toString + "\n")
    }
    bw.close()
  }

  def writeFile3(fileName: String, lines: Seq[(String,Int)]): Unit = {
    val fail = new File(fileName)
    val bw = new BufferedWriter(new FileWriter(fail))
    for ((k, v) <- lines) {
      bw.write(k.toString + "," + v.toString + "\n")
    }
    bw.close()
  }
}
