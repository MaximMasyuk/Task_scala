
import java.io.{BufferedWriter, File, FileWriter}
import java.text.SimpleDateFormat

import scala.collection.immutable.ListMap
import scala.collection.mutable.{ArrayBuffer, Map}
import scala.language.reflectiveCalls



object CsvTask extends  App {

  var bike = Map[String, Int ]()
  var allbikes = Set[String]()
  var Manth = Map[Int, Int]()
  var All = ArrayBuffer[String]()
  var mail = 0
  var femail = 0
  var date = 0
  var date4 = 0
  var alltrevel = 0


  using(io.Source.fromFile("201608-citibike-tripdata.csv")) { source =>

  {
    val allstring  = source.getLines().toArray

    for (line <- 1 to allstring.size-1) {
      val split_string = allstring(line).split(",")

      manth1(split_string(1))
      countbikeid(split_string(11))
      countusebike(split_string(11))
      mail = countMail(split_string(14))
      femail = countFemail(split_string(14))
      date4 = trevel_time(split_string(1), split_string(2))
      alltrevel+=1

    }
    val m = (mail * 100 / alltrevel + "%")
    val f = (femail * 100 / alltrevel + "%")
    All.append(alltrevel.toString, date4.toString, (allbikes.size).toString, f, m )


    writeFile1("general-stats.cvs", All)

    writeFile2 ("usage-stats.cvs", Manth)

    writeFile3("bike-stats.cvs", ListMap(bike.toSeq.sortWith(_._2 > _._2):_*) )
    }
  }


  def manth1(date: String) :Unit={

    val format = new SimpleDateFormat("\"MM/dd/yyyy hh:mm:ss\"")
    val date1 = (format.parse(date))

    if (!Manth.contains(date1.getMonth))
      { Manth += (date1.getMonth -> 1) }
    else
      { Manth(date1.getMonth) += 1 }
  }


  def countbikeid(bikeid : String): Unit ={
    allbikes += bikeid


  }



  def countusebike(bikeid : String):Unit = {

    if (!bike.contains(bikeid)){
      bike += (bikeid-> 1)
    }

    else
    {
      bike(bikeid) += 1
    }
  }

  def countMail(sex : String) :Int ={

    if (sex == "\"1\"" ){
      mail += 1
    }
    return mail
  }

  def countFemail(sex : String) :Int ={

    if (sex == "\"2\"" ){
      femail += 1
    }
    return femail
  }
  def trevel_time (datestart: String, dateend:String): Int={
    val format = new SimpleDateFormat("\"MM/dd/yyyy hh:mm:ss\"")
    val date1 = (format.parse(datestart))

    val date2 = (format.parse(dateend))


    if (date<(((date2.getMinutes - date1.getMinutes)*60)+(date2.getSeconds - date1.getSeconds))){
      date = (((date2.getMinutes - date1.getMinutes)*60)+(date2.getSeconds - date1.getSeconds))
    }

    return  date
  }

  def using[A <: { def close(): Unit }, B](param: A)(f: A => B): B =
    try {
      f(param)
    } finally {
      param.close()
    }


  def writeFile1 (fileName:String ,lines : ArrayBuffer[String]):Unit = {
    val fail = new File(fileName)
    val bw = new BufferedWriter(new FileWriter(fail))
    for (line <- lines){
    bw.write(line + "\n")
    }
    bw.close()
  }
  def writeFile2 (fileName:String ,lines : Map[Int,Int]):Unit = {
    val fail = new File(fileName)
    val bw = new BufferedWriter(new FileWriter(fail))
    for ((k,v) <- lines){
      bw.write(k.toString+ "," + v.toString  + "\n")
    }
    bw.close()
  }
  def writeFile3 (fileName:String ,lines : ListMap[String,Int]):Unit = {
    val fail = new File(fileName)
    val bw = new BufferedWriter(new FileWriter(fail))
    for ((k,v) <- lines){
      bw.write(k.toString+ "," + v.toString  + "\n")
    }
    bw.close()
  }
}


