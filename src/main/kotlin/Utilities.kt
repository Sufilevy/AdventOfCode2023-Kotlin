import kotlin.math.pow

// --- Strings ---
fun String.splitWhitespace(): List<String> = split("\\s+".toRegex())


// --- Numbers ---
infix fun Int.pow(other: Int): Int = toDouble().pow(other).toInt()
