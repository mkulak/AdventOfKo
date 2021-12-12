fun day12a(): Int {
    val graph = HashMap<String, HashSet<String>>()
    readInput("day12.txt").lines().forEach {
        val (start, end) = it.split("-")
        graph.getOrPut(start) { HashSet() } += end
        graph.getOrPut(end) { HashSet() } += start
    }

    fun find(vertx: String, smallVisited: Set<String>): Int {
        if (vertx == "end") {
            return 1
        }
        var paths = 0
        val newVisited = if (vertx.isBig) smallVisited else smallVisited + vertx
        for (next in graph[vertx].orEmpty()) {
            if (next.isBig || next !in smallVisited) {
                paths += find(next, newVisited)
            }
        }
        return paths
    }

    return find("start", emptySet())
}

val String.isBig get() = this[0].isUpperCase()

fun main() {
    println(day12a())
}
