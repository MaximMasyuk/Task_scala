package itchart.scala.core

object computing_metrics {

  val monthOfTravel = 1
  def month(seqWithData: Seq[String]): Unit={
    seqWithData.groupBy(_.charAt(monthOfTravel)).mapValues(_.size).toSeq
  }//3

  def countusebike(bikeid: Seq[String]): Seq[(String,Int)] = {

    return  bikeid.groupBy(_.toString).mapValues(_.size).toSeq.sortWith(_._2 > _._2)
  }//2в и 4

  def mailAndFemail(sex: Seq[String] ):Unit = {
    sex.groupBy(_.toString).mapValues(_.size).toSeq
  }//2г

}
