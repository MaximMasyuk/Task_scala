package itchart.scala.vew

import java.io.File

import com.univocity.parsers.common.processor.RowListProcessor
import com.univocity.parsers.csv.{CsvParser, CsvParserSettings}
import org.apache.logging.log4j.LogManager


object CsvParse {
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

}
