fun main() {
    val masks = listOf("0AAAAAB", "AABBCCD", "CAAABBD").map { Mask(it) }


    (0..9999999).map {
        buildString {
            repeat(7 - it.toString().length) { append("0") }
            append(it.toString())
        }
    }.forEach { msisdn ->
        val matchedMasks = masks.filter { it.match(msisdn) }.toSet()

        if (matchedMasks.isNotEmpty()) {

            println("$msisdn ${matchedMasks.map { it.value }}")

        }
    }
}