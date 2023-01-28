package fadian.common

import kotlin.random.Random

/**
 * Emotional communication improver
 */
object EmoComm {

    val emoticons = arrayOf(
        "\uD83D\uDE0A", "\uD83D\uDE1A", "\uD83E\uDD70",
        "\uD83D\uDE19", "\uD83D\uDE0D",
        "\uD83D\uDE28", "\uD83D\uDDA4", "\uD83D\uDC95", "\u2764", "\uD83C\uDF80", "\u2728",
        "⭕️"
    )

    fun random(rng: Random) = emoticons.random(rng)

    fun decoration(word: String, rng: Random, chance: Float = 0.3f): String {
        if (rng.nextFloat() < chance && word.trim().isNotEmpty()) {
            return (if (rng.nextFloat() < 0.4f) random(rng) else "") +
                    word + (if (rng.nextBoolean()) random(rng) else "")
        }
        return word
    }

}