object Day8 {
	fun puzzleOne(input: String): Int = Network(input).traverse("AAA") { it == "ZZZ" }

	fun puzzleTwo(input: String): Long {
		val network = Network(input)
		val results = network.startingNodes().map { node ->
			network.traverse(node) { it.endsWith('Z') }.toLong()
		}
		return lcm(results)
	}

	private fun lcm(numbers: List<Long>): Long {
		val a = numbers[0]
		if (numbers.size == 1) return a
		val b = lcm(numbers.drop(1))
		return a * b / gcd(a, b)
	}

	private fun gcd(a: Long, b: Long): Long =
		if (b == 0L) a
		else gcd(b, a % b)
}

fun <T> Sequence<T>.cycle() = sequence { while (true) yieldAll(this@cycle) }

typealias Node = Pair<String, String>

class Network(input: String) {
	private val instructions: String
	private val nodes: Map<String, Node>

	init {
		val (instrctns, nds) = input.split("\r\n\r\n")
		instructions = instrctns
		nodes = nds.lineSequence().map(this::nodeFrom).toMap()
	}

	private fun nodeFrom(line: String): Pair<String, Node> {
		val (key, value) = line.split(" = ")
		val (left, right) = value.removeSurrounding("(", ")").split(", ")
		return key to (left to right)
	}

	fun startingNodes(): List<String> = nodes.keys.filter { it.endsWith('A') }

	fun traverse(startingNode: String, predicate: (String) -> Boolean): Int {
		var currentNode = startingNode
		return instructions
			.asSequence()
			.cycle()
			.withIndex()
			.firstNotNullOf { (index, instruction) ->
				val nextNode = nodes[currentNode]!!
				currentNode =
					when (instruction) {
						'L' -> nextNode.first
						else -> nextNode.second
					}

				if (predicate(currentNode)) index + 1
				else null
			}
	}
}
