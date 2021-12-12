fun day8b(): Long {
    val data = readInput("day8.txt").lines().map {
        val (input, output) = it.trim().split(" | ")
        parse(input) to parse(output)
    }
    return data.sumOf { (input, output) ->
        val table = solve(input)
        output.fold(0L) { acc, value ->
            val index = table.indexOf(value)
            if (index == -1) unreachable()
            acc * 10 + index
        }
    }
}

fun parse(s: String) = s.trim().split(" ").map { it.toSet() }

fun solve(input: List<Set<Char>>): Array<Set<Char>> {
    val table = Array(10) { emptySet<Char>() }
    table[1] = input.single { it.size == 2 }
    table[4] = input.single { it.size == 4 }
    table[7] = input.single { it.size == 3 }
    table[8] = input.single { it.size == 7 }
    table[3] = input.single { it.size == 5 && it.containsAll(table[1]) }
    table[5] = input.single { it.size == 5 && it.containsAll(table[4] - table[1]) }
    table[2] = input.single { it.size == 5 && it != table[3] && it != table[5] }
    table[0] = input.single { it.size == 6 && it.containsAll(table[2] - table[5]) }
    table[9] = input.single { it.size == 6 && (table[4] - it).isEmpty() }
    table[6] = input.single { it.size == 6 && it != table[0] && it != table[9] }
    return table
}

fun main() {
    println(day8b())
}
