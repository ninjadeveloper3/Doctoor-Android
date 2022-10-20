package com.Doctoor.app.utils

import android.os.Build
import android.text.format.DateFormat
import androidx.annotation.RequiresApi
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class DateUtils {

    companion object {

        public fun calendar(): Calendar {
            val calendar = Calendar.getInstance()
            calendar.set(getYear(), getMonth(), getDayOfMonth())
            return calendar
        }

        public fun getYear(): Int {
            return Calendar.getInstance().get(Calendar.YEAR)
        }

        public fun getMonth(): Int {
            return Calendar.getInstance().get(Calendar.MONTH)
        }

        public fun getDayOfMonth(): Int {
            return Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        }

        public fun formatDate(selectedYear: Int, selectedMonth: Int, selectedDay: Int): String {

            val calendar = Calendar.getInstance()

            calendar.set(selectedYear, selectedMonth, selectedDay)

            val date = DateFormat.format("yyyy/MM/dd", calendar)
                .toString() //format of the date, choose what you want

            return date
        }

        public fun getCurrentDate(): String {

            return formatDate(getYear(), getMonth(), getDayOfMonth())
        }

        @RequiresApi(Build.VERSION_CODES.O)
        public fun formatDateFromString(dateTime: String): String {
            val formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy", Locale.ENGLISH)
            val d = ZonedDateTime.parse(dateTime)

            return d.format(formatter)
        }
    }
}