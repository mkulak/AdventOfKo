fun day11a(): Int {
    val data = readInput("day11.txt").lines().map { it.map { it.code - '0'.code }.toIntArray() }
    return (1..100).fold(0) { acc, _ -> acc + step(data) }
}

fun day11b(): Int {
    val data = readInput("day11.txt").lines().map { it.map { it.code - '0'.code }.toIntArray() }
    return generateSequence { step(data) }.indexOfFirst { it == data.size * data[0].size } + 1
}

val flash = -1
val max = 9
val neighbours = listOf(-1 to -1, 0 to -1, 1 to -1, -1 to 0, 1 to 0, -1 to 1, 0 to 1, 1 to 1)

fun step(data: List<IntArray>): Int {
    for (y in data.indices) {
        for (x in data[y].indices) {
            inc(data, x, y)
        }
    }
    var flashes = 0
    for (y in data.indices) {
        for (x in data[y].indices) {
            if (data[y][x] == flash) {
                flashes++
                data[y][x] = 0
            }
        }
    }
    return flashes
}

fun inc(data: List<IntArray>, x: Int, y: Int): Unit =
    when {
        y !in data.indices || x !in data[y].indices || data[y][x] == flash -> Unit
        data[y][x] < max -> data[y][x] += 1
        else -> {
            data[y][x] = flash
            neighbours.forEach { (dx, dy) -> inc(data, x + dx, y + dy) }
        }
    }

fun main() {
    println(day11b())
}
