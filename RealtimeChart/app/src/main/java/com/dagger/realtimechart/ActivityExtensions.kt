package com.dagger.realtimechart

import android.content.Context
import java.text.NumberFormat
import java.util.*

fun Context.convertComma(value: Int?) : String? {
	return NumberFormat.getNumberInstance(Locale.KOREA).format(value)
}