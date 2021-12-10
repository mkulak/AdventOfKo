fun day10a(): Int {
    val data = readInput("day10.txt").lines()
    val tokenPrice = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    return data.mapNotNull { getIllegalChar(it) }.sumOf {
        tokenPrice[it] ?: unreachable()
    }
}

fun getIllegalChar(line: String): Char? {
    val tokens = mapOf('[' to ']', '(' to ')', '<' to '>', '{' to '}')
    val stack = ArrayDeque<Char>()
    line.forEach { ch ->
        when {
            ch in tokens.keys -> stack.addFirst(ch)
            stack.isEmpty() -> return ch
            tokens[stack.removeFirst()] != ch -> return ch
        }
    }
    return null
}

fun day10b(): Long {
    val data = readInput("day10.txt").lines()
    val scores = data.mapNotNull { getCompletion(it) }.map { getScore(it) }.sorted()
    return scores[scores.size / 2]
}

fun getCompletion(line: String): List<Char>? {
    val tokens = mapOf('[' to ']', '(' to ')', '<' to '>', '{' to '}')
    val stack = ArrayDeque<Char>()
    line.forEach { ch ->
        when {
            ch in tokens.keys -> stack.addFirst(ch)
            stack.isEmpty() -> return null
            tokens[stack.removeFirst()] != ch -> return null
        }
    }
    return stack.map { tokens[it] ?: unreachable() }.takeIf { it.isNotEmpty() }
}

fun getScore(list: List<Char>): Long {
    val tokenPrice = charArrayOf(')', ']', '}', '>')
    return list.fold(0) {acc, ch -> acc * 5 + tokenPrice.indexOf(ch) + 1 }
}

fun main() {
    println(day10b())
}
