import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.sign

data class Segment(val startX: Int, val startY: Int, val endX: Int, val endY: Int)

fun day5b(): Int {
    val regexp = "(\\d+),(\\d+) -> (\\d+),(\\d+)".toRegex()
    val data = readInput("day5.txt").lines().map { line ->
        val (_, s1, s2, s3, s4) = regexp.find(line)!!.groupValues
        Segment(s1.toInt(), s2.toInt(), s3.toInt(), s4.toInt())
    }
    val size = 1000
    val field = ShortArray(size * size)
    data.forEach {
        markSegment(field, size, it)
    }
    return field.count { it > 1 }
}

fun markSegment(field: ShortArray, size: Int, segment: Segment) {
    val diffX = segment.endX - segment.startX
    val diffY = segment.endY - segment.startY
    val length = max(diffX.absoluteValue, diffY.absoluteValue) + 1
    repeat(length) {
        val x = segment.startX + diffX.sign * it
        val y = segment.startY + diffY.sign * it
        field[y * size + x]++
    }
}

fun main() {
    println(day5b())
}
