package tjeneste

import java.util.Date
import spark.Spark._

object Bysykkeltjener extends App {

  get("/ping", (req, res) => s"I'm awake!? Promise! (${new Date()})\n")

  get("/avail", (req, res) => {
    res.`type`("application/json")
    s"{ 'stations' : 'aaaa', 'updated_at':'${new Date()}', 'refresh_rate':'7.0' }"
  })

}
