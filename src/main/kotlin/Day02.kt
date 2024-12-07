import java.io.File

private val path = "src/main/kotlin/inputs/day02.txt"
private val file = File(path).absoluteFile

private fun part1(): Int =
    buildList {
        file.forEachLine {
            val items = it.split(" ").mapNotNull { it.toIntOrNull() }.windowed(2, 1)
            add(allIncreaseOrDecrease(items))
        }
    }.count { it }

private fun part2(): Int {
    var safeCount = 0

    file.forEachLine {
        val items = it.split(" ").mapNotNull { it.toIntOrNull() }

        for (i in items.indices) {
            val tempItems = items.toMutableList()
            if (allIncreaseOrDecrease(tempItems.apply { removeAt(i) }.windowed(2, 1))) {
                safeCount++
                break
            }
        }
    }

    return safeCount
}

private fun allIncreaseOrDecrease(items: List<List<Int>>) =
    items.all { isIncrease(it) } || items.all { isDecrease(it) }

private fun isIncrease(it: List<Int>) = it.last() > it.first() && it.last() - it.first() < 4
private fun isDecrease(it: List<Int>) = it.first() > it.last() && it.first() - it.last() < 4


fun main() {
    println(part1())
    println(part2())
}