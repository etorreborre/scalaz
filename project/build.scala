import collection.immutable.IndexedSeq
import java.awt.Desktop
import sbt._
import Keys._
import GenTypeClass._
import Project.Setting
import com.typesafe.sbtosgi.OsgiPlugin._

object build extends Build {
  type Sett = Project.Setting[_]

  lazy val standardSettings: Seq[Sett] = Defaults.defaultSettings ++ Seq[Sett](
    organization := "org.specs2",
    scalaVersion := "2.10.0-M7",
    version      := "7.0-SNAPSHOT",
    crossVersion := CrossVersion.full,
    name         := "scalaz",
    resolvers += "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases",
    scalacOptions <++= (scalaVersion).map((sv: String) => Seq("-deprecation", "-unchecked") ++ (if(sv.contains("2.10")) None else Some("-Ydependent-method-types"))),
    scalacOptions in (Compile, doc) <++= (baseDirectory in LocalProject("scalaz")).map {
      bd => Seq("-sourcepath", bd.getAbsolutePath, "-doc-source-url", "https://github.com/scalaz/scalaz/tree/scalaz-seven€{FILE_PATH}.scala")
    },
    (unmanagedClasspath in Compile) += Attributed.blank(file("dummy")),

    genTypeClasses <<= (scalaSource in Compile, streams, typeClasses) map {
      (scalaSource, streams, typeClasses) =>
        typeClasses.flatMap { tc => typeclassSource(tc).sources.map(_.createOrUpdate(scalaSource, streams.log)) }
    },
    typeClasses := Seq(),
    genToSyntax <<= typeClasses map { tcs: Seq[TypeClass] =>
      val objects = tcs.map(tc => "object %s extends To%sSyntax".format(Util.initLower(tc.name), tc.name)).mkString("\n")
      val all = "object all extends " + tcs.map(tc => "To%sSyntax".format(tc.name)).mkString(" with ")
      objects + "\n\n" + all
    },
    typeClassTree <<= typeClasses map { _.map(_.doc).mkString("\n") },

    showDoc in Compile <<= (doc in Compile, target in doc in Compile) map { (_, out) =>
      val index = out / "index.html"
      if (index.exists()) Desktop.getDesktop.open(out / "index.html")
    },
    credentials += Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", "etorreborre", "kela22"), //Path.userHome / ".ivy2" / ".credentials"),
    publishTo <<= version { v: String =>
      val nexus = "https://oss.sonatype.org/"
      if (v.trim.endsWith("SNAPSHOT")) Some("sonatype-snapshots" at nexus + "content/repositories/snapshots")
      else                             Some("sonatype-staging" at nexus   + "service/local/staging/deploy/maven2")
    },
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := { x => false },
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
))

  lazy val `specs2-scalaz` = Project(
    id = "specs2-scalaz",
    base = file("."),
    settings = standardSettings ++ Unidoc.settings,
    aggregate = Seq(core, concurrent, effect, xml)
  )

  lazy val scalaz = Project(
    id = "scalaz",
    base = file("."),
    settings = standardSettings ++ Unidoc.settings,
    aggregate = Seq(core, concurrent, effect, example, iterv, iteratee, scalacheckBinding, tests, typelevel, xml)
  )

  lazy val core = Project(
    id = "core",
    base = file("core"),
    settings = standardSettings ++ Seq[Sett](
      name := "scalaz-core",
      organization := "org.specs2",
      typeClasses := TypeClass.core,
      credentials += Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", "etorreborre", "kela22"), //Path.userHome / ".ivy2" / ".credentials"),
      (sourceGenerators in Compile) <+= (sourceManaged in Compile) map {
        dir => Seq(generateTupleW(dir))
      }
    )
  )

  lazy val concurrent = Project(
    id = "concurrent",
    base = file("concurrent"),
    settings = standardSettings ++ Seq[Sett](
      name := "scalaz-concurrent",
      typeClasses := TypeClass.concurrent
    ),
    dependencies = Seq(core, effect)
  )

  lazy val effect = Project(
    id = "effect",
    base = file("effect"),
    settings = standardSettings ++ Seq[Sett](
      name := "scalaz-effect",
      typeClasses := TypeClass.effect
    ),
    dependencies = Seq(core)
  )

  lazy val iteratee = Project(
    id = "iteratee",
    base = file("iteratee"),
    settings = standardSettings ++ Seq[Sett](
      name := "scalaz-iteratee"
    ),
    dependencies = Seq(effect)
  )

  lazy val iterv = Project(
    id = "iterv",
    base = file("iterv"),
    settings = standardSettings ++ Seq[Sett](
      name := "scalaz-iterv"
    ),
    dependencies = Seq(effect)
  )

  lazy val typelevel = Project(
    id = "typelevel",
    base = file("typelevel"),
    settings = standardSettings ++ Seq[Sett](
      name := "scalaz-typelevel"
    ),
    dependencies = Seq(core)
  )

  lazy val xml = Project(
    id = "xml",
    base = file("xml"),
    settings = standardSettings ++ Seq[Sett](
      name := "scalaz-xml",
      typeClasses := TypeClass.xml
    ),
    dependencies = Seq(core)
  )

  lazy val example = Project(
    id = "example",
    base = file("example"),
    dependencies = Seq(core, iteratee, concurrent, typelevel, xml),
    settings = standardSettings ++ Seq[Sett](
      name := "scalaz-example"
    )
  )

  lazy val scalacheckBinding = Project(
    id           = "scalacheck-binding",
    base         = file("scalacheck-binding"),
    dependencies = Seq(core, concurrent, typelevel),
    settings     = standardSettings ++ Seq[Sett](
      name := "scalaz-scalacheck-binding",
      libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.10.0" cross CrossVersion.full
    )
  )

  lazy val tests = Project(
    id = "tests",
    base = file("tests"),
    dependencies = Seq(core, iteratee, concurrent, effect, typelevel, scalacheckBinding % "test"),
    settings = standardSettings ++Seq[Sett](
      name := "scalaz-tests",
      libraryDependencies ++= Seq(
        "org.specs2" %% "specs2" % "1.12.1.1" % "test" cross CrossVersion.full,
        "org.scalacheck" %% "scalacheck" % "1.10.0" % "test" cross CrossVersion.full
      )
    )
  )

  lazy val publishSetting = publishTo <<= (version).apply{
    v =>
      val nexus = "https://oss.sonatype.org/"
      if (v.trim.endsWith("SNAPSHOT"))
        Some("sonatype-snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("sonatype-staging" at nexus + "service/local/staging/deploy/maven2")
  }

  lazy val credentialsSetting = credentials += {
    Seq("build.publish.user", "build.publish.password").map(k => Option(System.getProperty(k))) match {
      case Seq(Some(user), Some(pass)) => 
        println("credentials are "+(user, pass))
        Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", user, pass)
      case _                           =>
        println("no credentials given. Pass the build.publish.user and build.publish.password system properties or use the ~/.ivy2/.credentials file")
        Credentials(Path.userHome / ".ivy2" / ".credentials")
    }
  }

  lazy val genTypeClasses = TaskKey[Seq[File]]("gen-type-classes")

  lazy val typeClasses = TaskKey[Seq[TypeClass]]("type-classes")

  lazy val genToSyntax = TaskKey[String]("gen-to-syntax")

  lazy val showDoc = TaskKey[Unit]("show-doc")

  lazy val typeClassTree = TaskKey[String]("type-class-tree", "Generates scaladoc formatted tree of type classes.")

  def generateTupleW(outputDir: File) = {
    val arities = 2 to 12

    def writeFileScalazPackage(fileName: String, source: String): File = {
      val file = (outputDir / "scalaz" / "syntax" / "std" / fileName).asFile
      IO.write(file, source)
      file
    }

    def double(s: String) = s + s

    val tuples: IndexedSeq[(String, String)] = for (arity: Int <- arities) yield {
      case class N(n: Int) {
        val alpha: String = ('A' + (n - 1)).toChar.toString
        val alpha2: String = alpha + alpha
        val element: String = "_" + n
      }
      val ns = (1 to arity) map N.apply
      def mapMkString(f: N => String): String = ns.map(f).mkString(", ")

      val tparams = mapMkString {
        n => n.alpha
      }
      val params = mapMkString {
        n => n.element
      }

      val ztparams = mapMkString {
        _ => "Z"
      }

      val mapallTParams = mapMkString {
        n => n.alpha2
      }
      val mapallParams = mapMkString {
        n => "%s: (%s => %s) = identity[%s] _".format(n.element, n.alpha, n.alpha2, n.alpha)
      }
      val mapallApply = mapMkString {
        n => "%s(value.%s)".format(n.element, n.element)
      }

      val pimp = """|
          |trait Tuple%dOps[%s] extends Ops[Tuple%d[%s]] {
          |  val value = self
          |  def fold[Z](f: => (%s) => Z): Z = {import value._; f(%s)}
          |  def toIndexedSeq[Z](implicit ev: value.type <:< Tuple%d[%s]): IndexedSeq[Z] = {val zs = ev(value); import zs._; IndexedSeq(%s)}
          |  def mapElements[%s](%s): (%s) = (%s)
          |}""".stripMargin.format(arity, tparams, arity, tparams, tparams, params, arity,
        ztparams, params,
        mapallTParams, mapallParams, mapallTParams, mapallApply
      )

      val conv = """implicit def ToTuple%dOps[%s](t: (%s)): Tuple%dOps[%s] = new { val self = t } with Tuple%dOps[%s]
          |""".stripMargin.format(arity, tparams, tparams, arity, tparams, arity, tparams)
      (pimp, conv)
    }

    val source = "package org.specs2.internal.scalaz\npackage syntax\npackage std\n\n" +
      tuples.map(_._1).mkString("\n") +
      "\n\ntrait ToTupleOps {\n" +
         tuples.map("  " + _._2).mkString("\n") +
      "}"
    writeFileScalazPackage("TupleOps.scala", source)
  }

  def osgiExport(packs: String*) = OsgiKeys.exportPackage := packs.map(_ + ".*;version=${Bundle-Version}")
}
