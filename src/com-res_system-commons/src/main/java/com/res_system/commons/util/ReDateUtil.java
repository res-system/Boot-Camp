/**
 *   Copyright (c) 2018 株式会社リスタート.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.res_system.commons.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

/**
 * <pre>
 * 日付共通処理クラス.
 *
 * 日付に関する共通処理を行います.
 * </pre>
 * @author res.
 */
public class ReDateUtil {

    //---------------------------------------------- const [public].
    /** 時刻フォーマット[HH:mm:ss]. */
    public static final String TIME_FORMAT_C = "HH:mm:ss";
    /** 時刻フォーマット[HHmmss]. */
    public static final String TIME_FORMAT_N = "HHmmss";
    /** 日付フォーマット[yyyy-MM-dd]. */
    public static final String DATE_FORMAT_H = "yyyy-MM-dd";
    /** 日付フォーマット[yyyy/MM/dd]. */
    public static final String DATE_FORMAT_S = "yyyy/MM/dd";
    /** 日付フォーマット[yyyyMMdd]. */
    public static final String DATE_FORMAT_N = "yyyyMMdd";
    /** 日付フォーマット[yyyy-MM-dd HH:mm:ss]. */
    public static final String DATETIME_FORMAT_H = DATE_FORMAT_H + " " + TIME_FORMAT_C;
    /** 日付フォーマット[yyyy/MM/dd HH:mm:ss]. */
    public static final String DATETIME_FORMAT_S = DATE_FORMAT_S + " " + TIME_FORMAT_C;
    /** 日付フォーマット[yyyyMMddHHmmss]. */
    public static final String DATETIME_FORMAT_N = DATE_FORMAT_N + TIME_FORMAT_N;



    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    private ReDateUtil() {}



    //---------------------------------------------- static [public] チェック・変換処理.
    /**
     * 値が日付である事を確認します.
     * @param value 確認対象の値.
     * @param format フォーマット.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean check(String value, String format) {
        if (value != null && value.length() > 0
                && format != null && format.length() > 0) {
            try {
                DateFormat df = new SimpleDateFormat(format);
                df.setLenient(false);
                df.parse(value);
                return true;
            } catch (Exception  e) {}
        }
        return false;
    }


    /**
     * 日付に変換します.
     * @param value 対象の値.
     * @return 変換後の値.
     */
    public static Date toDate(String value) {
       if (value != null && value.length() > 0) {
           value = value.replace('/', '-');
           if (value.length() > 10) {
               return toDate(value, ReDateUtil.DATETIME_FORMAT_H);
           } else {
               return toDate(value, ReDateUtil.DATE_FORMAT_H);
           }
       }
       return null;
    }

    /**
     * 日付に変換します.
     * @param value 対象の値.
     * @param format フォーマット.
     * @return 変換後の値.
     */
    public static Date toDate(String value, String format) {
        if (value != null && value.length() > 0
                && format != null && format.length() > 0) {
            try {
                DateFormat df = new SimpleDateFormat(format);
                df.setLenient(false);
                return new Date(df.parse(value).getTime());
            } catch (Exception  e) {}
        }
        return null;
    }

    /**
     * 日付に変換します.
     * @param value 対象の値.
     * @return 変換後の値.
     */
    public static Date toDate(final Timestamp value) {
        if (value != null) {
            return new Date(value.getTime());
        }
        return null;
    }

    /**
     * 日付に変換します.
     * @param value 対象の値.
     * @return 変換後の値.
     */
    public static Date toDate(final LocalDate value) {
        if (value != null) {
            return Date.valueOf(value);
        }
        return null;
    }

    /**
     * 日付に変換します.
     * @param value 対象の値.
     * @return 変換後の値.
     */
    public static Date toDate(final LocalDateTime value) {
        if (value != null) {
            return Date.valueOf(value.toLocalDate());
        }
        return null;
    }


    /**
     * タイムスタンプに変換します.
     * @param value 対象の値.
     * @return 変換後の値.
     */
    public static Timestamp toTimestamp(String value) {
        return toTimestamp(toDate(value));
    }

    /**
     * タイムスタンプに変換します.
     * @param value 対象の値.
     * @param format フォーマット.
     * @return 変換後の値.
     */
    public static Timestamp toTimestamp(String value, String format) {
        return toTimestamp(toDate(value, format));
    }

    /**
     * タイムスタンプに変換します.
     * @param value 対象の値.
     * @return 変換後の値.
     */
    public static Timestamp toTimestamp(final Date value) {
        if (value != null) {
            try {
                return new Timestamp(value.getTime());
            } catch(Exception e){
                return null;
            }
        }
        return null;
    }

    /**
     * タイムスタンプに変換します.
     * @param value 対象の値.
     * @return 変換後の値.
     */
    public static Timestamp toTimestamp(final LocalDateTime value) {
        if (value != null) {
            return Timestamp.valueOf(value);
        }
        return null;
    }

    /**
     * タイムスタンプに変換します.
     * @param value 対象の値.
     * @return 変換後の値.
     */
    public static Timestamp toTimestamp(final LocalDate value) {
        if (value != null) {
            return Timestamp.valueOf(value.atTime(0, 0, 0));
        }
        return null;
    }


    /**
     * LocalDateに変換します.
     * @param value 対象の値.
     * @return 変換後の値.
     */
    public static LocalDate toLocalDate(String value) {
        return toLocalDate(toDate(value));
    }

    /**
     * LocalDateに変換します.
     * @param value 対象の値.
     * @param format フォーマット.
     * @return 変換後の値.
     */
    public static LocalDate toLocalDate(String value, String format) {
        return toLocalDate(toDate(value, format));
    }

