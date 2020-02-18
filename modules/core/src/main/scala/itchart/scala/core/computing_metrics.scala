package itchart.scala.core

import java.text.SimpleDateFormat
import java.util.Date
import org.apache.logging.log4j.LogManager

object computing_metrics {
  private var date = 0
  private var date1 = new Date()

  private val monthOfTravel = 1

  def faunddate(date: String): Date = {
    val format = new SimpleDateFormat("\"MM/dd/yyyy hh:mm:ss\"")
    format.parse(date)

  }

  def timttrevel(date1: Date, date2: Date): Int = {
    if (date < (date2.getTime.toInt - date1.getTime.toInt)) {
      date = (date2.getTime.toInt - date1.getTime.toInt)
    }
    date
  }



  def month(seqWithData: Seq[String]): Seq[(Char,Int)] ={
    seqWithData.groupBy(_.charAt(monthOfTravel)).mapValues(_.size).toSeq
  }

  def countusebike(bikeid: Seq[String]): Seq[(String,Int)] = {

    bikeid.groupBy(_.toString).mapValues(_.size).toSeq.sortWith(_._2 > _._2)
  }

  def mailAndFeMail(sex: Seq[String] ): Seq[(String,Int)] = {
    sex.groupBy(_.toString).mapValues(_.size).toSeq
  }
}//yfpdfnm yjhvfymyj
//reduse