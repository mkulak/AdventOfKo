fun day3a(): Int {
    val data = readInput("day03.txt").lines()
    val len = data[0].length
    var gamma = 0
    var epsilon = 0
    (0 until len).forEach { index ->
        val zeroCount = data.count { it[index] == '0' }
        val moreZeros = zeroCount >= data.size / 2
        gamma *= 2
        epsilon *= 2
        if (moreZeros) gamma++ else epsilon++
    }
    return gamma * epsilon
}

fun day3b(): Int {
    val data = readInput("day03.txt").lines()
    val oxygen = find(data, true).single().toInt(2)
    val scrubber = find(data, false).single().toInt(2)
    return oxygen * scrubber
}

fun find(data: List<String>, mostCommon: Boolean): List<String> =
    (0 until data[0].length).fold(data) { curData, i ->
        if (curData.size == 1) return curData
        val (zeros, ones) = curData.partition { it[i] == '0' }
        if (mostCommon == (zeros.size <= ones.size)) ones else zeros
    }

fun main() {
    println(day3b())
}
