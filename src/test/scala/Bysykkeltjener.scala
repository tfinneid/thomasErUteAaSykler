package tjeneste

import java.util.Date

import spark.Request
import spark.Spark._

object Bysykkeltjener extends App {

  get("/ping", (req, res) => {
    log(req)
    s"I'm awake!? Promise! (${new Date()})\n"
  })

  get("/api/v1/stations", (req, res) => {
    log(req)
    res.`type`("application/json")
    s"""
       {
         "stations":[
           {
             "id": 210,
             "title": "Birkelunden",
             "subtitle": "langs Seilduksgata",
             "number_of_locks": 10,
             "center": {
               "latitude": 59.925622,
               "longitude": 10.760822
             },
             "bounds": [
               {
                 "latitude": 59.92559918218687,
                 "longitude": 10.760778486728668
               },
               {
                 "latitude": 59.925603214545724,
                 "longitude": 10.76099306344986
               },
               {
                 "latitude": 59.9256529469314,
                 "longitude": 10.760995745658873
               },
               {
                 "latitude": 59.9257161203949,
                 "longitude": 10.760791897773741
               },
               {
                 "latitude": 59.9256919263167,
                 "longitude": 10.760748982429503
               },
               {
                 "latitude": 59.92568117338741,
                 "longitude": 10.76061487197876
               },
               {
                 "latitude": 59.92559918218687,
                 "longitude": 10.760778486728668
               }
             ]
           },
           {
             "id": 191,
             "title": "Jakob kirke",
             "subtitle": "langs Torggata",
             "number_of_locks": 9,
             "center": {
               "latitude": 59.917879,
               "longitude": 10.754906
             },
             "bounds": [
               {
                 "latitude": 59.91771363912163,
                 "longitude": 10.7547327876091
               },
               {
                 "latitude": 59.91789110461389,
                 "longitude": 10.755239725112913
               },
               {
                 "latitude": 59.9180309252425,
                 "longitude": 10.755076110363005
               },
               {
                 "latitude": 59.917846738329786,
                 "longitude": 10.754566490650177
               },
               {
                 "latitude": 59.91771363912163,
                 "longitude": 10.7547327876091
               }
             ]
           },
           {
             "id": 7,
             "title": "Ullevålsveien",
             "subtitle": "langs Vår Frelsers gravlund",
             "number_of_locks": 9,
             "center": {
               "latitude": 59.917879,
               "longitude": 10.754906
             },
             "bounds": [
               {
                 "latitude": 59.91771363912163,
                 "longitude": 10.7547327876091
               },
               {
                 "latitude": 59.91789110461389,
                 "longitude": 10.755239725112913
               },
               {
                 "latitude": 59.9180309252425,
                 "longitude": 10.755076110363005
               },
               {
                 "latitude": 59.917846738329786,
                 "longitude": 10.754566490650177
               },
               {
                 "latitude": 59.91771363912163,
                 "longitude": 10.7547327876091
               }
             ]
           }
         ]
       }
      """
  })

  get("/api/v1/stations/availability", (req, res) => {
    log(req)
    res.`type`("application/json")
    s"""
    {
      "stations": [
      {
        "id": 210,
        "availability": {
          "bikes": 7,
          "locks": 3
        }
      },
      {
        "id": 7,
        "availability": {
          "bikes": 19,
          "locks": 24
        }
      },
      {
        "id": 345,
        "availability": {
          "bikes": 19,
          "locks": 24
        }
      },
      {
        "id": 191,
        "availability": {
          "bikes": 2,
          "locks": 7
        }
      }
      ],
      "updated_at": "2016-09-09T09:50:33+00:00",
      "refresh_rate": 10.0
    }
      """
  })

  get("/api/v1/stations/availability/empty", (req, res) => {
    log(req)
    ""
  })

  get("/api/v1/stations/availability/big", (req, res) => {
    log(req)
    res.`type`("application/json")
    val bigResponse =
      """|      {
         |        "id": 210,
         |        "availability": {
         |          "bikes": 7,
         |          "locks": 3
         |        }
         |      },
         |""".stripMargin * 10000

    s"""
    {
      "stations": [
      $bigResponse
      {
        "id": 191,
        "availability": {
          "bikes": 2,
          "locks": 7
        }
      }
      ],
      "updated_at": "2016-09-09T09:50:33+00:00",
      "refresh_rate": 10.0
    }
      """
  })

  def log(req: Request): Unit = {
    println(s"Got ${req.url()} request from ${req.ip()} with client-identifier: ${req.headers("Client-Identifier")}")
  }
}
