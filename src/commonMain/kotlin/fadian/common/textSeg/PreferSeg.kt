package fadian.common.textSeg

class PreferSeg(val children: Set<TextSeg>): TextSeg {

    override fun segment(text: String) = children.map { it.segment(text) }.minBy { it.size }

}