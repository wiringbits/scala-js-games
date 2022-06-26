# Scala.js Games (Scala 3)

This is a collection of games ported from Scala 2 to Scala.js (Scala 3): the [source](https://github.com/wiringbits/scala-js-games) for each game is written in Scala, and [Scala.js](https://scala-js.org) cross compiled to run in the browser targeting the [HTML5 Canvas](https://developer.mozilla.org/en-US/docs/HTML/Canvas). The original games can be found [here](https://github.com/lihaoyi/scala-js-games) and targeting Scala 2.

[Live demo](https://scalajs-games.wiringbits.net)

The games are, in order:

<ul>
    <li><b>Asteroids</b>: shoot down the asteroid swarms and avoid getting hit!</li>
    <li><b>Astrolander</b>: bring your lander to a a gentle landing on flat ground before you run out of fuel.</li>
    <li><b>Snake</b>: eat apples to grow long and don't crash into walls!</li>
    <li><b>Pong</b>: outsmart your AI opponent to get the ball past his paddle (right) to score points!</li>
    <li><b>Brick</b>: use your paddle to bounce the ball up, destroying all the bricks before you run out of balls.</li>
    <li><b>Tetris</b>: collect points by clearing rows and don't let the screen fill with blocks.</li>
</ul>

<p>
    The controls are generally up-down-left-right and spacebar; they
    aren't very complex games. Click on a game to start playing and click
    somewhere else to pause it.
</p>

## How to build
- Install [sdkman](https://sdkman.io) - or, make sure to pick the correct java version (see [.sdkmanrc](.sdkmanrc)).
- Install [nvm](https://github.com/nvm-sh/nvm) - or, make sure to pick the correct node version (see [.nvmrc](.nvmrc)).
- Install [sbt](https://www.scala-sbt.org/).
- Clone the repo.
- Run `sbt fastLinkJS` (just to make sure the app compiles).
- Run `npm install` to install the js dependencies.
- Run `npm run dev` to launch the dev server, then, open `http://localhost:3000` to load the app.
- Run `npm run build` to prepare the production build.
