import org.scalatest._

class MathematicsTest extends FlatSpec {

   "A Maths class" should "support pow()" in {
      val maths = new Mathematics
      assert(maths.pow(3) == 9)
   }
}
