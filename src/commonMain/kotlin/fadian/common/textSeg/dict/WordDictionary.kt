package fadian.common.textSeg.dict

data class WordDictionary(
    val words: Set<String>,
    val maxLength: Int = words.maxOf { it.length },
) {

    companion object {

        fun fromJieba(text: String, maxLength: Int? = null): WordDictionary {
            val words = text.lineSequence()
                .map { it.trim() }
                .filterNot { it.isEmpty() }
                .map { it.substringBefore(' ') }
                .toSet()
            return if (maxLength != null) WordDictionary(words, maxLength) else WordDictionary(words)
        }

    }

}
