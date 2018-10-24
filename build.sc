import mill._, scalalib._
import coursier.maven.MavenRepository

trait CommonModule extends ScalaModule {
  def scalaVersion = "2.11.12"
  def repositories = super.repositories ++ Seq(
    MavenRepository("https://oss.sonatype.org/content/repositories/releases")
  )
  def scalacOptions = Seq(
    "-deprecation",
    "-encoding",
    "UTF-8",
    "-feature",
    "-language:existentials",
    "-language:higherKinds",
    "-Ypartial-unification"
  )
}

object main extends CommonModule {
  override def ivyDeps = Agg(
    D.droste
  )

  object test extends CommonModule {
    override def ivyDeps = Agg(
      D.testzCore,
      D.testzStdlib
    )
  }
}


object D {
  val V = new {
    val droste = "0.5.0"
    val testz = "0.0.5"
  }
  val droste = ivy"io.higherkindness::droste-core:${V.droste}"
  val testzCore = ivy"org.scalaz::testz-core:${V.testz}"
  val testzStdlib = ivy"org.scalaz::testz-stdlib:${V.testz}"
}
