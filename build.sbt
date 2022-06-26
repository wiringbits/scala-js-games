import org.scalajs.linker.interface.ModuleSplitStyle

val fastLinkOutputDir = taskKey[String]("output dir for 'npm run dev'")
val fullLinkOutputDir = taskKey[String]("output dir for 'npm run build'")

lazy val scalajsGames = project
  .in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    scalaVersion := "3.1.2",
    scalaJSUseMainModuleInitializer := true,
    scalaJSLinkerConfig ~= {
      _.withModuleKind(ModuleKind.ESModule)
        .withModuleSplitStyle(ModuleSplitStyle.SmallModulesFor(List("scalajsGames")))
    },
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.2.0",
    fastLinkOutputDir := {
      // Ensure that fastLinkJS has run, then return its output directory
      (Compile / fastLinkJS).value
      (Compile / fastLinkJS / scalaJSLinkerOutputDirectory).value.getAbsolutePath
    },
    fullLinkOutputDir := {
      // Ensure that fullLinkJS has run, then return its output directory
      (Compile / fullLinkJS).value
      (Compile / fullLinkJS / scalaJSLinkerOutputDirectory).value.getAbsolutePath
    }
  )
