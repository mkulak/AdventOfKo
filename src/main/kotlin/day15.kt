fun day15a(): Int {
    val data = readInput("day15.txt").lines().map { line ->
        line.map { it.code - '0'.code }.toIntArray()
    }
    println(data.joinToString("\n") { it.contentToString() })
    val target = XY(data[0].size - 1, data.size - 1)
    fun XY.neighbours(): List<XY> =
        listOf(XY(x - 1, y), XY(x + 1, y), XY(x, y - 1), XY(x, y + 1)).filter {
            it.x in data[0].indices && it.y in data.indices
        }

    fun find(cur: XY, visited: Set<XY>): Int? {
        println("cur: $cur visited: ${visited.size}")
        val cost = data[cur.y][cur.x]
        if (cur == target) return cost
        if (cur in visited) return null
        val nextVisited = visited + cur
        val minCost = cur.neighbours().mapNotNull { find(it, nextVisited) }.minOrNull() ?: return null
        return cost + minCost
    }
    return find(XY(0, 0), emptySet())!!
}



data class XY(val x: Int, val y: Int)


fun main() {
    println(day15a())
}