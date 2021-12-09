fun day6b(): Long {
    val data = readInput("day6.txt").trim().split(",").map { it.toInt() }
    val fish = LongArray(9)
    data.forEach {
        fish[it] = fish[it] + 1
    }
    repeat(256) {
        tick(fish)
    }
    return fish.sum()
}

fun tick(fish: LongArray) {
    val zeros = fish[0]
    (0..7).forEach {
        fish[it] = fish[it + 1]
    }
    fish[8] = zeros
    fish[6] += fish[6] + zeros
}

fun main() {
    println(day6b())
}
