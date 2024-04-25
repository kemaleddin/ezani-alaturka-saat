package com.sahnisemanyazilim.ezanisaat.extensions

import android.content.Context
import com.google.gson.reflect.TypeToken
import com.kemalettinsargin.mylib.Util
import com.sahnisemanyazilim.ezanisaat.C
import com.sahnisemanyazilim.ezanisaat.model.Town

/**
 * Written by "كمال الدّين صارغين"  on 23.04.2024.
 * و من الله توفیق
 */

fun Context.getDefaultTown():Town?{
    val gson= Util.getGson()
    val towns = gson.fromJson<List<Town>>(
        Util.getPref(this, C.KEY_LOCATIONS),
        object : TypeToken<List<Town?>?>() {}.type
    )
    val defaultId=Util.getPref(this,C.KEY_ACTIVE)
    return towns?.firstOrNull { it.ilceID==defaultId }?:towns.firstOrNull()
}