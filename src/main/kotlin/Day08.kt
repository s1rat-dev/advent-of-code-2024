import java.io.File

private val path = "src/main/kotlin/inputs/day08.txt"
private val file = File(path).absoluteFile
private const val RANGE = 49

private fun part1(): Int {
    val antennas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

    file.readLines().forEachIndexed { index, line ->
        line.forEachIndexed { lIndex, c ->
            if (c != '.') {
                antennas[c]?.add(lIndex to index) ?: antennas.put(c, mutableListOf(lIndex to index))
            }
        }
    }

    val antiNodes = buildList {
        antennas.values.forEach { coordinates ->
            for (i in 0 until coordinates.size) {
                coordinates.drop(i + 1).forEach { coordinate ->
                    val prevItem = coordinates[i].first + (coordinates[i].first - coordinate.first) to coordinates[i].second + (coordinates[i].second - coordinate.second)
                    val nextItem = coordinate.first + (coordinate.first - coordinates[i].first) to coordinate.second + (coordinate.second - coordinates[i].second)

                    if (prevItem.first in 0..RANGE && prevItem.second in 0..RANGE) {
                        add(prevItem)
                    }

                    if (nextItem.first in 0..RANGE && nextItem.second in 0..RANGE) {
                        add(nextItem)
                    }
                }
            }
        }
    }

    return antiNodes.distinct().size
}

private fun part2(): Int {
    val antennas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
    val nodes = mutableListOf<Pair<Int, Int>>()

    file.readLines().forEachIndexed { index, line ->
        line.forEachIndexed { lIndex, c ->
            if (c != '.') {
                antennas[c]?.add(lIndex to index) ?: antennas.put(c, mutableListOf(lIndex to index))
                nodes.add(lIndex to index)
            }
        }
    }

    val antiNodes = buildList {
        antennas.values.forEach { coordinates ->
            for (i in 0 until coordinates.size) {
                coordinates.drop(i + 1).forEach { coordinate ->
                    var prevItem = coordinates[i].first + (coordinates[i].first - coordinate.first) to coordinates[i].second + (coordinates[i].second - coordinate.second)
                    var nextItem = coordinate.first + (coordinate.first - coordinates[i].first) to coordinate.second + (coordinate.second - coordinates[i].second)

                    while (prevItem.first in 0..RANGE && prevItem.second in 0..RANGE) {
                        add(prevItem)
                        prevItem = prevItem.first + (coordinates[i].first - coordinate.first) to prevItem.second + (coordinates[i].second - coordinate.second)
                    }

                    while (nextItem.first in 0..RANGE && nextItem.second in 0..RANGE) {
                        add(nextItem)
                        nextItem = nextItem.first + (coordinate.first - coordinates[i].first) to nextItem.second + (coordinate.second - coordinates[i].second)
                    }
                }
            }
        }
    }

    return (antiNodes + nodes).distinct().size
}

fun main() {
    println(part1())
    println(part2())
}
