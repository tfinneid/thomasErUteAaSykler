package tjeneste

import java.util.Date

object Bysykkeltjener extends App {

  get("/ping", (req, res) => s"I'm awake!? Promise! (${new Date()})\n")

}
