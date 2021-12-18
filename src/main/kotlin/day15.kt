import java.util.PriorityQueue

fun day15a(): Int {
    val lines = readInput("day15.txt").lines()
    val data = lines.flatMap { line -> line.map { (it.code - '0'.code).toByte() } }.toByteArray()
    return findShortestPathCost(data, lines[0].length)
}

fun day15b(): Int {
    val lines = readInput("day15.txt").lines()
    val mul = 5
    val smallData = lines.flatMap { line -> enlarge(line.map { (it.code - '0'.code).toByte() }, mul) }
    val data = enlarge(smallData, mul).toByteArray()
    return findShortestPathCost(data, lines[0].length * mul)
}

fun enlarge(data: List<Byte>, mul: Int): List<Byte> =
    (0 until mul).flatMap { dv -> data.map { ((it - 1 + dv) % 9 + 1).toByte() } }

private fun findShortestPathCost(data: ByteArray, size: Int): Int {
    val minCostFound = IntArray(data.size) { Int.MAX_VALUE }
    val comparator = compareBy<State> { (size - it.pos % size) + (size - it.pos / size) + it.cost }
    val queue = PriorityQueue(comparator)
    queue += State(0, -data[0])
    while (queue.isNotEmpty()) {
        val state = queue.poll()
        val newCost = state.cost + data[state.pos]
        if (newCost >= minCostFound[state.pos]) {
            continue
        }
        minCostFound[state.pos] = newCost
        if (state.pos == data.size - 1) {
            continue
        }
        neighbours(state.pos, size).forEach {
            queue += State(it, newCost)
        }
    }
    return minCostFound.last()
}

data class State(val pos: Int, val cost: Int)

fun neighbours(pos: Int, size: Int): List<Int> = buildList {
    val x = pos % size
    val y = pos / size
    if (x > 0) add(pos - 1)
    if (x < size - 1) add(pos + 1)
    if (y > 0) add(pos - size)
    if (y < size - 1) add(pos + size)
}

fun main() {
    println(day15b())
}
