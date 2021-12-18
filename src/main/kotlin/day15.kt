import java.util.PriorityQueue
import kotlin.math.absoluteValue

fun day15a(): Int {
    val data = readInput("day15.txt").lines().map { line ->
        line.map { it.code - '0'.code }.toIntArray()
    }
    val target = XY(data[0].size - 1, data.size - 1)
    fun XY.neighbours() = listOf(XY(x - 1, y), XY(x + 1, y), XY(x, y - 1), XY(x, y + 1))
    fun XY.isValid() = x in data[0].indices && y in data.indices

    data class State(val pos: XY, val cost: Int, val visited: Set<XY>)

    var minPath = Int.MAX_VALUE
    val comparator = compareBy<State> { (target.x - it.pos.x) + (target.y - it.pos.y) }
    val queue = PriorityQueue(comparator)
    queue += State(XY(0, 0), -data[0][0], emptySet())
    while (queue.isNotEmpty()) {
        val state = queue.poll()
        println(queue.size)
        val newCost = state.cost + data[state.pos.y][state.pos.x]
        if (newCost >= minPath) {
            continue
        }
        if (state.pos == target) {
            println("new minPath: $newCost")
            minPath = newCost
            continue
//            return newCost
        }
        val newVisited = state.visited + state.pos
        state.pos.neighbours().forEach {
            if (it.isValid() && it !in newVisited) {
                queue += State(it, newCost, newVisited)
            }
        }
    }
    return minPath
}

data class XY(val x: Int, val y: Int)

fun main() {
    println(day15a())
}