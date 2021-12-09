import kotlin.math.absoluteValue
import kotlin.math.min

fun day7b(): Int {
    val data = readInput("day7.txt").trim().split(",").map { it.toInt() }
    val min = data.minOrNull()!!
    val max = data.maxOrNull()!!
    return (min..max).fold(Int.MAX_VALUE) { acc, value ->
        val cost = calcCost(data, value)
        min(cost, acc)
    }
}

fun calcCost(data: List<Int>, value: Int): Int =
    data.sumOf { elem ->
        val steps = (elem - value).absoluteValue
        steps * (steps + 1) / 2
    }

fun main() {
    println(day7b())
}
