import java.util.PriorityQueue

fun day9a(): Int {
    val data = readInput("day09.txt").lines().map { line ->
        line.map { it.code - '0'.code }
    }
    var risk = 0
    val neighbours = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
    data.indices.forEach { y ->
        data[y].indices.forEach { x ->
            val values = neighbours.mapNotNull { (nx, ny) -> get(data, x + nx, y + ny) }
            val critical = values.all { it > data[y][x] }
            if (critical) {
                risk += data[y][x] + 1
            }
        }
    }
    return risk
}

fun day9b(): Int {
    val data = readInput("day09.txt").lines().map { line ->
        IntArray(line.length) { line[it].code - '0'.code }
    }

    val visited = -1
    val max = 9
    val neighbours = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)

    fun get(x: Int, y: Int): Int? =
        if (y in data.indices && x in data[y].indices) data[y][x] else null

    fun basinSize(x: Int, y: Int): Int {
        val v = get(x, y)
        if (v == null || v == visited || v == max) return 0
        data[y][x] = visited
        return neighbours.fold(1) { acc, (nx, ny) -> acc + basinSize(x + nx, y + ny) }
    }

    val basinSizes = PriorityQueue<Int>()
    for (y in data.indices) {
        for (x in data[y].indices) {
            val size = basinSize(x, y)
            basinSizes += size
            while (basinSizes.size > 3) {
                basinSizes.poll()
            }
        }
    }
    return basinSizes.fold(1) { acc, v -> acc * v }
}

fun get(data: List<List<Int>>, x: Int, y: Int): Int? =
    if (y in data.indices && x in data[y].indices) data[y][x] else null

fun main() {
    println(day9b())
}
