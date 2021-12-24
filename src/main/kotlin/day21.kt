
fun day21a(): Int {
    val (pos1, pos2) = readInput("day21.txt").lines().map { it.last().code - '0'.code }
    val pos = intArrayOf(pos1, pos2)
    val scores = intArrayOf(0, 0)
    var turn = 0
    fun makeTurn() {
        val player = turn % 2
        val inc = (((turn * 3) % 100) * 3 + 6) % 10
        val newPos = pos[player] + inc
        pos[player] = if (newPos > 10) newPos - 10 else newPos
        scores[player] += pos[player]
        turn++
    }
    while (true) {
        val player = turn % 2
        makeTurn()
//        println("Player ${player + 1} moves to ${pos[player]} for a total score of ${scores[player]}")
        if (scores[player] >= 1000) {
            return scores[(player + 1) % 2] * turn * 3
        }
    }
}

fun main() {
    println(day21a())
}

