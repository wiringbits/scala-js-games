package scalajsGames

import org.scalajs.dom

import scala.scalajs.js

abstract class Game {
  var result: Option[String] = None
  var finalScore: Option[Int] = None
  def update(keys: Set[Int]): Unit

  def draw(ctx: dom.CanvasRenderingContext2D): Unit

  implicit class pimpedContext(val ctx: dom.CanvasRenderingContext2D) {
    def fillCircle(x: Double, y: Double, r: Double) = {
      ctx.beginPath()
      ctx.arc(x, y, r, 0, math.Pi * 2)
      ctx.fill()
    }
    def strokePath(points: Point*) = {

      ctx.beginPath()
      ctx.moveTo(points.last.x, points.last.y)
      for (p <- points) {
        ctx.lineTo(p.x, p.y)
      }
      ctx.stroke()
    }
  }
}
