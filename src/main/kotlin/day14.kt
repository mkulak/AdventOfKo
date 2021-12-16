fun day14a(): Int {
    val lines = readInput("day14.txt").lines()
    val template = lines.first().trim()
    val rules = lines.drop(2).associate { line ->
        val (left, right) = line.split(" -> ")
        left to right
    }
    val seq = generateSequence(template) { apply(it, rules) }.drop(10).first()
    val frequencies = seq.groupBy { it }.mapValues { it.value.size }
    val max = frequencies.maxByOrNull { it.value }!!
    val min = frequencies.minByOrNull { it.value }!!
    return max.value - min.value
}

fun apply(template: String, rules: Map<String, String>): String =
    template.windowed(2, 1).fold("") { acc, pair -> acc + pair[0] + rules[pair].orEmpty() } + template.last()

fun day14b(): Long {
    val lines = readInput("day14.txt").lines()
    val template = lines.first().trim()
    val templateMap = template.windowed(2, 1).groupBy { it }.mapValues { it.value.size.toLong() }
    val rules = lines.drop(2).associate { line ->
        val (left, right) = line.split(" -> ")
        left to right[0]
    }
    val pairFreq = generateSequence(templateMap) { applySmart(it, rules) }.drop(40).first()
    val frequencies = pairFreq.entries.fold(HashMap<Char, Long>()) { acc, (pair, count) ->
        acc.inc(pair[0], count)
        acc.inc(pair[1], count)
        acc
    }
    frequencies.inc(template.first(), 1)
    frequencies.inc(template.last(), 1)
    val max = frequencies.maxByOrNull { it.value }!!
    val min = frequencies.minByOrNull { it.value }!!
    return (max.value - min.value) / 2
}

fun applySmart(template: Map<String, Long>, rules: Map<String, Char>): Map<String, Long> {
    val res = HashMap<String, Long>()
    template.forEach { (key, count) ->
        val insertion = rules[key]
        if (insertion != null) {
            res.inc("${key[0]}$insertion", count)
            res.inc("$insertion${key[1]}", count)
        } else {
            res[key] = count
        }
    }
    return res
}

fun <A> MutableMap<A, Long>.inc(key: A, count: Long) {
    this[key] = (this[key] ?: 0) + count
}

fun main() {
    println(day14b())
}