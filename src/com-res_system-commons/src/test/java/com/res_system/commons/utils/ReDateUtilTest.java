package com.res_system.commons.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Test;

import com.res_system.commons.util.ReDateUtil;

public class ReDateUtilTest {

    @Test
    public void toDateTest() {
        String value = null;
        System.out.println("◆◆◆ toDate() ◆◆◆");
        assertNull(ReDateUtil.toDate(null, null));                    // NG
        assertNull(ReDateUtil.toDate("", ""));                        // NG
        assertNull(ReDateUtil.toDate(value));                         // NG
        assertNull(ReDateUtil.toDate(""));                            // NG
        assertNull(ReDateUtil.toDate("asdasd"));                      // NG
        assertNull(ReDateUtil.toDate("20160708"));                    // NG
        assertNull(ReDateUtil.toDate("2016-13-08"));                  // NG
        assertNull(ReDateUtil.toDate("2016-06-31"));                  // NG
        assertNull(ReDateUtil.toDate("2016-07-32"));                  // NG
        assertNull(ReDateUtil.toDate("2015-02-29"));                  // NG
        assertNull(ReDateUtil.toDate("2016/07/08", "asd"));           // NG
        assertNull(ReDateUtil.toDate("2016-07-08", "234"));           // NG
        assertNull(ReDateUtil.toDate("2016/07/08", "yyyy-MM-dd"));    // NG
        assertNull(ReDateUtil.toDate("2016-07-08", "yyyy/MM/dd"));    // NG
        assertNull(ReDateUtil.toDate("2016-07-08", "yyyy-MM-dd HH:mm:ss"));   // NG
        assertNull(ReDateUtil.toDate("2016/07/08", "yyyy/MM/dd HH:mm:ss"));   // NG
        assertNull(ReDateUtil.toDate("2017-02-29"));                  // NG
        assertNotNull(ReDateUtil.toDate("2016-02-29")); System.out.println("\"2016-02-29\":" + ReDateUtil.toDate("2016-02-29"));
        assertNotNull(ReDateUtil.toDate("2016/7/8")); System.out.println("\"2016/7/8\":" + ReDateUtil.toDate("2016/7/8"));
        assertNotNull(ReDateUtil.toDate("2016/07/08")); System.out.println("\"2016/07/08\":" + ReDateUtil.toDate("2016/07/08"));
        assertNotNull(ReDateUtil.toDate("2016-7-8")); System.out.println("\"2016-7-8\":" + ReDateUtil.toDate("2016-7-8"));
        assertNotNull(ReDateUtil.toDate("2016-07-08")); System.out.println("\"2016-07-08\":" + ReDateUtil.toDate("2016-07-08"));
        assertNotNull(ReDateUtil.toDate("2016-07-08 18:12:45")); System.out.println("\"2016-07-08 18:12:45\":" + ReDateUtil.toDate("2016-07-08 18:12:45"));
        assertNotNull(ReDateUtil.toDate("2016/07/08",          "yyyy/MM/dd")); System.out.println("\"2016/07/08\",\"yyyy/MM/dd\":" + ReDateUtil.toDate("2016/07/08", "yyyy/MM/dd"));
        assertNotNull(ReDateUtil.toDate("2016-07-08",          "yyyy-MM-dd")); System.out.println("\"2016-07-08\",\"yyyy-MM-dd\":" + ReDateUtil.toDate("2016-07-08", "yyyy-MM-dd"));
        assertNotNull(ReDateUtil.toDate("20160708",            "yyyyMMdd")); System.out.println("\"20160708\",\"yyyyMMdd\":" + ReDateUtil.toDate("20160708", "yyyyMMdd"));
        assertNotNull(ReDateUtil.toDate("2016-07-08 18:12:45", "yyyy-MM-dd HH:mm:ss")); System.out.println("\"2016-07-08 18:12:45\",\"yyyy-MM-dd HH:mm:ss\":" + ReDateUtil.toDate("2016-07-08 18:12:45", "yyyy-MM-dd HH:mm:ss"));
        assertNotNull(ReDateUtil.toDate("20160708181245",      "yyyyMMddHHmmss")); System.out.println("\"20160708181245\",\"yyyyMMddHHmmss\":" + ReDateUtil.toDate("20160708181245", "yyyyMMddHHmmss"));
        assertNotNull(ReDateUtil.toDate("2016-07-08 18:12:45", "yyyy-MM-dd")); System.out.println("\"2016-07-08 18:12:45\",\"yyyy-MM-dd\":" + ReDateUtil.toDate("2016-07-08 18:12:45", "yyyy-MM-dd"));
        assertNotNull(ReDateUtil.toDate("2016/07/08 18:12:45", "yyyy/MM/dd")); System.out.println("\"2016/07/08 18:12:45\",\"yyyy/MM/dd\":" + ReDateUtil.toDate("2016/07/08 18:12:45", "yyyy/MM/dd"));

        assertNotNull(ReDateUtil.toDate(ReDateUtil.nowTs())); System.out.println("ReDateUtil.getNowTs():" + ReDateUtil.toDate(ReDateUtil.nowTs()));
        assertNotNull(ReDateUtil.toDate(LocalDate.now())); System.out.println("LocalDate.now():" + ReDateUtil.toDate(LocalDate.now()));
        assertNotNull(ReDateUtil.toDate(LocalDateTime.now())); System.out.println("LocalDateTime.now():" + ReDateUtil.toDate(LocalDateTime.now()));
    }

