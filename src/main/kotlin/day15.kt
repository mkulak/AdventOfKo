import java.util.PriorityQueue

fun day15a(): Int {
    val lines = readInput("day15.txt").lines()
    val data = lines.flatMap { line -> line.map { (it.code - '0'.code).toByte() } }.toByteArray()
    val size = lines[0].length
    val target = data.size - 1

    val minCostFound = IntArray(data.size) { Int.MAX_VALUE }
    val comparator = compareBy<State> { (size - it.pos % size) + (size - it.pos / size) }
    val queue = PriorityQueue(comparator)
    queue += State(0, -data[0], BooleanArray(data.size))
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
        newVisited[state.pos] = true
        val x = state.pos % size
        val y = state.pos / size
        if (x > 0) queue.addState(state.pos - 1, newCost, state, newVisited)
        if (x < size - 1) queue.addState(state.pos + 1, newCost, state, newVisited)
        if (y > 0) queue.addState(state.pos - size, newCost, state, newVisited)
        if (y < size - 1) queue.addState(state.pos + size, newCost, state, newVisited)
    }
    return minCostFound.last()
}

data class State(val pos: Int, val cost: Int, val visited: BooleanArray)

fun PriorityQueue<State>.addState(pos: Int, newCost: Int, state: State, newVisited: BooleanArray) {
    if (!state.visited[pos]) {
        newVisited[pos] = true
        this += State(pos, newCost, newVisited)
    }
}

fun main() {
    println(day15a())
}