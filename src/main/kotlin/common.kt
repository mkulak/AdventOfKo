import java.lang.RuntimeException

fun readInput(path: String) =
    Thread.currentThread().contextClassLoader.getResource(path)!!.readText()

inline fun unreachable(): Nothing = throw RuntimeException("Should not reach here")
