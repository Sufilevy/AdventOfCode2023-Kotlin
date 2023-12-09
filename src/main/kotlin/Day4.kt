object Day4 {
	fun puzzleOne(input: String): Int =
		input.lines().sumOf { card ->
			val winningCount = winningCountOf(card)
			if (winningCount == 0) 0 else 2 pow (winningCount - 1)
		}

	private fun winningCountOf(card: String): Int {
		val parts = card.substringAfter(": ").split(" | ")

		val winningNumbers = numbersOf(parts[0])
		val yourNumbers = numbersOf(parts[1])

		return yourNumbers.intersect(winningNumbers).size
	}

	private fun numbersOf(part: String): Set<Int> =
		part.trim().splitWhitespace().map(String::toInt).toSet()

	fun puzzleTwo(input: String): Int =
		input.lines().map(::winningCountOf).withIndex().toList().let { cards ->
			cards.size + cards.sumOf { numberOfCardsFromCard(it.index, cards) }
		}

	private val CARDS_CACHE = mutableMapOf<Int, Int>()

	private fun numberOfCardsFromCard(cardIndex: Int, cards: List<IndexedValue<Int>>): Int {
		CARDS_CACHE[cardIndex]?.let { return it }

		var winningCount = cards[cardIndex].value
		winningCount += cards.drop(cardIndex + 1).take(winningCount).sumOf { numberOfCardsFromCard(it.index, cards) }

		return winningCount.also { CARDS_CACHE[cardIndex] = it }
	}
}

