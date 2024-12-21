import java.io.File

private val path = "src/main/kotlin/inputs/day06.txt"
private val file = File(path).absoluteFile
private const val RANGE = 129

private fun part1(): Int {
    val pathHistory = mutableListOf<Pair<Int, Int>>()
    val obstacles: MutableList<Pair<Int, Int>> = mutableListOf()
    lateinit var player: Pair<Int, Int>
    val directionDecider = DirectionDecider()

    file.readLines().forEachIndexed { index, line ->
        line.forEachIndexed { li, c ->
            if (c == '#') {
                obstacles.add(li to index)
            } else if (c == '^') {
                player = li to index
            }
        }
    }

    while (player.first in 0..RANGE && player.second in 0..RANGE) {
        player = directionDecider.run(player)

        if (player in obstacles) {
            player = directionDecider.decideNextLocation(player)
        }

        pathHistory.add(player)
    }


    return pathHistory.distinct().size - 1
}

private fun part2(): Int {
    val pathHistory = mutableListOf<Pair<Pair<Int, Int>, Int>>()
    val obstacles: MutableList<Pair<Int, Int>> = mutableListOf()
    lateinit var player: Pair<Int, Int>
    val directionDecider = DirectionDecider()

    file.readLines().forEachIndexed { index, line ->
        line.forEachIndexed { li, c ->
            if (c == '#') {
                obstacles.add(li to index)
            } else if (c == '^') {
                player = li to index
            }
        }
    }

    while (player.first in 0..RANGE && player.second in 0..RANGE) {
        player = directionDecider.run(player)

        if (player in obstacles) {
            player = directionDecider.decideNextLocation(player)
        }

        pathHistory.add(player to directionDecider.getDirection())

    }

    var counter = 0

    pathHistory.distinctBy { it.first }.forEach {
        val clearPath = mutableListOf<Pair<Pair<Int, Int>, Int>>()
        player = directionDecider.getPlayerLocation(it)
        directionDecider.setDirection(it.second)

        obstacles.add(it.first)
        while (player.first in 0..RANGE && player.second in 0..RANGE) {
            player = directionDecider.run(player)

            if (player in obstacles) {
                player = directionDecider.decideNextLocation(player)
            }

            val playerWithDirection = player to directionDecider.getDirection()

            if (playerWithDirection in clearPath) {
                counter++
                break
            }

            clearPath.add(playerWithDirection)
        }

        obstacles.remove(it.first)
    }

    return counter
}

fun main() {
    println(part1())
    println(part2())
}


class DirectionDecider {
    private var count = 0

    fun getDirection() = count % 4

    fun setDirection(direction: Int) {
        count = direction
    }

    fun run(location: Pair<Int, Int>): Pair<Int, Int> = when (count % 4) {
        0 -> location.first to location.second - 1
        1 -> location.first + 1 to location.second
        2 -> location.first to location.second + 1
        3 -> location.first - 1 to location.second
        else -> throw Exception()
    }

    fun decideNextLocation(location: Pair<Int, Int>): Pair<Int, Int> {
        val nextLocation = getLocation(location)
        count++

        return nextLocation
    }

    private fun getLocation(location: Pair<Int, Int>): Pair<Int, Int> = when (count % 4) {
        0 -> location.first to location.second + 1
        1 -> location.first - 1 to location.second
        2 -> location.first to location.second - 1
        3 -> location.first + 1 to location.second
        else -> throw Exception()
    }

    fun getPlayerLocation(locationWithDirection: Pair<Pair<Int, Int>, Int>): Pair<Int, Int> {
        val direction = locationWithDirection.second
        val location = locationWithDirection.first

        return when (direction) {
            0 -> location.first to location.second + 1
            1 -> location.first - 1 to location.second
            2 -> location.first to location.second - 1
            3 -> location.first + 1 to location.second
            else -> throw Exception()
        }
    }

}