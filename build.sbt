/** Project */
name := "specs2-scalaz-core"

version := "6.0.1"

organization := "org.specs2"

scalaVersion := "2.9.3"

/** Shell */
shellPrompt := { state => System.getProperty("user.name") + "> " }

shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }

/** Dependencies */
resolvers ++= Seq("snapshots-repo" at "http://scala-tools.org/repo-snapshots", 
                  "Local Maven Repository" at "file://c:/Documents and Settings/Eric/.m2/repository")

/** Compilation */
javacOptions ++= Seq("-Xmx1812m", "-Xms512m", "-Xss4m")

javaOptions += "-Xmx2G"

scalacOptions += "-deprecation"

scalacOptions += "-unchecked"

maxErrors := 20 

pollInterval := 1000

/** Console */
initialCommands in console := "import org.specs2._"

// Packaging

// disable publishing the test API jar
//publishArtifact in (Compile, packageDoc) := false

/** Publishing */
credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

publishTo <<= version { v: String =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "content/repositories/snapshots")
  else                             Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { x => false }

pomExtra := (
  <url>http://specs2.org/</url>
  <licenses>
    <license>
      <name>MIT-style</name>
      <url>http://www.opensource.org/licenses/mit-license.php</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>http://github.com/etorreborre/specs2</url>
    <connection>scm:http:http://etorreborre@github.com/etorreborre/specs2.git</connection>
  </scm>
  <developers>
    <developer>
      <id>etorreborre</id>
      <name>Eric Torreborre</name>
      <url>http://etorreborre.blogspot.com/</url>
      </developer>
    </developers>
)
