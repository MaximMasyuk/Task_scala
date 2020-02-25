package itchart.scala.core
import java.io.File
final case class Config(foo: String = "Task.csv",
                  out: File = new File("."),
                  xyz: Boolean = false,
                  libName: String = "",
                  maxCount: Int = -1,
                  verbose: Boolean = false,
                  debug: Boolean = false,
                  mode: String = "",
                  files: Seq[File] = Seq(),
                  keepAlive: Boolean = false,
                  jars: Seq[File] = Seq(),
                  kwargs: Map[String, String] = Map())
