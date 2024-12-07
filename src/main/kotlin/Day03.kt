import java.io.File

private val path = "src/main/kotlin/inputs/day03.txt"
private val file = File(path).absoluteFile

private fun part1(): Int {
    val regex = "mul\\(\\d{1,3},\\d{1,3}\\)".toRegex()
    val fileText = buildString {
        file.forEachLine {
            append(it)
        }
    }

    return regex.findAll(fileText).sumOf {
        it.value
            .replace("mul(", "")
            .replace(")", "")
            .split(",").fold(1) { x, y -> x * y.toInt() }.toInt()
    }
}

private fun part2(): Int {
    val regex = "don't\\(\\)|do\\(\\)|mul\\(\\d{1,3},\\d{1,3}\\)".toRegex()
    val enabledRegex = "do\\(\\)(mul\\(\\d{1,3},\\d{1,3}\\))+".toRegex()
    val fileText = buildString {
        file.forEachLine {
            append(it)
        }
    }

    val filteredText = regex.findAll(fileText).joinToString("") { it.value }

    return (filteredText.takeWhile { it != 'd' } + enabledRegex.findAll(filteredText).joinToString("") { it.value })
        .replace("do()", "")
        .split("mul(")
        .sumOf {
            if (it.isNotEmpty()) {
               it.replace(")", "")
                   .split(",")
                   .fold(1) { x, y -> x * y.toInt() } as Int
            } else { 0 }
        }

}

fun main() {
    println(part1())
    println(part2())
}