package com.jbr.utils

import android.content.res.Resources
import com.jbr.asharplibrary.R

fun isDeviceTablet(resources: Resources): Boolean {
    return resources.getBoolean(R.bool.is_tablet)
}