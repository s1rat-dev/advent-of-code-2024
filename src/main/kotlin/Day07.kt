import java.io.File
import java.math.BigDecimal

private val path = "src/main/kotlin/inputs/day07.txt"
private val file = File(path).absoluteFile

private fun part1() : BigDecimal {
    val counter = Counter()

    file.forEachLine {
        it.split(":").let {
            val target = BigDecimal(it.first())
            val nums = it.last().split(" ").mapNotNull { it.toBigDecimalOrNull() }

            counter.calculateP1(list = nums, target = target)
        }
    }

    return counter.count
}

private fun part2() : BigDecimal {
    val counter = Counter()

    file.forEachLine {
        it.split(":").let {
            val target = BigDecimal(it.first())
            val nums = it.last().split(" ").mapNotNull { it.toBigDecimalOrNull() }

            counter.calculateP2(list = nums, target = target)
        }
    }

    return counter.count
}

fun main() {
    println(part1())
    println(part2())
}

class Counter {
    var count: BigDecimal = BigDecimal.ZERO

    fun calculateP1(list: List<BigDecimal>, target: BigDecimal) {
        var iterationsCount = 0

        fun iterate(current: BigDecimal, list: List<BigDecimal>, target: BigDecimal) {
            if (list.isEmpty()) {
                if (current == target) iterationsCount++
                return
            }

            iterate(current + list.first(), list.drop(1), target)
            iterate(current * list.first(), list.drop(1), target)
        }

        iterate(BigDecimal.ZERO, list, target)

        if (iterationsCount != 0) count += target
    }

    fun calculateP2(list: List<BigDecimal>, target: BigDecimal) {
        var iterationsCount = 0

        fun iterate(current: BigDecimal, list: List<BigDecimal>, target: BigDecimal) {
            if (list.isEmpty()) {
                if (current == target) iterationsCount++
                return
            }

            iterate(BigDecimal("$current${list.first()}"), list.drop(1), target)
            iterate(current + list.first(), list.drop(1), target)
            iterate(current * list.first(), list.drop(1), target)
        }

        iterate(BigDecimal.ZERO, list, target)

        if (iterationsCount != 0) count += target
    }


}