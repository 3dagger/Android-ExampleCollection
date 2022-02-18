package com.dagger.realtimechart

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class Utility(private val context: Context) {

    fun openUrl(link: String) {
        if (TextUtils.isEmpty(link)) {
            Toast.makeText(context, "링크가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = getIntentByUrl(context, link)

        if (intent == null) {
            Toast.makeText(context, "링크를 여는 중 오류가 발생했습니다.\n잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            Toast.makeText(context, "해당 링크를 열 수 있는 앱이 설치되어 있지 않습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getIntentByUrl(context: Context, url: String): Intent? {
        if (TextUtils.isEmpty(url)) {
            return null
        }

        if (url.startsWith("intent:")) {
            try {
                var intent: Intent? = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                val existPackage =
                    context.packageManager.getLaunchIntentForPackage(intent!!.getPackage()!!)
                if (existPackage != null) {
                    return intent
                } else {
                    val fallBackUrl = intent.getStringExtra("browser_fallback_url")
                    if (TextUtils.isEmpty(fallBackUrl)) {
                        val marketIntent = Intent(Intent.ACTION_VIEW)
                        marketIntent.data =
                            Uri.parse("market://details?id=" + intent.getPackage()!!)
                        return marketIntent
                    } else {
                        intent = getIntentByUrl(context, fallBackUrl!!)
                    }

                }
                return intent
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        val intent = Intent(Intent.ACTION_VIEW)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.data = Uri.parse(url)

        return intent
    }

    /**
     * @author : 이수현
     * @Date : 2021/11/02 17:22 오전
     * @Description : Display 가로 DP 구하기
     * @History :
     *
     **/
    fun getDeviceWidthDpSize(activity: Activity): Float {
        val display = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.display
        } else {
            activity.windowManager.defaultDisplay
        }
        val outMetrics = DisplayMetrics()
        display?.getRealMetrics(outMetrics)

        return outMetrics.widthPixels / activity.resources.displayMetrics.density
    }

    fun getDeviceWidthDpSize2() : Float {
        val display = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.display
        } else {
            val b = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            b.defaultDisplay
//            activity.windowManager.defaultDisplay
        }
        val outMetrics = DisplayMetrics()
        display?.getRealMetrics(outMetrics)

//        return outMetrics.widthPixels / activity.resources.displayMetrics.density
        return outMetrics.widthPixels / context.resources.displayMetrics.density
    }

    /**
     * @author : 이수현
     * @Date : 2021/12/07 3:47 오후
     * @Description : Dp -> Pixel 변환
     * @History :
     *
     **/
     fun convertDpToPixel(context: Context, dp: Float): Int {
        val resources = context.resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).roundToInt()
    }

    /**
     * @author : 이수현
     * @Date : 2021/12/07 3:48 오후
     * @Description : Pixel -> Dp 변환
     * @History :
     *
     **/
    fun convertPixelToDp(context: Context, px: Int): Int {
        return (px / context.resources.displayMetrics.density).toInt()
    }

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

}