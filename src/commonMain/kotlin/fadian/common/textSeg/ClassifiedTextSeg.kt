package fadian.common.textSeg

class ClassifiedTextSeg(val seg: Map<CharClass, TextSeg>, val fallbackSeg: TextSeg) : TextSeg {

    override fun segment(text: String): List<String> {
        val buffer = StringBuilder()
        var currentType = CharClass.UNIDENTIFIED
        val words = mutableListOf<String>()
        (text + "\n").toCharArray()
            .forEach { char ->
                val type = CharClass.classify(char)
                if (type != currentType) {
                    if (buffer.isNotEmpty()) {
                        words += (seg[currentType] ?: fallbackSeg).segment(buffer.toString())
                    }
                    currentType = type
                    buffer.clear()
                }
                buffer.append(char)
            }
        return words
    }

}

enum class CharClass {

    /**
     * OTHER includes ideographs and unicase alphabets that is not CJK.
     */
    OTHER_LETTER,
    CJK, ENGLISH, NUMBER, SEPARATOR, SYMBOL, UNIDENTIFIED;

    companion object {

        fun classify(char: Char): CharClass {
            if (char.category == CharCategory.DECIMAL_DIGIT_NUMBER) {
                return NUMBER
            } else if (char.category == CharCategory.LOWERCASE_LETTER
                || char.category == CharCategory.UPPERCASE_LETTER
            ) {
                return ENGLISH
            } else if (char.category.name.endsWith("_PUNCTUATION")
                || char.category.name.endsWith("_SYMBOL")
            ) {
                return SYMBOL
            } else if (char.category == CharCategory.SPACE_SEPARATOR
                || char.category == CharCategory.LINE_SEPARATOR
                || char.category == CharCategory.PARAGRAPH_SEPARATOR
            ) {
                return SEPARATOR
            } else if (char.code in 0x2E80..0x9FFF
                || char.code in 0xF900..0xFAFF
                || char.code in 0xFE30..0xFE4F
                || char.code in 0x20000..0x3134F
            ) {
                return CJK
            } else if (char.code in 0xFF00..0xFFEF) {
                // CJK full/part angle symbols
                return SYMBOL
            } else if (char.category == CharCategory.OTHER_LETTER) {
                return OTHER_LETTER
            } else {
                return UNIDENTIFIED
            }
        }

    }

}