package itchart.scala.core

import java.io.File
import java.time.LocalDate

object ModelClasses {

  case class BikeTravelData(tripDuration: Option[String],
                            startTime: Option[String],
                            stopTime: Option[String],
                            startStationId: Option[String],
                            startStationName: Option[String],
                            startStationLatitude: Option[String],
                            startStationLongitude: Option[String],
                            endStationId: Option[String],
                            endStationName: Option[String],
                            endStationLatitude: Option[String],
                            endStationLongitude: Option[String],
                            bikeId: Option[String],
                            userType: Option[String],
                            birthYear: Option[String],
                            gender: Option[String])

  case class BikeTravelTime(startTime:LocalDate,
                            stopTime:LocalDate)


  case class Config(foo: String = "Task.csv",
                    out: File = new File("."),
                    xyz: Boolean = false,
                    libName: String = "",
                    maxCount: Int = -1,
                    verbose: Boolean = false,
                    debug: Boolean = false,
                    mode: String = "",
                    files: Seq[File] = Seq(),
                    keepalive: Boolean = false,
                    jars: Seq[File] = Seq(),
                    kwargs: Map[String, String] = Map())

}
