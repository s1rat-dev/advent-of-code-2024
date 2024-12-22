import java.io.File

private val path = "src/main/kotlin/inputs/day09.txt"
private val file = File(path).absoluteFile

private fun part1(): Long {
    val data = prepareData()

    val emptyDataIndexes = data.mapIndexedNotNull { index, value ->
        value.takeIf { it == -1 }?.let { index }
    }

    emptyDataIndexes.forEach { value ->
        while (data.last() == -1) data.removeLast()
        if (data.size <= value) return@forEach
        data[value] = data.removeLast()
    }

    return data.withIndex().sumOf { it.index.toLong() * it.value.toLong() }
}

private fun part2(): Long {
    val data = prepareData()

    var i = 0
    val freeDiskAreas = buildList {
        while (i <= data.lastIndex) {
            if (data[i] == -1) {
                val startIndex = i
                var stopIndex = i

                for (k in i..data.lastIndex) {
                    if (data[k] != -1) {
                        stopIndex = k
                        break
                    }
                }

                add(startIndex to stopIndex)
                i = stopIndex
            }

            i++
        }
    }.toMutableList()

    val dataWithId = data.withIndex().filter { it.value != -1 }.groupBy { it.value }.toSortedMap().reversed()

    dataWithId.forEach { dwi ->
        freeDiskAreas.firstOrNull { it.second - it.first >= dwi.value.size && dwi.value.last().index > it.first }
            ?.let { fda ->
                for (k in (fda.first until fda.second).take(dwi.value.size)) data[k] = dwi.key
                dwi.value.forEach { k -> data[k.index] = -1 }

                if (fda.second - fda.first == dwi.value.size) freeDiskAreas.remove(fda)
                else freeDiskAreas[freeDiskAreas.indexOf(fda)] = fda.first + dwi.value.size to fda.second
            }
    }

    return data.withIndex().sumOf {
        if (it.value != -1) it.index.toLong() * it.value.toLong()
        else 0
    }
}

private fun prepareData(): MutableList<Int> {
    var isData = true
    var count = 0
    val data = buildList {
        file.forEachLine {
            it.forEach {
                if (isData) {
                    addAll(List(it.digitToInt()) { count })
                    count++
                } else {
                    addAll(List(it.digitToInt()) { -1 })
                }

                isData = !isData
            }
        }
    }.toMutableList()
    return data
}

fun main() {
    println(part1())
    println(part2())
}
