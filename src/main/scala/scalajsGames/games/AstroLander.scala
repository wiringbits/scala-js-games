package scalajsGames
package games

import org.scalajs.dom
import scala.util.Random

case class AstroLander(bounds: Point, resetGame: () => Unit) extends Game {
  val points = {
    var current = 450
    var pts = List.empty[Point]
    val flat = Random.nextInt(21)
    val cliff1 = {
      var x = 0
      while
        x = Random.nextInt(21)
        x == flat
      do ()

      x
    }
    val cliff2 = {
      var x = 0
      while
        x = Random.nextInt(21)
        x == flat || x == cliff1
      do ()

      x
    }

    (0 to 21).foreach { n =>
      if (n == flat + 1) current = current
      else if (n == cliff1 + 1) current = current - Random.nextInt(25) - 150
      else if (n == cliff2 + 2) current = current - Random.nextInt(25) + 150
      else current = current - Random.nextInt(50) + 25

      if (current > bounds.y) current = (2 * bounds.y - current).toInt

      pts = Point(n * 40, current) :: pts
    }

    pts.reverse
  }

  var craftPos = Point(400, 25)
  var craftVel = Point(0, 0)
  var theta = -math.Pi / 2
  var fuel = 500

  def shipPoints = Seq(
    craftPos + Point(15, 0).rotate(theta),
    craftPos + Point(7, 0).rotate(theta + 127.5 / 180 * Math.PI),
    craftPos + Point(7, 0).rotate(theta - 127.5 / 180 * Math.PI)
  )

  def draw(ctx: dom.CanvasRenderingContext2D) = {
    ctx.textAlign = "left"
    ctx.fillStyle = Color.Black
    ctx.fillRect(0, 0, bounds.x, bounds.y)


    ctx.fillStyle = if (craftVel.length < 3) Color.Green else Color.White
    ctx.fillText("Speed: " + (craftVel.length * 10).toInt.toDouble / 10, 20, 50)

    ctx.strokeStyle = Color.Green
    ctx.strokeRect(20, 60, math.max(1, fuel) * 65 / 500, 15)
    ctx.fillStyle = Color.White
    ctx.strokeStyle = Color.White
    ctx.strokeRect(20, 60, 65, 15)

    ctx.beginPath()
    ctx.moveTo(0, bounds.y)
    for (p <- points) ctx.lineTo(p.x, p.y)
    ctx.lineTo(bounds.x, bounds.y)
    ctx.fill()

    ctx.beginPath()

    ctx.moveTo(shipPoints.last.x, shipPoints.last.y)
    shipPoints.map(p => ctx.lineTo(p.x, p.y))
    ctx.fill()

    def drawFlame(p: Point, angle: Double) = {
      val offset = math.Pi * 1.25

      def diamond(a: Int, b: Int, c: Int, w: Int) = {
        val width = w * math.Pi / 180

        ctx.strokePath(
          p + Point(a, a).rotate(angle - offset),
          p + Point(b, b).rotate(angle - offset + width),
          p + Point(c, c).rotate(angle - offset),
          p + Point(b, b).rotate(angle - offset - width),
          p + Point(a, a).rotate(angle - offset)
        )
      }

      diamond(5, 15, 25, 15)
      diamond(10, 15, 20, 10)
    }

    ctx.strokeStyle = Color.Red
    if (fuel > 0) {
      if (lastKeys(37)) drawFlame(craftPos, theta + math.Pi / 4)
      if (lastKeys(39)) drawFlame(craftPos, theta - math.Pi / 4)
      if (lastKeys(40)) drawFlame(craftPos, theta)
    }

  }

  var lastKeys: Set[Int] = Set()

  def update(keys: Set[Int]) = {
    lastKeys = keys
    if (fuel > 0) {
      // I changed the first parameter of Point of 0.5 to 0.1, thats makes the ship slowest
      if (keys(37)) craftVel += Point(0.1, 0).rotate(theta + math.Pi / 4)
      if (keys(39)) craftVel += Point(0.1, 0).rotate(theta - math.Pi / 4)
      if (keys(40)) craftVel += Point(0.4, 0).rotate(theta)
      fuel -= Seq(keys(37), keys(39), keys(40)).count(x => x)
    }

    craftVel += Point(0, 0.2)
    craftPos += craftVel

    val shipLost = craftPos.x < 0 || craftPos.x >= bounds.x || craftPos.y <= 1 || craftPos.y >= bounds.y
    val hit = points.flatMap { p =>
      val prevIndex = points.lastIndexWhere(_.x < craftPos.x)


      if (prevIndex == 21) None
      else {
        val prev = points(if prevIndex == -1 then prevIndex + 1 else prevIndex)
        val next = points(prevIndex + 1)
        val height = (craftPos.x - prev.x) / (next.x - prev.x) * (next.y - prev.y) + prev.y
        if (height > craftPos.y && !shipLost) None
        else Some {
          val groundGradient = math.abs((next.y - prev.y) / (next.x - prev.x))
          val landingSkew = math.abs(craftVel.x / craftVel.y)


          if (shipLost) ExtraFailure("You have lost in the space")
          else if (groundGradient > 0.1) Failure("landing area too steep")
          else if (landingSkew > 1) Failure("too much horiontal velocity")
          else if (craftVel.length > 3) Failure("coming in too fast")
          else Success
        }
      }
    }


    hit.headOption.map {
      case Success =>
        result = Some("You have landed successfully.")
        resetGame()
      case Failure(reason) =>
        result = Some("You have crashed your lander: " + reason)
        resetGame()
      case ExtraFailure(reason) =>
        result = Some("You died: " + reason)
        resetGame()
    }
  }
}

trait Collide

case object Success extends Collide

case class Failure(reason: String) extends Collide

case class ExtraFailure(reason: String) extends Collide
