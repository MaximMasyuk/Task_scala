package com.igeolise

import java.util.Date

package object csv {


   final case class BikeTrevelTime(startTime: Option[Date],
                                   stopTime: Option[Date])


   final case class BikeTravelData(tripDuration: Option[String],
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


}
