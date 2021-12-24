
fun day21a(): Int {
    val (start1, start2) = readInput("day21.txt").lines().map { it.last().code - '0'.code }
    var pos1 = start1
    var pos2 = start2
    var score1 = 0
    var score2 = 0
    var throwCount = 0
    var dice = 0
    fun throwDice(): Int {
        throwCount++
        dice = dice % 100 + 1
        println("throw $throwCount dice: $dice")
        return dice
    }
    fun makeTurn(pos: Int): Int {
        val inc = (throwDice() + throwDice() + throwDice()) % 10
        val newPos = pos + inc
        return if (newPos > 10) newPos - 10 else newPos
    }
    while (true) {
        pos1 = makeTurn(pos1)
        score1 += pos1
        println("Player 1 moves to $pos1 for a total score of $score1")
        if (score1 >= 1000) {
            return score2 * throwCount
        }
        pos2 = makeTurn(pos2)
        score2 += pos2
        println("Player 2 moves to $pos2 for a total score of $score2")
        if (score2 >= 1000) {
            return score1 * throwCount
        }
    }
}


fun main() {
    println(day21a())
}
