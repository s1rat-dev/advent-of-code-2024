import java.io.File
import java.math.BigDecimal

private val path = "src/main/kotlin/inputs/day07.txt"
private val file = File(path).absoluteFile
private val calculator = Calculator()

private fun part1(): BigDecimal {
    val operations = listOf(
        fun(x: BigDecimal, y: BigDecimal): BigDecimal = x + y,
        fun(x: BigDecimal, y: BigDecimal): BigDecimal = x * y
    )

    return calculator.createAndCalculate(file, operations)
}

private fun part2(): BigDecimal {
    val operations = listOf(
        fun(x: BigDecimal, y: BigDecimal): BigDecimal = x + y,
        fun(x: BigDecimal, y: BigDecimal): BigDecimal = x * y,
        fun(x: BigDecimal, y: BigDecimal): BigDecimal = BigDecimal("$x$y")
    )

    return calculator.createAndCalculate(file, operations)
}

fun main() {
    println(part1())
    println(part2())
}

class Calculator {

    fun createAndCalculate(file: File, operations: List<(BigDecimal, BigDecimal) -> BigDecimal>): BigDecimal {
        var count: BigDecimal = BigDecimal.ZERO

        file.forEachLine {
            it.split(":").let {
                val target = BigDecimal(it.first())
                val nums = it.last().split(" ").mapNotNull { it.toBigDecimalOrNull() }

                count += calculate(list = nums, target = target, operations)
            }
        }

        return count
    }

    private fun calculate(
        list: List<BigDecimal>,
        target: BigDecimal,
        operations: List<(BigDecimal, BigDecimal) -> BigDecimal>
    ): BigDecimal {
        var iterationsCount = 0

        fun iterate(current: BigDecimal, list: List<BigDecimal>, target: BigDecimal) {
            if (list.isEmpty()) {
                if (current == target) iterationsCount++
                return
            }

            operations.forEach {
                iterate(it.invoke(current, list.first()), list.drop(1), target)
            }
        }
        iterate(BigDecimal.ZERO, list, target)

        return if (iterationsCount != 0) target else BigDecimal.ZERO
    }
}