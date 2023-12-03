import kotlin.math.max

object Day2 {
	fun puzzleOne(input: List<String>): Int =
		input.mapIndexed { index, line ->
			val game = line.split(": ")[1]

			if (game.split("; ").all(::doesRoundMatchBag)) {
				index + 1
			} else {
				0
			}
		}.sum()

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

	private fun numCubesOfColor(color: String): Int = color.split(' ')[0].toInt()

	fun puzzleTwo(input: List<String>): Int =
		input.sumOf { line ->
			val game = line.split(": ")[1]

			val minBag = game.split("; ").map(::bagOfRound).fold(arrayOf(0, 0, 0)) { minBag, bag ->
				arrayOf(
					max(minBag[0], bag[0]), // Red
					max(minBag[1], bag[1]), // Green
					max(minBag[2], bag[2]), // Blue
				)
			}

			minBag[0] * minBag[1] * minBag[2]
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