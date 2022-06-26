package scalajsGames

import scala.scalajs.js
import org.scalajs.dom

@main
def ScalajsGames(): Unit = {
  dom.document.querySelector("#app").innerHTML = """
    <h1>Hello Scala.js</h1>
    <a href="https://vitejs.dev/guide/features.html" target="_blank">Documentation</a>
    """
}
