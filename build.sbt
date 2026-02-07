name := "PDSS-Prototype"

version := "1.0"

scalaVersion := "2.12.18"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.5.0",
  "org.apache.spark" %% "spark-sql" % "3.5.0",
  "org.apache.spark" %% "spark-mllib" % "3.5.0",
  // "org.json4s" %% "json4s-jackson" % "3.7.0-M11" 
  "org.json4s" %% "json4s-jackson" % "4.0.6"
)

// Dependency resolution strategy - use Spark's version when conflicts
dependencyOverrides += "org.json4s" %% "json4s-core" % "3.7.0-M11"
dependencyOverrides += "org.json4s" %% "json4s-ast" % "3.7.0-M11"
dependencyOverrides += "org.json4s" %% "json4s-jackson" % "3.7.0-M11"

// Java 17 compatibility fixes for Spark
fork := true

javaOptions ++= Seq(
  "--add-opens=java.base/java.lang=ALL-UNNAMED",
  "--add-opens=java.base/java.lang.invoke=ALL-UNNAMED",
  "--add-opens=java.base/java.lang.reflect=ALL-UNNAMED",
  "--add-opens=java.base/java.io=ALL-UNNAMED",
  "--add-opens=java.base/java.net=ALL-UNNAMED",
  "--add-opens=java.base/java.nio=ALL-UNNAMED",
  "--add-opens=java.base/java.util=ALL-UNNAMED",
  "--add-opens=java.base/java.util.concurrent=ALL-UNNAMED",
  "--add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED",
  "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED",
  "--add-opens=java.base/sun.nio.cs=ALL-UNNAMED",
  "--add-opens=java.base/sun.security.action=ALL-UNNAMED",
  "--add-opens=java.base/sun.util.calendar=ALL-UNNAMED",
  "-Djava.security.manager=allow"
)