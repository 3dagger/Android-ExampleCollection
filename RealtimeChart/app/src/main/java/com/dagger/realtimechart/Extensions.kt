package com.dagger.realtimechart

import java.text.SimpleDateFormat
import java.util.*

/**
 * @author : 이수현
 * @Date : 2022/01/20 12:17 오후
 * @Description : 일별 사용량 리포트 X축 파싱 메서드
 * @History :
 *
 **/
fun convertDailyFormant(inputDate: String?) : String {
    return if(inputDate.isNullOrEmpty() || inputDate.isNullOrBlank()) {
        "0"
    }else {
        val inputDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
        val date: Date = inputDateFormat.parse(inputDate)!!
        val resultDateFormat = SimpleDateFormat("MM.dd", Locale.KOREA)
        resultDateFormat.format(date)
    }
}

/**
 * @author : 이수현
 * @Date : 2022/01/20 12:17 오후
 * @Description : 월별 사용량 리포트 X축 파싱 메서드
 * @History :
 *
 **/
fun convertMonthlyFormat(inputDate: String?) : String {
    return if(inputDate.isNullOrEmpty() || inputDate.isNullOrBlank()) {
        "0"
    }else {
        val inputDateFormat = SimpleDateFormat("yyyy.MM", Locale.KOREA)
        val date: Date = inputDateFormat.parse(inputDate)!!
        val resultDateFormat = SimpleDateFormat("MM", Locale.KOREA)
        return "${resultDateFormat.format(date)}월"
    }
}