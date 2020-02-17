package com.igeolise.csv


import java.io.File
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
      //logger.info("Line to be processed is : {}", lineData)
      //logger.info("Amount of items in first line  : {}", lineData.size)
      val c1: Option[String] = headers.get("tripduration").map(index => {
        //logger.info("Index of column tripduration is : {}", index)
        //logger.info("Value of column tripduration is : {}", lineData(index))
        lineData(index)
      })
      val c2: Option[String] = headers.get("starttime").map(index => lineData(index))
       val c3: Option[String] = headers.get("stoptime").map(index => lineData(index))
       val c4: Option[String] = headers.get("start station id").map(index => lineData(index))
       val c5: Option[String] = headers.get("start station name").map(index => lineData(index))
       val c6: Option[String] = headers.get("start station latitude").map(index => lineData(index))
       val c7: Option[String] = headers.get("start station longitude").map(index => lineData(index))
       val c8: Option[String] = headers.get("end station id").map(index => lineData(index))
       val c9: Option[String] = headers.get("end station name").map(index => lineData(index))
       val c10: Option[String] = headers.get("end station latitude").map(index => lineData(index))
       val c11: Option[String] = headers.get("end station longitude").map(index => lineData(index))
       val c12: Option[String] = headers.get("bikeid").map(index => lineData(index))
       val c13: Option[String] = headers.get("usertype").map(index => lineData(index))
       val c14: Option[String] = headers.get("birth year").map(index => lineData(index))
       val c15: Option[String] = headers.get("gender").map(index => lineData(index))


      BikeTravelData(c1, c2, c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15)

    })

    logger.info("end parse csv")


    result
    }


  val s = (parseCsv("Task.csv")(0))


  //println(result)


    //logger.debug(s"Number of men and women Result=$mail$femail")
    //logger.debug(s"time of the longest trip Result=$date")
    //logger.debug(s"number of trips per month Result=$Manth")
    //logger.debug(s"the number of trips in decreasing number of trips Result=$bike")




    /*All.append(rows.size(), date, (countusebike(bikeid).size))

    logger.info("start writing in csv file")
    writeFile("general-stats.cvs", All, mailAndFemail(sex))

    writeFile("usage-stats.cvs", month(seqWithData))

    writeFile3("bike-stats.cvs", countusebike(bikeid))

    logger.info("end writing in csv file")*/

  }

/*
  parseCsv("Task.csv")

  logger.info("end project")

  def faunddate(date: String): Date = {
    val format = new SimpleDateFormat("\"MM/dd/yyyy hh:mm:ss\"")
    format.parse(date)

  }

  def timt_trevel(date1: Date, date2: Date): Int = {
    if (date < (date2.getTime.toInt - date1.getTime.toInt)) {
      date = (date2.getTime.toInt - date1.getTime.toInt)
    }
    date //2 а
  }



  def month(seqWithData: Seq[String]): Seq[(Char,Int)] ={
    seqWithData.groupBy(_.charAt(monthOfTravel)).mapValues(_.size).toSeq
  }//3

  def countusebike(bikeid: Seq[String]): Seq[(String,Int)] = {

    bikeid.groupBy(_.toString).mapValues(_.size).toSeq.sortWith(_._2 > _._2)
  }//2в и 4

  def mailAndFemail(sex: Seq[String] ): Seq[(String,Int)] = {
    sex.groupBy(_.toString).mapValues(_.size).toSeq
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
}*/
