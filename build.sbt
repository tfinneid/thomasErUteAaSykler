name := "bysykkel"
version := "1.0"

scalaVersion := "2.12.1"
cancelable in Global := true

//packageOptions in (Compile, packageBin) += Package.ManifestAttributes( "Main-Class" -> "com.Main" )

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
libraryDependencies += "com.sparkjava" % "spark-core" % "2.7.2"
//libraryDependencies += "com.typesafe.play" %% "play-ws" % "2.6.12"
libraryDependencies += "com.typesafe.play" %% "play-ahc-ws-standalone" % "1.1.6"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % "2.12.1",
  "org.scala-lang.modules" % "scala-xml_2.12" % "1.0.6"
)

// Alternative options

//scalaSource in Compile := baseDirectory.value / "src"
//scalaSource in Test := baseDirectory.value / "src-test"
//scalacOptions ++= Seq("-deprecation", "-feature")
//scalaHome := Some(file("/Users/tofi/bin/scala/"))

