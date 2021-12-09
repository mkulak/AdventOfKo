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
    val stepX = (segment.endX - segment.startX).sign
    val stepY = (segment.endY - segment.startY).sign
    var curX = segment.startX
    var curY = segment.startY
    while (curX != segment.endX || curY != segment.endY) {
        inc(field, size, curX, curY)
        curX += stepX
        curY += stepY
    }
    inc(field, size, curX, curY)
}

fun inc(field: ShortArray, size: Int, x: Int, y: Int) {
    val index = y * size + x
    field[index] = (field[index] + 1).toShort()
}

fun main() {
    println(day5b())
}
