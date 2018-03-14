package tjeneste

import java.util.Date

import spark.Spark._

object Bysykkeltjener extends App {

  get("/ping", (req, res) => s"I'm awake!? Promise! (${new Date()})\n")

  get("/avail", (req, res) => {
    // TODO Hvordan logge?
    println(s"Got ${req.url()} request from ${req.ip()}")
    res.`type`("application/json")
    s"""{ "stations":"aaaa", "updated_at":"${new Date()}", "refresh_rate":7.0 }\n  """"
  })

  get("/stations", (req, res) => {
    println(s"Got ${req.url()} request from ${req.ip()}")
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
           }
         ]
       }
      """
  })

  get("/stations/availability", (req, res) => {
    println(s"Got ${req.url()} request from ${req.ip()}")
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
      """   //  "updated_at": "2016-09-09T09:50:33+00:00", "updated_at": "${new Date()}",
  })
}
