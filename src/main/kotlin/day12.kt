fun day12a(): Int {
    val graph = HashMap<String, HashSet<String>>()
    readInput("day12.txt").lines().forEach {
        val (start, end) = it.split("-")
        graph.getOrPut(start) { HashSet() } += end
        graph.getOrPut(end) { HashSet() } += start
    }

    fun find(vertx: String, forbidden: Set<String>): Int {
        if (vertx == "end") return 1
        val nextForbidden = if (vertx.isBig) forbidden else forbidden + vertx
        return graph[vertx].orEmpty().filter { it.isBig || it !in forbidden }.sumOf { find(it, nextForbidden) }
    }

    return find("start", emptySet())
}

fun day12b(): Int {
    val graph = HashMap<String, HashSet<String>>()
    readInput("day12.txt").lines().forEach {
        val (start, end) = it.split("-")
        graph.getOrPut(start) { HashSet() } += end
        graph.getOrPut(end) { HashSet() } += start
    }

    fun find(vertx: String, forbidden: Set<String>, hasCheated: Boolean): Int {
        if (vertx == "end") return 1
        val nextForbidden = if (vertx.isBig) forbidden else forbidden + vertx
        return graph[vertx].orEmpty().sumOf {
            when {
                it == "start" -> 0
                it.isBig || it !in forbidden -> find(it, nextForbidden, hasCheated)
                !hasCheated -> find(it, nextForbidden, true)
                else -> 0
            }
        }
    }
    
    return find("start", emptySet(), false)
}

val String.isBig get() = this[0].isUpperCase()

fun main() {
    println(day12b())
}
