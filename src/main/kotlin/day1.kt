fun day1a(): Int {
    val measurements = readInput("day1.txt").lines().map { it.toInt() }
    return findIncreaseCount(measurements)
}

fun day1b(): Int {
    val data = readInput("day1.txt").lines().map { it.toInt() }
    val sliding = data.indices.drop(2).map { i -> data[i - 2] + data[i - 1] + data[i] }
    return findIncreaseCount(sliding)
}

fun findIncreaseCount(data: List<Int>) =
    data.indices.drop(1).count { i -> data[i - 1] < data[i] }

fun main() {
    println(day1b())
}
