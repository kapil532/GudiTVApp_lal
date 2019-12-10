package com.tv.guditvapp.utils

import com.tv.guditvapp.dashboard.model.SectionDataModel
import com.tv.guditvapp.utils.AppConstants.EXTRA_DASH_BOARD_MODEL_SECTION_DATA

object DashBoardUtils {
    var screenType = 0

    val map = mutableMapOf<String, SectionDataModel?>()

    fun storeDashBoardSectionModel(sectionDataModel: SectionDataModel){
        map.put(EXTRA_DASH_BOARD_MODEL_SECTION_DATA,sectionDataModel)
    }

    fun getDashBoardSectionModel(): SectionDataModel?{
        return  map.get(EXTRA_DASH_BOARD_MODEL_SECTION_DATA)
    }

    fun clearSectionModelData(){
        map.clear()
    }
}