import Direction.*

object Day10 {
	fun puzzleOne(input: String): Int =
		Pipes(input.lines()).let { pipes ->
			(pipes.getPipesLoop().size + 1) / 2
		}

	fun puzzleTwo(input: String): Int =
		input.lines().let { lines ->
			Pipes(lines).countEnclosedTiles(lines)
		}
}

enum class Direction {
	North,
	South,
	East,
	West;

	fun opposite(): Direction = when (this) {
		North -> South
		South -> North
		East -> West
		West -> East
	}
}

sealed class Tile {
	class Pipe(val first: Direction, val second: Direction) : Tile()
	data object Start : Tile()
	data object Ground : Tile()

	fun leadsTo(direction: Direction): Boolean =
		when (this) {
			is Pipe -> first == direction || second == direction
			Start -> true
			Ground -> false
		}

	fun availableDirections(): List<Direction> =
		when (this) {
			is Pipe -> listOf(first, second)
			Start -> listOf(North, South, East, West)
			Ground -> unreachable()
		}

	companion object {
		fun from(c: Char): Tile =
			when (c) {
				'|' -> Pipe(North, South)
				'-' -> Pipe(East, West)
				'L' -> Pipe(North, East)
				'J' -> Pipe(North, West)
				'7' -> Pipe(South, West)
				'F' -> Pipe(South, East)
				'S' -> Start
				'.' -> Ground
				else -> unreachable()
			}
	}
}

typealias TilePosition = Pair<Int, Int>

class Pipes(input: List<String>) {
	private val tiles = input.map { it.map(Tile::from).toTypedArray() }.toTypedArray()
	private val startingPipe: TilePosition

	init {
		val rowIndex = tiles.indexOfFirst { Tile.Start in it }
		val columnIndex = tiles[rowIndex].indexOfFirst { it is Tile.Start }
		startingPipe = rowIndex to columnIndex
	}

	private fun tileOf(tile: TilePosition) = tiles[tile.first][tile.second]

	fun getPipesLoop(): List<TilePosition> {
		val pipes = mutableListOf(startingPipe)
		var currentDirection = directionToNextPipeOf(startingPipe)
		var currentPipe = nextPipeInDirection(startingPipe, currentDirection)

		while (currentPipe != startingPipe) {
			pipes.add(currentPipe)
			currentDirection = directionToNextPipeOf(currentPipe, currentDirection)
			currentPipe = nextPipeInDirection(currentPipe, currentDirection)
		}

		return pipes
	}

	private fun directionToNextPipeOf(pipe: TilePosition, lastDirection: Direction? = null): Direction {
		for (direction in tileOf(pipe).availableDirections()) {
			val oppositeDirection = direction.opposite()
			val nextTile = nextPipeInDirection(pipe, direction)

			if (oppositeDirection != lastDirection && tileOf(nextTile).leadsTo(oppositeDirection)) {
				return direction
			}
		}
		unreachable()
	}

	private fun nextPipeInDirection(tile: TilePosition, direction: Direction): TilePosition =
		when (direction) {
			North -> (tile.first - 1) to tile.second
			South -> (tile.first + 1) to tile.second
			East -> tile.first to (tile.second + 1)
			West -> tile.first to (tile.second - 1)
		}

	fun countEnclosedTiles(input: List<String>): Int {
		val crossingPipes = listOf('S', '|', 'F', '7')
		val pipesLoop = getPipesLoop()
		return input.withIndex().sumOf { (y, line) ->
			var crossings = 0
			line.withIndex().sumOf { (x, char) ->
				if (pipesLoop.contains(y to x)) {
					if (char in crossingPipes) crossings += 1
					0
				} else if (crossings % 2 != 0) 1
				else 0.toInt()
			}
		}
	}
}
