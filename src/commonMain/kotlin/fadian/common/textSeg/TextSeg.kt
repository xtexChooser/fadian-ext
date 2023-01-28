package fadian.common.textSeg

interface TextSeg {

    fun segment(text: String): List<String>

}

class NopSeg : TextSeg {

    override fun segment(text: String) = listOf(text)

}

class PreferSeg(val children: Set<TextSeg>): TextSeg {

    override fun segment(text: String) = children.map { it.segment(text) }.minBy { it.size }

}
