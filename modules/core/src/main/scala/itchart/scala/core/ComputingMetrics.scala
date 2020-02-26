package itchart.scala.core

import itchart.scala.core.ModelClasses.{BikeTravelData, BikeTravelTime}
import org.apache.logging.log4j.LogManager

object ComputingMetrics {

  private val logger = LogManager.getLogger("CSVProceeding")
  def countUseBike(bikeId: Seq[BikeTravelData]): Seq[(Option[String], Int)] = {
    logger.info("Start countUseBike function ")
    logger.debug("CountUseBike ={}",bikeId.groupBy(_.bikeId).mapValues(_.size).toSeq.sortWith(_._2 > _._2))

    bikeId.groupBy(_.bikeId).mapValues(_.size).toSeq.sortWith(_._2 > _._2)

  } //2в и 4

  def mailAndFeMail(sex: Seq[BikeTravelData]): Seq[(Option[String], Int)] = {
    logger.info("Start mailAndFeMail function ")
    logger.debug("MailAndFeMail ={}",sex.groupBy(_.gender).mapValues(_.size).toSeq )

    sex.groupBy(_.gender).mapValues(_.size).toSeq
    //logger.info("End mailAndFeMail function ")

  } //2г
  def month(seqWithData: Seq[BikeTravelTime]): Seq[(Int, Int)] = {
    logger.info("Start month function ")
    logger.debug("Month ={}",seqWithData.groupBy(_.startTime.getMonth).mapValues(_.size).toSeq)

    seqWithData.groupBy(_.startTime.getMonth).mapValues(_.size).toSeq
  } //3

  def time(s: Seq[BikeTravelTime]): Long = {
    logger.info("Start time function ")
    logger.debug("Time ={}",    s.map(elem => elem.stopTime.getTime - elem.startTime.getTime).maxBy(identity))

    s.map(elem => elem.stopTime.getTime - elem.startTime.getTime).maxBy(identity)
  }
}
