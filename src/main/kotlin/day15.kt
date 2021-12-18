import java.util.PriorityQueue

fun day15a(): Int {
    val lines = readInput("day15.txt").lines()
    val data = lines.flatMap { line -> line.map { (it.code - '0'.code).toByte() } }.toByteArray()
    val size = lines[0].length
    val target = data.size - 1

    fun neighbours(pos: Int): List<Int> = buildList {
        val x = pos % size
        val y = pos / size
        if (x > 0) add(pos - 1)
        if (x < size - 1) add(pos + 1)
        if (y > 0) add(pos - size)
        if (y < size - 1) add(pos + size)
    }

    val minCostFound = IntArray(data.size) { Int.MAX_VALUE }
    val comparator = compareBy<State> { (size - it.pos % size) + (size - it.pos / size) + it.cost }
    val queue = PriorityQueue(comparator)
    queue += State(0, -data[0], BooleanArray(data.size) { it == 0 })

    while (queue.isNotEmpty()) {
        val state = queue.poll()
        val newCost = state.cost + data[state.pos]
        if (newCost >= minCostFound[state.pos]) {
            continue
        }
        minCostFound[state.pos] = newCost
        if (state.pos == target) {
            println("new minPath: $newCost")
            continue
        }
        val newVisited = state.visited.copyOf()
        neighbours(state.pos).forEach {
            if (!state.visited[it]) {
                newVisited[it] = true
                queue += State(it, newCost, newVisited)
            }
        }
    }
    return minCostFound.last()
}

data class State(val pos: Int, val cost: Int, val visited: BooleanArray)

fun main() {
    println(day15a())
}