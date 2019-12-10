package com.tv.guditvapp.utils

class ScreenType {

    enum class ScreenType(val pos: Int){
        SCREEN_1(1),
        SCREEN_2(2),
        SCREEN_3(3)
    }
    enum class ScreenViewType(val view: String){
        SCREEN_SECTION_VIDEO("video"),
        SCREEN_SECTION_IMAGE("image"),
        SCREEN_SECTION_TEXT("text")
    }
}