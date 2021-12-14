import kotlin.math.absoluteValue

fun day7b(): Int {
    val data = readInput("day07.txt").trim().split(",").map { it.toInt() }
    val min = data.minOrNull()!!
    val max = data.maxOrNull()!!
    return (min..max).minOf { calcCost(data, it) }
}

fun calcCost(data: List<Int>, value: Int): Int =
    data.sumOf { elem ->
        val steps = (elem - value).absoluteValue
        steps * (steps + 1) / 2
    }

fun main() {
    println(day7b())
}
