ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.19"

lazy val root = (project in file("."))
  .settings(
    name := "finalproject",
    libraryDependencies ++= Seq(
      "org.scalafx" %% "scalafx" % "8.0.192-R14",
      "org.scalafx" %% "scalafxml-core-sfx8" % "0.5",
      "org.scalikejdbc" %% "scalikejdbc" % "3.1.0",
      "com.h2database" % "h2" % "1.4.196",
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "org.apache.derby" % "derby" % "10.13.1.1",
      "org.scala-lang.modules" %% "scala-collection-compat" % "2.6.0"
    )
  )

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)