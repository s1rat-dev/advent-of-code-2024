import java.io.File

private val path = "src/main/kotlin/inputs/day04.txt"
private val file = File(path).absoluteFile

private fun part1(): Int {
    val letters = buildList {
        file.forEachLine {
            add(it)
        }
    }
    val width = letters.first().length
    val height = letters.size
    var count = 0

    fun checkIsXMAS(i: Int, j: Int, rule: Triple<Char, Char, Char>) {
        if (j + 3 < width && (letters[i][j + 1] == rule.first && letters[i][j + 2] == rule.second && letters[i][j + 3] == rule.third)) count++
        if (i + 3 < height && (letters[i + 1][j] == rule.first && letters[i + 2][j] == rule.second && letters[i + 3][j] == rule.third)) count++

        if (j + 3 < width && i + 3 < height && (letters[i + 1][j + 1] == rule.first && letters[i + 2][j + 2] == rule.second && letters[i + 3][j + 3] == rule.third)) count++
        if (j - 3 >= 0 && i + 3 < height && (letters[i + 1][j - 1] == rule.first && letters[i + 2][j - 2] == rule.second && letters[i + 3][j - 3] == rule.third)) count++
    }


    for (i in letters.indices) {
        for (j in letters[i].indices) {
            if (letters[i][j] == 'X') {
                checkIsXMAS(i, j, Triple('M', 'A', 'S'))
            }

            if (letters[i][j] == 'S') {
                checkIsXMAS(i, j, Triple('A', 'M', 'S'))
            }
        }
    }

    return count
}


private fun part2(): Int {
    val letters = buildList {
        file.forEachLine {
            add(it)
        }
    }
    val width = letters.first().length
    val height = letters.size
    var count = 0

    fun checkMAS(i: Int, j: Int, indexes: List<Pair<Int, Int>>, rule: Pair<Char, Char>) =
        letters[i + indexes.first().first][j + indexes.first().second] == rule.first && letters[i + indexes.last().first][j + indexes.last().second] == rule.second

    fun checkLeftMAS(i: Int, j: Int) =
        checkMAS(i, j, listOf(1 to 1, -1 to -1), 'M' to 'S') || checkMAS(i, j, listOf(1 to 1, -1 to -1), 'S' to 'M')

    fun checkRightMAS(i: Int, j: Int) =
        checkMAS(i, j, listOf(-1 to 1, 1 to -1), 'M' to 'S') || checkMAS(i, j, listOf(-1 to 1, 1 to -1), 'S' to 'M')


    for (i in letters.indices) {
        for (j in letters[i].indices) {
            if (j + 1 < width && i + 1 < height && j - 1 >= 0 && i - 1 >= 0 && letters[i][j] == 'A') {
                if (checkLeftMAS(i, j) && checkRightMAS(i, j))
                    count++
            }
        }
    }

    return count
}


fun main() {
    println(part1())
    println(part2())
}