    /**
     * LocalDateに変換します.
     * @param value 対象の値.
     * @return 変換後の値.
     */
    public static LocalDate toLocalDate(final Date value) {
        if (value != null) {
            return value.toLocalDate();
        }
        return null;
    }

    /**
     * LocalDateに変換します.
     * @param value 対象の値.
     * @return 変換後の値.
     */
    public static LocalDate toLocalDate(final Timestamp value) {
        if (value != null) {
            return value.toLocalDateTime().toLocalDate();
        }
        return null;
    }


    /**
     * LocalDateに変換します.
     * @param value 対象の値.
     * @return 変換後の値.
     */
    public static LocalDateTime toLocalDateTime(String value) {
        return toLocalDateTime(toTimestamp(value));
    }

    /**
     * LocalDateに変換します.
     * @param value 対象の値.
     * @param format フォーマット.
     * @return 変換後の値.
     */
    public static LocalDateTime toLocalDateTime(String value, String format) {
        return toLocalDateTime(toTimestamp(value, format));
    }

    /**
     * LocalDateTimeに変換します.
     * @param value 対象の値.
     * @return 変換後の値.
     */
    public static LocalDateTime toLocalDateTime(final Date value) {
        if (value != null) {
            return value.toLocalDate().atTime(0, 0, 0);
        }
        return null;
    }

    /**
     * LocalDateTimeに変換します.
     * @param value 対象の値.
     * @return 変換後の値.
     */
    public static LocalDateTime toLocalDateTime(final Timestamp value) {
        if (value != null) {
            return value.toLocalDateTime();
        }
        return null;
    }



    //---------------------------------------------- static [public] システム日付関連.
    /**
     * システム日付を取得します.
     * @return システム日付（Timestamp）.
     */
    public static Timestamp nowTs() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * システム日付を取得します.
     * @return システム日付（Date）.
     */
    public static Date nowDt() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * システム日付を取得します.
     * @param pattern フォーマットパターン.
     * @return システム日付（String）.
     */
    public static String nowStr(String pattern) {
        if (pattern != null && pattern.length() > 0) {
            DateFormat df = new SimpleDateFormat(pattern);
            return df.format(nowDt());
        }
        return "";
    }

    /**
     * システム日付を取得します.
     * @return システム日付（String[yyyy/MM/dd]）.
     */
    public static String nowStr() {
        return nowStr(DATE_FORMAT_S);
    }



    //---------------------------------------------- static [public] 日付計算.
    /**
     * 月末日を取得します.
     * @param date 対象日付.
     * @return 加算後の日付（Date）.
     */
    public static Date getLastDayOfTheMonth(Date date) {
        if (date != null) {
            return toDate(toLocalDate(date).with(TemporalAdjusters.lastDayOfMonth()));
        }
        return date;
    }


    /**
     * 年齢を算出します.
     * @param birthday 生年月日.
     * @param now 基準日.
     * @return 年齢.
     */
    public static int calcAge(Date birthday, Date now) {
        if (birthday != null && now != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            return (Integer.parseInt(sdf.format(now)) - Integer.parseInt(sdf.format(birthday))) / 10000;
        }
        return -1;
    }

    /**
     * 年齢を算出します（時刻基準）.
     * @param birthday 生年月日.
     * @return 年齢.
     */
    public static int calcAge(Date birthday) {
        return calcAge(birthday, nowDt());
    }

    /**
     * 年齢を算出します（時刻基準）.
     * @param now 基準日.
     * @param birthday 生年月日.
     * @return 年齢.
     */
    public static int calcAge(String birthday, Date now) {
        return calcAge(toDate(birthday), now);
    }

    /**
     * 年齢を算出します（時刻基準）.
     * @param birthday 生年月日.
     * @return 年齢.
     */
    public static int calcAge(String birthday) {
        return calcAge(toDate(birthday), nowDt());
    }



    //---------------------------------------------- static [public] フォーマット.
    /**
     * 日付のフォーマットを行います.
     * @param dateValue 対象日付.
     * @param pattern フォーマットパターン.
     * @return フォーマット後の日付.
     */
    public static String format(String dateValue, String pattern) {
        Date date = toDate(dateValue);
        if (date != null) {
            return format(date, pattern);
        }
        return dateValue;
    }

    /**
     * 日付のフォーマットを行います.
     * @param dateValue 対象日付.
     * @param pattern フォーマットパターン.
     * @return フォーマット後の日付.
     */
    public static String format(Date dateValue, String pattern) {
        if (dateValue != null && pattern != null) {
            DateFormat df = new SimpleDateFormat(pattern);
            return df.format(dateValue);
        }
        return null;
    }

    /**
     * 日付のフォーマットを行います.
     * @param dateValue 対象日付.
     * @param pattern フォーマットパターン.
     * @return フォーマット後の日付.
     */
    public static String format(Timestamp dateValue, String pattern) {
        if (dateValue != null && pattern != null) {
            DateFormat df = new SimpleDateFormat(pattern);
            return df.format(dateValue);
        }
        return null;
    }

    /**
     * 日付のフォーマットを行います.
     * @param dateValue 対象日付.
     * @param pattern フォーマットパターン.
     * @return フォーマット後の日付.
     */
    public static String format(LocalDate dateValue, String pattern) {
        if (dateValue != null && pattern != null) {
            DateFormat df = new SimpleDateFormat(pattern);
            return df.format(toDate(dateValue));
        }
        return null;
    }

    /**
     * 日付のフォーマットを行います.
     * @param dateValue 対象日付.
     * @param pattern フォーマットパターン.
     * @return フォーマット後の日付.
     */
    public static String format(LocalDateTime dateValue, String pattern) {
        if (dateValue != null && pattern != null) {
            DateFormat df = new SimpleDateFormat(pattern);
            return df.format(toTimestamp(dateValue));
        }
        return null;
    }

}
