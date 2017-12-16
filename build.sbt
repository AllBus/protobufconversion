name := "protobufconversion"

organization in ThisBuild := "com.kos.conversion"
version := "0.1"

scalaVersion := "2.12.4"

publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))

//libraryDependencies += "com.trueaccord.scalapb" %% "compilerplugin" % "0.6.6"
libraryDependencies += "com.trueaccord.scalapb" %% "scalapb-runtime" % "0.6.6" % "provided"
libraryDependencies += "com.typesafe.play" %% "play" % "2.6.9"  % "provided"