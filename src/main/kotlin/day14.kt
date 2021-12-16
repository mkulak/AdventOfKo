fun day14a(): Int {
    val lines = readInput("day14.txt").lines()
    val template = lines.first()
    val rules = lines.drop(2).associate { line ->
        val (left, right) = line.split(" -> ")
        left to right
    }
    val seq = generateSequence(template) { apply(it, rules) }.drop(10).first()
    val frequencies = seq.groupBy { it }.mapValues { it.value.size }.toList()
    val max = frequencies.maxByOrNull { it.second }!!.second
    val min = frequencies.minByOrNull { it.second }!!.second
    return max - min
}

fun apply(template: String, rules: Map<String, String>): String =
    template.windowed(2, 1).fold("") { acc, pair -> acc + pair[0] + rules[pair].orEmpty() } + template.last()

fun main() {
    println(day14a())
}
