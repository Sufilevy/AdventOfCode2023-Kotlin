import java.io.File

fun main() {
	val input = File("src/main/resources/input1.txt").readLines()
	println(Day1.puzzleOne(input))
	println(Day1.puzzleTwo(input))
}