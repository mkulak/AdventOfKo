fun day13b() {
    val lines = readInput("day13.txt").lines()
    val dots = lines.takeWhile { it.isNotEmpty() }.map {
        val (x, y) = it.split(",")
        x.toInt() to y.toInt()
    }
    val folds = lines.drop(dots.size + 1).map {
        (it[11] == 'y') to it.drop(13).toInt()
    }
    val maxX = dots.maxByOrNull { it.first }!!.first + 1
    val maxY = dots.maxByOrNull { it.second }!!.second + 1
    val data = Array(maxY) { BooleanArray(maxX) }
    dots.forEach { (x, y) -> data[y][x] = true }
    val afterFold = folds.fold(data, ::fold)
    printField(afterFold)
}

fun fold(data: Array<BooleanArray>, f: Pair<Boolean, Int>): Array<BooleanArray> {
    val (left, num) = f
    val (rx, ry) = if (left) data[0].size to num else num to data.size
    val res = Array(ry) { BooleanArray(rx) }
    res.indices.forEach { y ->
        res[y].indices.forEach { x ->
            val (ox, oy) = if (left) x to num * 2 - y else num * 2 - x to y
            res[y][x] = data[y][x] || data[oy][ox]
        }
    }
    return res
}

private fun printField(data: Array<BooleanArray>) {
    data.forEach { line ->
        line.forEach {
            print(if (it) '#' else '.')
        }
        println()
    }
}

fun main() {
    day13b()
}