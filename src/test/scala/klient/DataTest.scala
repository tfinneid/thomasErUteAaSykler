package klient

import org.scalatest._

class DataTest extends FlatSpec {

   "A Maths " should "support pow()" in {
      val maths = new Mathematics
      assert(maths.pow(3) == 9)
   }
}
