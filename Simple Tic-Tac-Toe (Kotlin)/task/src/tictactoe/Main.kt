package tictactoe

import kotlin.math.abs

enum class GameState {
    XWins, OWins, Draw, GameNotFinished, Impossible
}

enum class Players {
    X, O
}

fun main() {
    var grid = "_________"
    buildBoard(grid)
    var activePlayer = Players.X;

    var gameOver = false

    do {
        var askUserForInput = true
        var elementInGrid = 0;
        do {
            val coords = readln()

            if (coords.contains(Regex("[a-z][A-Z]*"))) {
                println("You should enter numbers!")
                continue
            }

            val coordinates = coords.split(' ')
            if ((coordinates[0].toInt() > 3 && coordinates[0].toInt() > 1) || (coordinates[1].toInt() > 3 && coordinates[1].toInt() > 1)) {
                println("Coordinates should be from 1 to 3!")
                continue
            }

            elementInGrid = (coordinates[0].toInt() * 3) + coordinates[1].toInt() - 3
            if (grid[elementInGrid - 1] != '_') {
                println("This cell is occupied! Choose another one!")
                continue
            }

            askUserForInput = false
        } while (askUserForInput)

        val listOfChars = grid.toMutableList()
        listOfChars[elementInGrid - 1] = if (activePlayer == Players.X) 'X' else 'O'
        grid = listOfChars.joinToString("")

        buildBoard(grid)

        val evaluateWinCondition = evaluateWinCondition(grid)
        activePlayer = if (activePlayer == Players.X) Players.O else Players.X
        when (evaluateWinCondition) {
            GameState.XWins -> println("X wins")
            GameState.OWins -> println("O wins")
            GameState.Draw -> println("Draw")
            GameState.Impossible -> println("Impossible")
            GameState.GameNotFinished -> continue
        }

        gameOver = true
    } while (!gameOver)
}

fun evaluateWinCondition(grid: String): GameState {
    assert(grid.length == 9)

    val numberOfX = grid.filter { it == 'X' }.length
    val numberOfO = grid.filter { it == 'O' }.length

    if (abs(numberOfX - numberOfO) >= 2) return GameState.Impossible

    val winningPlayer: MutableList<GameState> = mutableListOf()
    for (player in arrayOf('X', 'O')) {
        val checkPlayer = if (player == 'X') GameState.XWins else GameState.OWins
        if (grid[0] == player && grid[1] == player && grid[2] == player) {
            winningPlayer.add(checkPlayer)
            continue
        }
        if (grid[3] == player && grid[4] == player && grid[5] == player) {
            winningPlayer.add(checkPlayer)
            continue
        }
        if (grid[6] == player && grid[7] == player && grid[8] == player) {
            winningPlayer.add(checkPlayer)
            continue
        }

        if (grid[0] == player && grid[4] == player && grid[8] == player) {
            winningPlayer.add(checkPlayer)
            continue
        }
        if (grid[2] == player && grid[4] == player && grid[6] == player) {
            winningPlayer.add(checkPlayer)
            continue
        }

        if (grid[0] == player && grid[3] == player && grid[6] == player) {
            winningPlayer.add(checkPlayer)
            continue
        }
        if (grid[1] == player && grid[4] == player && grid[7] == player) {
            winningPlayer.add(checkPlayer)
            continue
        }
        if (grid[2] == player && grid[5] == player && grid[8] == player) {
            winningPlayer.add(checkPlayer)
            continue
        }
    }

    if (winningPlayer.count() > 1) return GameState.Impossible
    if (winningPlayer.count() == 1) return winningPlayer.first()

    if (grid.contains("_")) return GameState.GameNotFinished

    return GameState.Draw
}

fun buildBoard(grid: String) {
    println("---------")

    for (i in grid.indices) {
        if (i % 3 == 0 && i != 0) {
            println()
        }
        if (i == 0 || i == 3 || i == 6) print("| ")
        if (i == 1 || i == 4 || i == 7) print(" ")
        print(grid[i])
        if (i == 1 || i == 4 || i == 7) print(" ")
        if (i == 2 || i == 5 || i == 8) print(" |")
        if (i == 8) println()
    }

    println("---------")
}