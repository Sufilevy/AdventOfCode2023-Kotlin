import kotlin.math.max

object Day2 {
	fun puzzleOne(input: String): Int =
		input.lines().withIndex().sumOf { (index, line) ->
			val game = line.substringAfter(": ")
			if (game.split("; ").all(::doesRoundMatchBag)) index + 1
			else 0
		}

	private val ELF_BAG = arrayOf(12, 13, 14)

	private fun doesRoundMatchBag(round: String): Boolean =
		round.split(", ").all { color ->
			val numCubes = numCubesOfColor(color)

			when (color.last()) {
				'd' -> numCubes <= ELF_BAG[0]  // Red
				'n' -> numCubes <= ELF_BAG[1]  // Green
				else -> numCubes <= ELF_BAG[2] // Blue
			}
		}

	private fun numCubesOfColor(color: String): Int = color.substringBefore(' ').toInt()

	fun puzzleTwo(input: String): Int =
		input.lines().sumOf { line ->
			val game = line.substringAfter(": ")

			val (minRed, minGreen, minBlue) =
				game.split("; ")
					.map(::bagOfRound)
					.fold(arrayOf(0, 0, 0)) { minBag, bag ->
						arrayOf(
							max(minBag[0], bag[0]), // Red
							max(minBag[1], bag[1]), // Green
							max(minBag[2], bag[2]), // Blue
						)
					}

			minRed * minGreen * minBlue
		}

	private fun bagOfRound(round: String): Array<Int> =
		round.split(", ").fold(arrayOf(0, 0, 0)) { bag, color ->
			val numCubes = numCubesOfColor(color)

			when (color.last()) {
				'd' -> bag[0] = numCubes  // Red
				'n' -> bag[1] = numCubes  // Green
				else -> bag[2] = numCubes // Blue
			}

			bag
		}
}