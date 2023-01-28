package fadian.common.textSeg.dict

class ForwardTextSeg(dict: WordDictionary) : DictTextSeg(dict) {

    override fun segment(text: String): List<String> {
        var index = 0
        val textLen = text.length
        val words = mutableListOf<String>()
        while (index < textLen) {
            var wordLen = minOf(dict.maxLength, textLen - index)
            while (wordLen > 0) {
                val word = text.substring(index, index + wordLen)
                if (word in dict.words) {
                    words += word
                    index += wordLen
                    break
                }
                wordLen--
            }
            if (wordLen == 0) {
                words += text[index].toString()
                index++
            }
        }
        return words
    }

}

class BackwardTextSeg(dict: WordDictionary) : DictTextSeg(dict) {

    override fun segment(text: String): List<String> {
        val textLen = text.length
        var index = textLen
        val words = mutableListOf<String>()
        while (index > 0) {
            var wordLen = minOf(dict.maxLength, index)
            while (wordLen > 0) {
                val word = text.substring(index - wordLen, index)
                if (word in dict.words) {
                    words += word
                    index -= wordLen
                    break
                }
                wordLen--
            }
            if (wordLen == 0) {
                words += text[index - 1].toString()
                index--
            }
        }
        words.reverse()
        return words
    }

}
