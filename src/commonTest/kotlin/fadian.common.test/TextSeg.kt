package fadian.common.test

import fadian.common.textSeg.PreferSeg
import fadian.common.textSeg.dict.BackwardTextSeg
import fadian.common.textSeg.dict.ForwardTextSeg
import fadian.common.textSeg.dict.WordDictionary
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class DictTextSegTest : WordSpec({
    "WordDictionary" should {
        "parse jieba dict" {
            WordDictionary.fromJieba(
                """
            水平阻生 3 n
            水平降水 3 n
            水平面 35 n
            水床 3 n
        """.trimIndent()
            ) shouldBe WordDictionary(setOf("水平阻生", "水平降水", "水平面", "水床"), 4)
        }
    }

    "ForwardTextSeg" should {
        "forward seg" {
            ForwardTextSeg(WordDictionary(setOf("你好", "世界", "个"))).segment("你好这个世界") shouldBe listOf(
                "你好",
                "这",
                "个",
                "世界"
            )
        }
    }

    "BackwardTextSeg" should {
        "backward seg" {
            BackwardTextSeg(WordDictionary(setOf("你好", "世界", "个"))).segment("你好这个世界") shouldBe listOf(
                "你好",
                "这",
                "个",
                "世界"
            )
        }
    }
})

class PreferSegTest : StringSpec({
    "prefer seg" {
        val dict = WordDictionary(setOf("A", "BCD", "CD"))
        PreferSeg(setOf(ForwardTextSeg(dict), BackwardTextSeg(dict))).segment("ABCD") shouldBe listOf(
            "A",
            "BCD"
        )
    }
})
