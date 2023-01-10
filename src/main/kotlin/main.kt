import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.rows
import org.jetbrains.kotlinx.dataframe.io.readTSV

fun main() {

  val file = FileSystem.RESOURCES.source("iban_data/registry.txt".toPath())

  val df = DataFrame.readTSV(file.buffer().inputStream())

  println(df.columnNames())

}
