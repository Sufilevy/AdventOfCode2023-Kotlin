object Day9 {
	fun puzzleOne(input: List<String>): Int =
		input.sumOf {
			val line = it.splitWhitespace().map(String::toInt).toList()
			extrapolateNext(line)
		}

	private fun extrapolateNext(line: List<Int>): Int =
		differencesOf(line).let { differences ->
			line.last() +
				if (differences.all { it == 0 }) 0
				else extrapolateNext(differences)
		}

	private fun differencesOf(line: List<Int>): List<Int> =
		line.withIndex().drop(1).map { (index, it) ->
			it - line[index - 1]
		}

	fun puzzleTwo(input: List<String>): Int = input.sumOf {
		val line = it.splitWhitespace().map(String::toInt).toList()
		extrapolatePrevious(line)
	}

	private fun extrapolatePrevious(line: List<Int>): Int =
		differencesOf(line).let { differences ->
			line.first() -
				if (differences.all { it == 0 }) 0
				else extrapolatePrevious(differences)
		}
}