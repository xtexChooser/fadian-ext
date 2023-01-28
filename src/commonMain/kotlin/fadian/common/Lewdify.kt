package fadian.common

import kotlin.random.Random

object Lewdify {

    val delimitersNoSpace = arrayOf("…", "...")
    val delimiters = arrayOf(" ", "…", "...")

    fun apply(word: String, rng: Random, chance: Float = 0.7f): String {
        if (rng.nextFloat() < chance && word.trim().isNotEmpty()) {
            if ((word == "," || word == "，" || word == "。") && rng.nextFloat() < 0.7f) {
                return delimiters.random(rng).repeat(if (word != "," && rng.nextBoolean()) 2 else 1)
            } else if (rng.nextFloat() < 0.3f) {
                return word + (if (rng.nextBoolean()) delimitersNoSpace.random(rng) else "")
            } else if (word.length < 3 && rng.nextFloat() < 0.47f) {
                return "〇".repeat(word.length)
            }
        }
        return word
    }

}