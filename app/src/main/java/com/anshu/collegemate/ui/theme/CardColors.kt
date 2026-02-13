package com.anshu.collegemate.ui.theme

import android.graphics.Color.rgb
data class CardColors(
    val colorKey: String,
    val nameColor: Int, val instructorColor: Int,
    val roomNoColor: Int, val progressBarColor: Int=0,
    val timingContentColor: Int, val timingBackgroundColor: Int,
    val cardBackgroundColor: Int
)

object CardColorsScheme{

    val BLUETHEME = CardColors(colorKey = "BLUETHEME",
        nameColor = 0xff075985.toInt(), instructorColor = 0xff0284C7.toInt(), roomNoColor = 0xff0369a1.toInt(),
        progressBarColor = 0xff0ea5e9.toInt(), timingContentColor = 0xff0c4a6e.toInt(), timingBackgroundColor = 0xff7dd3fc.toInt(),
        cardBackgroundColor = 0xffe0f2fe.toInt()
    )
    val GREENTHEME = CardColors(colorKey = "GREENTHEME",
        nameColor = rgb(6, 95, 70),
        instructorColor = rgb(5, 150, 105),
        roomNoColor = rgb(4, 120, 87),
        progressBarColor = 0,
        timingContentColor = rgb(6, 78, 59),
        timingBackgroundColor = rgb(110, 231, 183),
        cardBackgroundColor = rgb(209, 250, 229)
    )
    //done by AI

    val ORANGETHEME = CardColors(colorKey = "ORANGETHEME",
        nameColor = rgb(124, 45, 18),          // dark orange-brown
        instructorColor = rgb(249, 115, 22),   // bright orange
        roomNoColor = rgb(194, 65, 12),
        progressBarColor = rgb(251, 146, 60),
        timingContentColor = rgb(120, 53, 15),
        timingBackgroundColor = rgb(254, 215, 170),
        cardBackgroundColor = rgb(255, 237, 213)
    )
    val PURPLETHEME = CardColors(colorKey = "PURPLETHEME",
        nameColor = rgb(88, 28, 135),
        instructorColor = rgb(168, 85, 247),
        roomNoColor = rgb(107, 33, 168),
        progressBarColor = rgb(192, 132, 252),
        timingContentColor = rgb(76, 29, 149),
        timingBackgroundColor = rgb(221, 214, 254),
        cardBackgroundColor = rgb(243, 232, 255)
    )
    val REDTHEME = CardColors(colorKey = "REDTHEME",
        nameColor = rgb(127, 29, 29),
        instructorColor = rgb(220, 38, 38),
        roomNoColor = rgb(153, 27, 27),
        progressBarColor = rgb(248, 113, 113),
        timingContentColor = rgb(99, 26, 26),
        timingBackgroundColor = rgb(254, 202, 202),
        cardBackgroundColor = rgb(254, 226, 226)
    )
    val TEALTHEME = CardColors(colorKey = "TEALTHEME",
        nameColor = rgb(19, 78, 74),
        instructorColor = rgb(13, 148, 136),
        roomNoColor = rgb(15, 118, 110),
        progressBarColor = rgb(45, 212, 191),
        timingContentColor = rgb(17, 94, 89),
        timingBackgroundColor = rgb(153, 246, 228),
        cardBackgroundColor = rgb(204, 251, 241)
    )
    val YELLOWTHEME = CardColors(colorKey = "YELLOWTHEME",
        nameColor = rgb(113, 63, 18),
        instructorColor = rgb(234, 179, 8),
        roomNoColor = rgb(161, 98, 7),
        progressBarColor = rgb(250, 204, 21),
        timingContentColor = rgb(120, 53, 15),
        timingBackgroundColor = rgb(254, 240, 138),
        cardBackgroundColor = rgb(254, 249, 195)
    )

    val listOfCardColorScheme = listOf<CardColors>(BLUETHEME,GREENTHEME,TEALTHEME,YELLOWTHEME,ORANGETHEME,REDTHEME,PURPLETHEME)
    fun getCCScheme(colorKey:String): CardColors{
        listOfCardColorScheme.forEach {
            if(it.colorKey.contentEquals(colorKey)){
                return it}
        }
        return BLUETHEME
    }
}
