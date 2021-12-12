fun day12a(): Int {
    val graph = HashMap<String, HashSet<String>>()
    readInput("day12.txt").lines().forEach {
        val (start, end) = it.split("-")
        graph.getOrPut(start) { HashSet() } += end
        graph.getOrPut(end) { HashSet() } += start
    }
    data class State(val vertx: String, val smallVisited: Set<String>)
    val states = ArrayDeque<State>()
    states += State("start", emptySet())
    var pathsFound = 0
    while (states.isNotEmpty()) {
        val cur = states.removeFirst()
        if (cur.vertx == "end") {
            pathsFound++
            continue
        }
        for (next in graph[cur.vertx].orEmpty()) {
            if (next.isBig || next !in cur.smallVisited) {
                states += State(next, if (cur.vertx.isBig) cur.smallVisited else cur.smallVisited + cur.vertx)
            }
        }
    }
    return pathsFound
}

val String.isBig get() = this[0].isUpperCase()


fun main() {
    println(day12a())
}
