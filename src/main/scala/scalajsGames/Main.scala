package scalajsGames

import scala.scalajs.js
import org.scalajs.dom
import games.*

@main
def ScalajsGames(): Unit = {
  dom.document.querySelector("#app").innerHTML = """
    <h1>Hello Scala.js</h1>
    <a href="https://vitejs.dev/guide/features.html" target="_blank">Documentation</a>
    """

  val asteroids = new GameHolder("asteroids", Asteroids.apply)
//  val astrolander = new GameHolder("astrolander", AstroLander.apply)
  val snake = new GameHolder("snake", Snake.apply)
  val pong = new GameHolder("pong", Pong.apply)
  val bricks = new GameHolder("bricks", BrickBreaker.apply)
  val tetris = new GameHolder("tetris", Tetris.apply)
//  val games = Seq(asteroids, astrolander, snake, pong, bricks, tetris)
  val gameList = Seq(asteroids, snake, pong, bricks, tetris)
  dom.window.setInterval(() => gameList.foreach(_.update()), 15)
}