    @Test
    public void toTimestampTest() {
        String value = null;
        System.out.println("◆◆◆ toTimestamp() ◆◆◆");
        assertNull(ReDateUtil.toTimestamp(null, null));                    // NG
        assertNull(ReDateUtil.toTimestamp("", ""));                        // NG
        assertNull(ReDateUtil.toTimestamp(value));                         // NG
        assertNull(ReDateUtil.toTimestamp(""));                            // NG
        assertNull(ReDateUtil.toTimestamp("asdasd"));                      // NG
        assertNull(ReDateUtil.toTimestamp("20160708"));                    // NG
        assertNull(ReDateUtil.toTimestamp("2016-13-08"));                  // NG
        assertNull(ReDateUtil.toTimestamp("2016-06-31"));                  // NG
        assertNull(ReDateUtil.toTimestamp("2016-07-32"));                  // NG
        assertNull(ReDateUtil.toTimestamp("2015-02-29"));                  // NG
        assertNull(ReDateUtil.toTimestamp("2016/07/08", "asd"));           // NG
        assertNull(ReDateUtil.toTimestamp("2016-07-08", "234"));           // NG
        assertNull(ReDateUtil.toTimestamp("2016/07/08", "yyyy-MM-dd"));    // NG
        assertNull(ReDateUtil.toTimestamp("2016-07-08", "yyyy/MM/dd"));    // NG
        assertNull(ReDateUtil.toTimestamp("2016-07-08", "yyyy-MM-dd HH:mm:ss"));   // NG
        assertNull(ReDateUtil.toTimestamp("2016/07/08", "yyyy/MM/dd HH:mm:ss"));   // NG
        assertNull(ReDateUtil.toTimestamp("2017-02-29"));                  // NG
        assertNotNull(ReDateUtil.toTimestamp("2016-02-29")); System.out.println("\"2016-02-29\":" + ReDateUtil.toTimestamp("2016-02-29"));
        assertNotNull(ReDateUtil.toTimestamp("2016/7/8")); System.out.println("\"2016/7/8\":" + ReDateUtil.toTimestamp("2016/7/8"));
        assertNotNull(ReDateUtil.toTimestamp("2016/07/08")); System.out.println("\"2016/07/08\":" + ReDateUtil.toTimestamp("2016/07/08"));
        assertNotNull(ReDateUtil.toTimestamp("2016-7-8")); System.out.println("\"2016-7-8\":" + ReDateUtil.toTimestamp("2016-7-8"));
        assertNotNull(ReDateUtil.toTimestamp("2016-07-08")); System.out.println("\"2016-07-08\":" + ReDateUtil.toTimestamp("2016-07-08"));
        assertNotNull(ReDateUtil.toTimestamp("2016-07-08 18:12:45")); System.out.println("\"2016-07-08 18:12:45\":" + ReDateUtil.toTimestamp("2016-07-08 18:12:45"));
        assertNotNull(ReDateUtil.toTimestamp("2016/07/08",          "yyyy/MM/dd")); System.out.println("\"2016/07/08\",\"yyyy/MM/dd\":" + ReDateUtil.toTimestamp("2016/07/08", "yyyy/MM/dd"));
        assertNotNull(ReDateUtil.toTimestamp("2016-07-08",          "yyyy-MM-dd")); System.out.println("\"2016-07-08\",\"yyyy-MM-dd\":" + ReDateUtil.toTimestamp("2016-07-08", "yyyy-MM-dd"));
        assertNotNull(ReDateUtil.toTimestamp("20160708",            "yyyyMMdd")); System.out.println("\"20160708\",\"yyyyMMdd\":" + ReDateUtil.toTimestamp("20160708", "yyyyMMdd"));
        assertNotNull(ReDateUtil.toTimestamp("2016-07-08 18:12:45", "yyyy-MM-dd HH:mm:ss")); System.out.println("\"2016-07-08 18:12:45\",\"yyyy-MM-dd HH:mm:ss\":" + ReDateUtil.toTimestamp("2016-07-08 18:12:45", "yyyy-MM-dd HH:mm:ss"));
        assertNotNull(ReDateUtil.toTimestamp("20160708181245",      "yyyyMMddHHmmss")); System.out.println("\"20160708181245\",\"yyyyMMddHHmmss\":" + ReDateUtil.toTimestamp("20160708181245", "yyyyMMddHHmmss"));
        assertNotNull(ReDateUtil.toTimestamp("2016-07-08 18:12:45", "yyyy-MM-dd")); System.out.println("\"2016-07-08 18:12:45\",\"yyyy-MM-dd\":" + ReDateUtil.toTimestamp("2016-07-08 18:12:45", "yyyy-MM-dd"));
        assertNotNull(ReDateUtil.toTimestamp("2016/07/08 18:12:45", "yyyy/MM/dd")); System.out.println("\"2016/07/08 18:12:45\",\"yyyy/MM/dd\":" + ReDateUtil.toTimestamp("2016/07/08 18:12:45", "yyyy/MM/dd"));

        assertNotNull(ReDateUtil.toTimestamp(ReDateUtil.nowDt())); System.out.println("ReDateUtil.nowDt():" + ReDateUtil.toTimestamp(ReDateUtil.nowDt()));
        assertNotNull(ReDateUtil.toTimestamp(LocalDate.now())); System.out.println("LocalDate.now():" + ReDateUtil.toTimestamp(LocalDate.now()));
        assertNotNull(ReDateUtil.toTimestamp(LocalDateTime.now())); System.out.println("LocalDateTime.now():" + ReDateUtil.toTimestamp(LocalDateTime.now()));
    }

