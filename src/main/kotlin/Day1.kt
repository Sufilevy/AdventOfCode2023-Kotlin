object Day1 {
	fun puzzleOne(input: List<String>): Int = input.sumOf(::firstAndLastDigits)

	fun puzzleTwo(input: List<String>): Int =
		input.map { initialLine ->
			DIGITS_WORDS.fold(initialLine) { line, (from, to) -> line.replace(from, to) }
		}.sumOf(::firstAndLastDigits)

	private fun firstAndLastDigits(line: String): Int =
		line.first(Char::isDigit).digitToInt() * 10 +
			line.last(Char::isDigit).digitToInt()

	private val DIGITS_WORDS = listOf(
		"one" to "o1e",
		"two" to "t2o",
		"three" to "t3e",
		"four" to "f4r",
		"five" to "f5e",
		"six" to "s6x",
		"seven" to "s7n",
		"eight" to "e8t",
		"nine" to "n9e",
	)
}