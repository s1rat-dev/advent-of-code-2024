import java.io.File

private val path = "src/main/kotlin/inputs/day05.txt"
private val file = File(path).absoluteFile
private val rules = file.readLines().subList(0, 1176).groupBy { it.split("|").first() }
    .mapValues { it -> it.value.joinToString { it.takeLastWhile { it != '|' } } }
private val tests = file.readLines().subList(1177, file.readLines().size)


private fun part1(): Int =
    tests.map { it.split(",") }.filter { testData ->
        testData.windowed(2, 1).all { rules[it.first()]?.contains(it.last()) ?: false }
    }.sumOf { it[it.size / 2].toInt() }


private fun part2(): Int =
    (tests.map { it.split(",") }.filterNot { testData ->
        testData.windowed(2, 1).all { rules[it.first()]?.contains(it.last()) ?: false }
    }.map { td ->
        val list = mutableListOf<String>()
        val tempList = td.toMutableList()

        while (list.size != td.size) {
            for (item in tempList) {
                if ((tempList - item).all { rules[item]?.contains(it) ?: false }) {
                    list.add(item)
                }
            }
            tempList.removeAll(list)
        }

        return@map list
    }).sumOf { it[it.size / 2].toInt() }


fun main() {
    println(part1())
    println(part2())
}