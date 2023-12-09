import kotlin.math.*

object Day6 {
	fun puzzleOne(input: String): Int =
		input.lines().let { (firstLine, secondLine) ->
			firstLine.splitToNumbers()
				.zip(secondLine.splitToNumbers())
				.map(::optionsCountOfRace)
				.reduce(Int::times)
		}

	private fun String.splitToNumbers(): List<Double> = splitWhitespace().drop(1).map(String::toDouble)

	private fun optionsCountOfRace(race: Pair<Double, Double>): Int {
		val a = 1.0
		val b = -race.first // Time
		val c = race.second // Distance record
		val d = b.pow(2) - 4 * a * c
		val minDuration = (-b - sqrt(d)) / (2 * a)
		val maxDuration = (-b + sqrt(d)) / (2 * a)
		return floor(maxDuration).toInt() - ceil(minDuration).toInt() + 1
	}

	fun puzzleTwo(input: String): Int =
		input.lines().let { (firstLine, secondLine) ->
			optionsCountOfRace(
				firstLine.combineToNumber() to secondLine.combineToNumber()
			)
		}

	private fun String.combineToNumber(): Double = substringAfter(':').removeWhitespace().toDouble()
}