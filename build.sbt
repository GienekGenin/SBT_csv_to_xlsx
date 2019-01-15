//#sample-build-sbt
name := "javafx-licences-simple"

organization := "com.quadstingray"

version := "0.1"

scalaVersion := "2.12.7"
javacOptions ++= Seq("-source", "1.8", "-target", "1.8")
libraryDependencies += "org.apache.poi" % "poi" % "4.0.1"

libraryDependencies += "org.jetbrains.kotlin" % "kotlin-stdlib" % "1.3.11"

libraryDependencies += "org.apache.directory.studio" % "org.apache.commons.io" % "2.4"

unmanagedResourceDirectories in Compile += {baseDirectory.value / "lib"}

mainClass in (Compile, run) := Some("com.quadstingray.javafx.sample.Main")
mainClass in (Compile, packageBin) := Some("com.quadstingray.javafx.sample.Main")
mainClass in assembly := Some("com.quadstingray.javafx.sample.Main")

exportJars := true