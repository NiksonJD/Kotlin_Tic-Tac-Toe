fun main(args: Array<String>) {
    val playField = "         ".split("").filter { it != "" }.toMutableList()
    val winner = mutableListOf<String>()
    val winCombos = listOf("XXX", "OOO")
    var evenOdd = 0
    val rows = "012345678036147258048246".map { it.digitToInt() }.chunked(3)

    fun updateTheGrid() {
        println("---------")
        for (r in rows.subList(0, 3))
            println("| ${playField[r[0]]} ${playField[r[1]]} ${playField[r[2]]} |")
        println("---------")
    }
    updateTheGrid()

    start@ while (true) {
        println(
            "Enter two numbers separated by a space. The first number is the row number, " +
                    "the second number is the column number. Current move ${if (evenOdd % 2 == 0 || evenOdd == 0) "X" else "O"}"
        )
        val (a, b) = readln().split(" ").map {
            try {
                it.toInt()
            } catch (e: NumberFormatException) {
                0
            }
        }
        if (a == 0 || b == 0) {
            println("You should enter numbers!")
            continue@start
        } else if (a !in 1..3 || b !in 1..3) {
            println("Coordinates should be from 1 to 3!")
            continue@start
        } else if (playField[rows[a - 1][b - 1]] == " ") {
            playField[rows[a - 1][b - 1]] = if (++evenOdd % 2 == 0) "O" else "X"
            updateTheGrid()
            rows.forEach {
                if (winCombos.indexOf(playField[it[0]] + playField[it[1]] + playField[it[2]]) != -1) winner.add(
                    playField[it[0]]
                )
            }
        } else {
            println("This cell is occupied! Choose another one!")
            continue@start
        }
        if (winner.size > 1 || Math.abs(playField.filter { it == "X" }.size - playField.filter { it == "O" }.size) > 1) {
            println("Impossible")
            break
        } else if (winner.size == 1) {
            println("${winner[0]} wins")
            break
        } else if (playField.indexOf(" ") == -1) {
            println("Draw")
            break
        }
    }
}
