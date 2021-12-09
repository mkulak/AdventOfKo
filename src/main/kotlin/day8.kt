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

fun parse(s: String) = s.trim().split(" ").map { it.toCharArray().sorted().joinToString("") }

fun solve(input: List<String>): Array<String> {
    val table = Array(10) { "" }
    table[1] = input.single { it.length == 2 }
    table[4] = input.single { it.length == 4 }
    table[7] = input.single { it.length == 3 }
    table[8] = input.single { it.length == 7 }

    val fives = input.filter { it.length == 5 }
    val cf = table[1]
    val db = table[4].filter { it !in table[1] }
    table[3] = fives.single { cf[0] in it && cf[1] in it }
    table[5] = fives.single { db[0] in it && db[1] in it }
    table[2] = fives.single { it != table[3] && it != table[5] }

    val sixes = input.filter { it.length == 6 }
    val ce = table[2].filter { it !in table[5] }
    val c = ce.single { it in cf }
    table[0] = sixes.single { ce[0] in it && ce[1] in it }
    table[9] = sixes.single { c in it && it != table[0] }
    table[6] = sixes.single { it != table[0] && it != table[9] }
    return table
}

fun main() {
    println(day8b())
}
