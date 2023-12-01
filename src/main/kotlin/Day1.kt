object Day1 {
	fun puzzleOne(input: List<String>): Int = input.sumOf(::firstAndLastDigits)

	fun puzzleTwo(input: List<String>): Int =
		input.map { initialLine ->
			digitsWords.fold(initialLine) { line, (from, to) -> line.replace(from, to) }
		}.sumOf(::firstAndLastDigits)

	private fun firstAndLastDigits(line: String): Int =
		line.find { it.isDigit() }!!.digitToInt() * 10 +
			line.findLast { it.isDigit() }!!.digitToInt()

	private val digitsWords = listOf(
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