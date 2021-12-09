fun day4a(): Int {
    val data = readInput("day4.txt").lines()
    val numbers = data[0].split(",").map { it.toInt() }
    val boards = data.drop(2).windowed(6, 6, true).map { lines ->
        lines.dropLast(lines.size - 5).flatMap { it.trim().split("\\s+".toRegex()).map { it.toInt() } }.toIntArray()
    }
    numbers.forEach { number ->
        boards.forEach { board ->
            val isComplete = markNumber(board, number)
            if (isComplete) {
                return board.filter { it >= 0 }.sum() * number
            }
        }
    }
    unreachable()
}

fun day4b(): Int {
    val data = readInput("day4.txt").lines()
    val numbers = data[0].split(",").map { it.toInt() }
    var boards = data.drop(2).windowed(6, 6, true).map { lines ->
        lines.dropLast(lines.size - 5).flatMap { it.trim().split("\\s+".toRegex()).map { it.toInt() } }.toIntArray()
    }
    numbers.forEach { number ->
        val (complete, incomplete) = boards.partition { board -> markNumber(board, number) }
        if (incomplete.isEmpty()) {
            return complete[0].filter { it >= 0 }.sum() * number
        }
        boards = incomplete
    }
    unreachable()
}

fun markNumber(board: IntArray, number: Int): Boolean {
    val index = board.indexOfFirst { it == number }
    if (index == -1) return false
    board[index] = -1
    val horizontalFull = (0..4).map { board[it + index - (index % 5)] }.all { it == -1 }
    val verticalFull = (0..4).map { board[it * 5 + index % 5] }.all { it == -1 }
    return horizontalFull || verticalFull
}

fun main() {
    println(day4b())
}
