package itchart.scala.core

object computing_metrics {

  def countUseBike(bikeId: Seq[BikeTravelData]): Seq[(Option[String], Int)] = {

    bikeId.groupBy(_.bikeId).mapValues(_.size).toSeq.sortWith(_._2 > _._2)
  } //2в и 4

  def mailAndFeMail(sex: Seq[BikeTravelData]): Seq[(Option[String], Int)] = {
    sex.groupBy(_.gender).mapValues(_.size).toSeq
  } //2г
  def month(seqWithData: Seq[BikeTrevelTime]): Seq[(Int, Int)] = {
    seqWithData.groupBy(_.startTime.getMonth).mapValues(_.size).toSeq
  } //3

  def time(s: Seq[BikeTrevelTime]): Long = {
    s.map(elem => elem.stopTime.getTime - elem.startTime.getTime).maxBy(identity)
  }
}