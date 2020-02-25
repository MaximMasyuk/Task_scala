package itchart.scala.vew

import java.io.{BufferedWriter, File, FileWriter}
import java.text.SimpleDateFormat
import java.util.Date

import com.univocity.parsers.common.processor.RowListProcessor
import com.univocity.parsers.csv.{CsvParser, CsvParserSettings}
import itchart.scala.core.computing_metrics.{countUseBike, mailAndFeMail, month, time}
import itchart.scala.core.{BikeTravelData, BikeTrevelTime}

import scala.collection.mutable.ArrayBuffer


object CsvParse extends App {

  val format: SimpleDateFormat = new SimpleDateFormat("\"MM/dd/yyyy hh:mm:ss\"")
  //private val logger = LogManager.getLogger("CSVProceeding")
  def parseCsv(fileName: String): Seq[BikeTravelData] = {
    //logger.info("start parse csv")
    val parserSettings = new CsvParserSettings()
    parserSettings.setLineSeparatorDetectionEnabled(true)
    val rowProcessor = new RowListProcessor
    parserSettings.setRowProcessor(rowProcessor)
    parserSettings.setHeaderExtractionEnabled(true);


    val parser = new CsvParser(parserSettings)
    parser.parse(new File(fileName))
    val headers: Map[String, Int] = rowProcessor.getHeaders.zipWithIndex.toMap
    //logger.info("Headers and indexes of parsed file : {}", headers)
    import collection.JavaConverters._

    val rows = rowProcessor.getRows.asScala.toSeq

    //logger.info(s"rows size ${rows.size}")
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

    //logger.info("end parse csv")


    result
  }

  val s: Seq[BikeTravelData] = parseCsv("Task.csv")
  val q: Seq[BikeTrevelTime] = transformStringToDate(s)
  val all: ArrayBuffer[Int] = ArrayBuffer(s.size, time(q).toInt, countUseBike(s).size)
  writeFile("general-stats.csv", all, mailAndFeMail(s))
  writeFile2("usage-stats.csv", month(q))
  writeFile3(" bike-stats.csv", countUseBike(s))



  def transformStringToDate(bikeId: Seq[BikeTravelData]): Seq[BikeTrevelTime] = {

    val result = bikeId.map(line => {
      val c1: Option[Date] = line.startTime.filter(element => !element.isEmpty).flatMap(x => {
        try {
          Option(format.parse(x))
        } catch {
          case x: Exception => None
        }
      })

      val c2: Option[Date] = line.stopTime.filter(element => !element.isEmpty).flatMap(x => {
        try {
          Option(format.parse(x))
        } catch {
          case x: Exception => None
        }
      })

      c1.zip(c2)
    }).filter(x => x.isDefined).map(_.get).map(dto => {
      BikeTrevelTime(dto._1, dto._2)
    })

    result

  }

  def writeFile(fileName: String, lines: ArrayBuffer[Int], sex: Seq[(Option[String], Int)]): Unit = {
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
    val fail = new File(fileName)
    val bw = new BufferedWriter(new FileWriter(fail))
    for ((k, v) <- lines) {
      bw.write(k.toString + "," + v.toString + "\n")
    }
    bw.close()
  }

  def writeFile3(fileName: String, lines: Seq[(Option[String], Int)]): Unit = {
    val fail = new File(fileName)
    val bw = new BufferedWriter(new FileWriter(fail))
    for ((k, v) <- lines) {
      bw.write(k.toString + "," + v.toString + "\n")
    }
    bw.close()
  }


}
