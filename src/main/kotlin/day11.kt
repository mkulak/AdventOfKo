fun day11a(): Int {
    val data = readInput("day11.txt").lines().flatMap { it.map { it.code - '0'.code } }.toIntArray()
    return (1..100).sumOf { step(data) }
}

fun day11b(): Int {
    val data = readInput("day11.txt").lines().flatMap { it.map { it.code - '0'.code } }.toIntArray()
    return generateSequence { step(data) }.indexOfFirst { it == data.size } + 1
}

val size = 10
val flash = -1
val max = 9

fun step(data: IntArray): Int {
    data.indices.forEach { i -> inc(data, i) }
    var flashes = 0
    data.indices.forEach { i ->
        if (data[i] == flash) {
            flashes++
            data[i] = 0
        }
    }
    return flashes
}

fun inc(data: IntArray, i: Int): Unit =
    when {
        data[i] == flash -> Unit
        data[i] < max -> data[i] += 1
        else -> {
            data[i] = flash
            neighbours(i).forEach { inc(data, it) }
        }
    }

fun neighbours(i: Int) =
    (i / size - 1..i / size + 1).flatMap { ny ->
        (i % size - 1..i % size + 1).mapNotNull { nx ->
            (ny * size + nx).takeIf { ny in 0 until size && nx in 0 until size && it != i }
        }
    }

fun main() {
    println(day11b())
}
