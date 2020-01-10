class Mask(val value: String) {
    private val digitsRegex = Regex(value.replace(Regex("[a-zA-Z]"), "[0-9]{1}"))
    private val lettersMask: String
    private val digitsIndexes: List<Int>

    init {
        val removedIndexes = mutableListOf<Int>()
        for ((index, c) in value.withIndex()) {
            if (!LETTERS.contains(c)) {
                removedIndexes.add(index)
            }
        }
        digitsIndexes = removedIndexes.toList()
        lettersMask = removePositions(value, digitsIndexes)
    }

    companion object {
        const val LETTERS = "ABCDEFGHI"

        private fun removePositions(text: String, indexes: List<Int>): String {
            val charList = text.toMutableList()
            var removedCount = 0
            indexes.forEach { charList.removeAt(it - removedCount); removedCount++ }
            return String(charList.toCharArray())
        }
    }

    private fun createMask(number: String): String {
        val counters = mutableMapOf<Char, Int>()
        number.forEach {
            if (counters[it] == null) {
                counters[it] = 0
            }
            counters[it] = counters[it]!! + 1
        }
        val sortedChars = counters.toList().sortedByDescending { it.second }.map { it.first }
        var result = number
        sortedChars.indices.forEach {
            result = result.replace(sortedChars[it], LETTERS[it])
        }
        return result
    }

    fun match(msisdn: String): Boolean {
        if (digitsRegex.matches(msisdn).not()) {
            return false
        }
        val cleanMsisdn = removePositions(msisdn, digitsIndexes)
        val cleanMask = createMask(cleanMsisdn)
        return cleanMask == lettersMask
    }
}