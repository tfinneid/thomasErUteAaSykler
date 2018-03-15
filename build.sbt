name := "bysykkel"
version := "1.0"

scalaVersion := "2.12.1"
cancelable in Global := true

mainClass in (Compile,run) := Some("ThomasErUteAaSykler")
packageOptions in (Compile, packageBin) += Package.ManifestAttributes( "Main-Class" -> "ThomasErUteAaSykler" )

libraryDependencies += "com.mashape.unirest" % "unirest-java" % "1.4.9"

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.7.9"
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.7.9"
libraryDependencies += "com.sparkjava" % "spark-core" % "2.7.2"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % "2.12.1",
  "org.scala-lang.modules" % "scala-xml_2.12" % "1.0.6"
)

//scalacOptions ++= Seq("-deprecation", "-feature")

