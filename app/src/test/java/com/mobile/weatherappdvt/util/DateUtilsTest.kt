package com.mobile.weatherappdvt.util

import junit.framework.TestCase
import org.junit.Test

class DateUtilsTest : TestCase() {

    @Test
    fun testGetDayForDate() {
        val sat = DateUtils.getDayForDate("2022-01-08 21:00:00")
        assert(sat.equals("Saturday"))

        val sun = DateUtils.getDayForDate("2022-01-09 21:00:00")
        assert(sun.equals("Sunday"))

        val mon = DateUtils.getDayForDate("2022-01-10 21:00:00")
        assert(mon.equals("Monday"))

        val tue = DateUtils.getDayForDate("2022-01-11 21:00:00")
        assert(tue.equals("Tuesday"))

        val wed = DateUtils.getDayForDate("2022-01-12 21:00:00")
        assert(wed.equals("Wednesday"))

        val thu = DateUtils.getDayForDate("2022-01-13 21:00:00")
        assert(thu.equals("Thursday"))

        val fri = DateUtils.getDayForDate("2022-01-14 21:00:00")
        assert(fri.equals("Friday"))

        val noDate = DateUtils.getDayForDate("")
        assert(noDate == null)
    }
}