    @Test
    public void toLocalDateTest() {
        System.out.println("◆◆◆ toLocalDate() ◆◆◆");
        Date date;
        String str;

        // NG
        date = null;
        assertNull(ReDateUtil.toLocalDate(date));
        str = null;
        assertNull(ReDateUtil.toLocalDate(str));
        str = "";
        assertNull(ReDateUtil.toLocalDate(str));

        // OK
        assertNotNull(ReDateUtil.toLocalDate("2016/07/08 18:12:45")); System.out.println("String:" + ReDateUtil.toLocalDate("2016/07/08 18:12:45") );
        assertNotNull(ReDateUtil.toLocalDate(ReDateUtil.toDate("2016/07/08 18:12:45"))); System.out.println("Date:" + ReDateUtil.toLocalDate("2016/07/08 18:12:45") );
        assertNotNull(ReDateUtil.toLocalDate(ReDateUtil.toTimestamp("2016/07/08 18:12:45"))); System.out.println("Timestamp:" + ReDateUtil.toLocalDate("2016/07/08 18:12:45") );
    }

    @Test
    public void toLocalDateTimeTest() {
        System.out.println("◆◆◆ toLocalDateTime() ◆◆◆");
        Date date;
        String str;

        // NG
        date = null;
        assertNull(ReDateUtil.toLocalDateTime(date));
        str = null;
        assertNull(ReDateUtil.toLocalDateTime(str));
        str = "";
        assertNull(ReDateUtil.toLocalDateTime(str));

        // OK
        assertNotNull(ReDateUtil.toLocalDateTime("2016/07/08 18:12:45")); System.out.println("String:" + ReDateUtil.toLocalDateTime("2016/07/08 18:12:45") );
        assertNotNull(ReDateUtil.toLocalDateTime(ReDateUtil.toDate("2016/07/08 18:12:45"))); System.out.println("Date:" + ReDateUtil.toLocalDateTime("2016/07/08 18:12:45") );
        assertNotNull(ReDateUtil.toLocalDateTime(ReDateUtil.toTimestamp("2016/07/08 18:12:45"))); System.out.println("Timestamp:" + ReDateUtil.toLocalDateTime("2016/07/08 18:12:45") );
    }



