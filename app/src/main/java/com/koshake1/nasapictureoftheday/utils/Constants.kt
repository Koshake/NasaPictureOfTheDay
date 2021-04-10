package com.koshake1.nasapictureoftheday.utils

const val THEME_TAG : String = "theme"
const val PRIVATE_MODE : Int =  0

const val POSITION_FIRST : Int =  0
const val POSITION_SECOND : Int =  1
const val POSITION_THIRD : Int =  2

const val BULLET_SPAN_GAP : Int =  16
const val BULLET_SPAN_RADIUS : Int =  10

enum class Themes(val themeNum: Int) {
    YELLOW(1),
    BLUE(2),
    FANCY(3),
}