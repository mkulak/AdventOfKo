
fun day2b(): Int {
    val data = readInput("day2.txt").lines().map {
        val (cmd, num) = it.split(" ")
        when (cmd) {
            "forward" -> true to num.toInt()
            "down" -> false to num.toInt()
            "up" -> false to -(num.toInt())
            else -> throw RuntimeException("Couldn't parse line: $it")
        }
    }
    var x = 0
    var depth = 0
    var aim = 0
    data.forEach { (isForward, num) ->
        if (isForward) {
            x += num
            depth += num * aim
        } else {
            aim += num
        }
    }
    return x * depth
}

fun main() {
    println(day2b())
}