    @Test
    public void nowTsTest() {
        System.out.println("◆◆◆ nowTs() ◆◆◆");
        SimpleDateFormat sdf = new SimpleDateFormat(ReDateUtil.DATETIME_FORMAT_S);
        Date now = new Date(System.currentTimeMillis());
        String actual = sdf.format(ReDateUtil.nowTs());
        System.out.println("　now：" + actual);
        assertEquals(sdf.format(now), actual);
    }

    @Test
    public void nowDtTest() {
        System.out.println("◆◆◆ nowDt() ◆◆◆");
        SimpleDateFormat sdf = new SimpleDateFormat(ReDateUtil.DATETIME_FORMAT_S);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String actual = sdf.format(ReDateUtil.nowDt());
        System.out.println("　now：" + actual);
        assertEquals(sdf.format(now), actual);
    }

    @Test
    public void nowStrTest() {
        System.out.println("◆◆◆ nowStr() ◆◆◆");
        SimpleDateFormat sdf = new SimpleDateFormat(ReDateUtil.DATE_FORMAT_S);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String actual = ReDateUtil.nowStr();
        System.out.println("　now：" + actual);
        assertEquals(sdf.format(now), actual);
        assertEquals("", ReDateUtil.nowStr(null));
        assertEquals("", ReDateUtil.nowStr(""));
    }

