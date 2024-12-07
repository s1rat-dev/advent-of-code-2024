import java.io.File
import kotlin.math.abs

val path = "src/main/kotlin/inputs/day01.txt"
val file = File(path).absoluteFile


fun part1(): Int {
    val firstList = mutableListOf<Int>()
    val secondList = mutableListOf<Int>()

    file.forEachLine {
        val items = it.split(" ").filterNot { it.isBlank() }

        firstList.add(items[0].toInt())
        secondList.add(items[1].toInt())
    }

    firstList.sort()
    secondList.sort()

    return firstList.mapIndexed { index, value ->
        abs(secondList[index] - value)
    }.sum()
}

fun part2(): Int {
    val firstList = mutableListOf<Int>()
    val secondList = mutableListOf<Int>()

    file.forEachLine {
        val items = it.split(" ").mapNotNull { it.toIntOrNull() }

        firstList.add(items[0])
        secondList.add(items[1])
    }

    val firstListMap = firstList.associateWith { 0 }.toMutableMap()

    secondList.forEach { si ->
        firstListMap[si]?.let { firstListMap[si] = it + 1 }
    }

    return firstList.sumOf { it * firstListMap[it]!! }
}


fun main() {
    println(part1())
    println(part2())
}