    @Test
    public void getLastDayOfTheMonthTest() {
        System.out.println("◆◆◆ getLastDayOfTheMonth() ◆◆◆");
        Date actual;
        String expected;

        expected = "2016/01/31";
        actual = ReDateUtil.getLastDayOfTheMonth(ReDateUtil.toDate("2016/01/01"));
        assertEquals(expected, ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));
        System.out.println("\""+expected+"\":" + ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));

        expected = "2016/02/29";
        actual = ReDateUtil.getLastDayOfTheMonth(ReDateUtil.toDate("2016/02/01"));
        assertEquals(expected, ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));
        System.out.println("\""+expected+"\":" + ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));

        expected = "2016/03/31";
        actual = ReDateUtil.getLastDayOfTheMonth(ReDateUtil.toDate("2016/03/01"));
        assertEquals(expected, ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));
        System.out.println("\""+expected+"\":" + ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));

        expected = "2016/04/30";
        actual = ReDateUtil.getLastDayOfTheMonth(ReDateUtil.toDate("2016/04/01"));
        assertEquals(expected, ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));
        System.out.println("\""+expected+"\":" + ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));

        expected = "2016/05/31";
        actual = ReDateUtil.getLastDayOfTheMonth(ReDateUtil.toDate("2016/05/01"));
        assertEquals(expected, ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));
        System.out.println("\""+expected+"\":" + ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));

        expected = "2016/06/30";
        actual = ReDateUtil.getLastDayOfTheMonth(ReDateUtil.toDate("2016/06/01"));
        assertEquals(expected, ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));
        System.out.println("\""+expected+"\":" + ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));

        expected = "2016/07/31";
        actual = ReDateUtil.getLastDayOfTheMonth(ReDateUtil.toDate("2016/07/01"));
        assertEquals(expected, ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));
        System.out.println("\""+expected+"\":" + ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));

        expected = "2016/08/31";
        actual = ReDateUtil.getLastDayOfTheMonth(ReDateUtil.toDate("2016/08/01"));
        assertEquals(expected, ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));
        System.out.println("\""+expected+"\":" + ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));

        expected = "2016/09/30";
        actual = ReDateUtil.getLastDayOfTheMonth(ReDateUtil.toDate("2016/09/01"));
        assertEquals(expected, ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));
        System.out.println("\""+expected+"\":" + ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));

        expected = "2016/10/31";
        actual = ReDateUtil.getLastDayOfTheMonth(ReDateUtil.toDate("2016/10/01"));
        assertEquals(expected, ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));
        System.out.println("\""+expected+"\":" + ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));

        expected = "2016/11/30";
        actual = ReDateUtil.getLastDayOfTheMonth(ReDateUtil.toDate("2016/11/01"));
        assertEquals(expected, ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));
        System.out.println("\""+expected+"\":" + ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));

        expected = "2016/12/31";
        actual = ReDateUtil.getLastDayOfTheMonth(ReDateUtil.toDate("2016/12/01"));
        assertEquals(expected, ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));
        System.out.println("\""+expected+"\":" + ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));

        expected = "2017/02/28";
        actual = ReDateUtil.getLastDayOfTheMonth(ReDateUtil.toDate("2017/02/01"));
        assertEquals(expected, ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));
        System.out.println("\""+expected+"\":" + ReDateUtil.format(actual, ReDateUtil.DATE_FORMAT_S));

        // NG.
        assertEquals(null, ReDateUtil.getLastDayOfTheMonth(null));
    }

    @Test
    public void calcAgeTest() {
        System.out.println("◆◆◆ calcAge() ◆◆◆");
        Date birthday;
        Date now ;
        {
            birthday = ReDateUtil.toDate("1976/09/01");
            now = ReDateUtil.toDate("2016/08/31");
            assertEquals(39, ReDateUtil.calcAge(birthday, now));
        }
        {
            birthday = ReDateUtil.toDate("1976/09/01");
            now = ReDateUtil.toDate("2016/09/01");
            assertEquals(40, ReDateUtil.calcAge(birthday, now));
        }
        {
            birthday = ReDateUtil.toDate("1984/02/29");
            now = ReDateUtil.toDate("2016/03/01");
            assertEquals(32, ReDateUtil.calcAge(birthday, now));
        }
        {
            now = ReDateUtil.toDate("2016/02/28");
            assertEquals(31, ReDateUtil.calcAge("1984/02/29", now));
        }
        {
            birthday = ReDateUtil.toDate("1976/04/29");
            System.out.println("calcAge():" + ReDateUtil.calcAge(birthday));
            System.out.println("calcAge():" + ReDateUtil.calcAge("1976/04/29"));
        }
        // NG
        {
            birthday = null;
            assertEquals(-1, ReDateUtil.calcAge(birthday, null));
        }
        {
            String str = null;
            assertEquals(-1, ReDateUtil.calcAge(str, null));
        }
        {
            birthday = null;
            assertEquals(-1, ReDateUtil.calcAge(birthday));
        }
        {
            String str = null;
            assertEquals(-1, ReDateUtil.calcAge(str));
        }

     }

    @Test
    public void formatTest() {
        System.out.println("◆◆◆ format() ◆◆◆");
        Date date;
        String dateValue;
        String pattern;

        //NG
        dateValue = null;
        pattern = null;
        assertEquals(null, ReDateUtil.format(dateValue, pattern));

        dateValue = "";
        pattern = "";
        assertEquals("", ReDateUtil.format(dateValue, pattern));

        date = null;
        pattern = "";
        assertEquals(null, ReDateUtil.format(date, pattern));
        assertEquals(null, ReDateUtil.format(date, pattern));

        dateValue = "sdfsdf";
        pattern = "yyyy-MM-dd";
        assertEquals(dateValue, ReDateUtil.format(dateValue, pattern));

        //OK
        dateValue = "2016/07/08";
        pattern = "yyyy-MM-dd";
        assertEquals("2016-07-08", ReDateUtil.format(dateValue, pattern));

        dateValue = "2016-07-08";
        pattern = "yyyy/MM/dd";
        assertEquals("2016/07/08", ReDateUtil.format(dateValue, pattern));

        dateValue = "2016-07-08";
        pattern = "yyyy-MM-dd HH:mm:ss";
        assertEquals("2016-07-08 00:00:00", ReDateUtil.format(dateValue, pattern));

        dateValue = "2016-07-08 18:12:45";
        pattern = "yyyy/MM/dd HH:mm:ss";
        assertEquals("2016/07/08 18:12:45", ReDateUtil.format(dateValue, pattern));

        dateValue = "2016/07/08 18:12:45";
        pattern = "yyyy-MM-dd";
        assertEquals("2016-07-08", ReDateUtil.format(dateValue, pattern));

        pattern = "yyyy-MM-dd HH:mm:ss";
        System.out.println("String(yyyy-MM-dd HH:mm:ss):" + ReDateUtil.format(ReDateUtil.nowStr("yyyy-MM-dd HH:mm:ss"), pattern));
        System.out.println("Date(yyyy-MM-dd HH:mm:ss):" + ReDateUtil.format(ReDateUtil.nowDt(), pattern));
        System.out.println("Timestamp(yyyy-MM-dd HH:mm:ss):" + ReDateUtil.format(ReDateUtil.nowTs(), pattern));
        System.out.println("LocalDate(yyyy-MM-dd HH:mm:ss):" + ReDateUtil.format(LocalDate.now(), pattern));
        System.out.println("LocalDateTime(yyyy-MM-dd HH:mm:ss):" + ReDateUtil.format(LocalDateTime.now(), pattern));
    }

}
