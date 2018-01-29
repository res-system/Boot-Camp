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

import java.math.BigDecimal;

/**
 * <pre>
 * 入力チェック共通処理クラス.
 *
 * 文字列の入力チェックを行います.
 * </pre>
 * @author res.
 */
public class ReChecker {

    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    private ReChecker() {}



    //---------------------------------------------- static [public] 必須.
    /**
     * 値がNULL又は空である事を確認します.
     * @param value 確認対象の値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkEmpty(String value) {
        return ReUtil.isEmpty(value);
    }

    /**
     * 値がNULL又は空で無い事を確認します.
     * @param value 確認対象の値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkNotEmpty(String value) {
        return !ReUtil.isEmpty(value);
    }



    //---------------------------------------------- static [public] サイズ.
    /**
     * 指定の文字列のバイトサイズを確認します.
     * @param value 確認対象の値.
     * @param max 最大値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkByteSize(String value, int max) {
        return checkByteSize(value, -1, max);
    }

    /**
     * 指定の文字列のバイトサイズを確認します.
     * @param value 確認対象の値.
     * @param max 最大値.
     * @param charset 文字コード.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkByteSize(String value, int max, String charset) {
        return checkByteSize(value, -1, max, charset);
    }

    /**
     * 指定の文字列のバイトサイズを確認します.
     * @param value 確認対象の値.
     * @param min 最小値.
     * @param max 最大値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkByteSize(String value, int min, int max) {
        return checkByteSize(value, min, max, ReUtil.CHARSET);
    }

    /**
     * 指定の文字列のバイトサイズを確認します.
     * @param value 確認対象の値.
     * @param min 最小値.
     * @param max 最大値.
     * @param charset 文字コード.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkByteSize(String value, int min, int max, String charset) {
        if (checkEmpty(value)) {
            return true;
        }
        int length = ReUtil.byteLen(value, charset);
        if (min <= length && length <= max) {
            return true;
        }
        return false;
    }


    /**
     * 指定の文字列サイズを確認します.
     * @param value 確認対象の値.
     * @param max 最大値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkLength(String value, int max) {
        return checkLength(value, -1, max);
    }

    /**
     * 指定の文字列サイズを確認します.
     * @param value 確認対象の値.
     * @param min 最小値.
     * @param max 最大値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkLength(String value, int min, int max) {
        if (checkEmpty(value)) {
            return true;
        }
        int length = ReUtil.strLen(value);
        if (min <= length && length <= max) {
            return true;
        }
        return false;
    }



    //---------------------------------------------- static [public] 正規表現.
    /**
     * 正義表現のパターンチェックを行います.
     * @param value 確認対象の値.
     * @param regex パターン.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkMatch(String value, String regex) {
        if (checkEmpty(value)) {
            return true;
        }
        return ReUtil.isMatch(value, regex);
    }

    /**
     * 値が数値である事を確認します.
     * @param value 確認対象の値.
     * @param exclusion 除外文字列（正規表現）.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkNumeric(String value) {
        if (checkEmpty(value)) {
            return true;
        }
        return ReUtil.isNumeric(value);
    }

    /**
     * 値が数字である事を確認します.
     * @param value 確認対象の値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkNumber(String valuen) {
        return checkNumber(valuen, "");
    }

    /**
     * 値が数字である事を確認します.
     * @param value 確認対象の値.
     * @param exclusion 除外文字列（正規表現）.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkNumber(String value, String exclusion) {
        if (checkEmpty(value)) {
            return true;
        }
        if (exclusion == null) {
            exclusion = "";
        }
        return checkMatch(value, "^[0-9" + exclusion + "]+$");
    }


    /**
     * 値がアルファベットである事を確認します.
     * @param value 確認対象の値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkAlpha(String value) {
        return checkAlpha(value, "");
    }

    /**
     * 値がアルファベットである事を確認します.
     * @param value 確認対象の値.
     * @param exclusion 除外文字列（正規表現）.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkAlpha(String value, String exclusion) {
        if (checkEmpty(value)) {
            return true;
        }
        if (exclusion == null) {
            exclusion = "";
        }
        return checkMatch(value, "^[a-zA-Z" + exclusion + "]+$");
    }

    /**
     * 値がアルファベット又は数字である事を確認します.
     * @param value 確認対象の値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkAlphaNumber(String value) {
        return checkAlphaNumber(value, "");
    }

    /**
     * 値がアルファベット又は数字である事を確認します.
     * @param value 確認対象の値.
     * @param exclusion 除外文字列（正規表現）.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkAlphaNumber(String value, String exclusion) {
        if (checkEmpty(value)) {
            return true;
        }
        if (exclusion == null) {
            exclusion = "";
        }
        return checkMatch(value, "^[a-zA-Z0-9" + exclusion + "]+$");
    }

    /**
     * 値がASCII文字である事を確認します.
     * @param value 確認対象の値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkAscii(String value) {
        if (checkEmpty(value)) {
            return true;
        }
        return checkMatch(value, "^[\\x00-\\x7F]+$");
    }

    /**
     * 値が全角文字である事を確認します.
     * @param value 確認対象の値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkEm(String value) {
        if (checkEmpty(value)) {
            return true;
        }
        return checkMatch(value, "^[^ -~｡-ﾟ]+$");
    }

    /**
     * 値が全角カナ文字である事を確認します.
     * @param value 確認対象の値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkKana(String value) {
        return checkKana(value, "");
    }

    /**
     * 値が全角カナ文字である事を確認します.
     * @param value 確認対象の値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkKana(String value, String exclusion) {
        if (checkEmpty(value)) {
            return true;
        }
        if (exclusion == null) {
            exclusion = "";
        }
        return checkMatch(value, "^[ァアィイゥウェエォオカガキギクグケゲコゴサザシジスズセゼソゾタダチヂッツヅテデトドナニヌネノハバパヒビピフブプヘベペホボポマミムメモャヤュユョヨラリルレロヮワヰヱヲンヴヵヶー゛゜" + exclusion + "]+$");
    }



    //---------------------------------------------- static [public] 数値.
    /**
     * 数値のサイズを確認します.
     * @param value 確認対象の数値.
     * @param max 最大値(BigDecimalで変換可能な数値の範囲で設定する).
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkNumericSize(String value, String max) {
        if (checkEmpty(value) || !checkNumeric(value)) {
            return true;
        }
        if (ReUtil.isNumeric(max)) {
            BigDecimal maxB = new BigDecimal(max);
            BigDecimal valueB = new BigDecimal(value);
            if (valueB.compareTo(maxB) <= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 数値のサイズを確認します.
     * @param value 確認対象の数値.
     * @param min 最小値(BigDecimalで変換可能な数値の範囲で設定する).
     * @param max 最大値(BigDecimalで変換可能な数値の範囲で設定する).
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkNumericSize(String value, String min, String max) {
        if (checkEmpty(value) || !checkNumeric(value)) {
            return true;
        }
        if (ReUtil.isNumeric(min)
                && ReUtil.isNumeric(max)) {
            BigDecimal minB = new BigDecimal(min);
            BigDecimal maxB = new BigDecimal(max);
            BigDecimal valueB = new BigDecimal(value);
            if (minB.compareTo(valueB) <= 0 && valueB.compareTo(maxB) <= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 値がNULL又は空又は「0」で無い事を確認します.
     * @param value 確認対象の値（数値）.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkNotZero(String value) {
        if (checkEmpty(value) || !checkNumeric(value)) {
            return true;
        }
        BigDecimal num = new BigDecimal(value.trim());
        if (num.compareTo(BigDecimal.ZERO) != 0) {
            return true;
        }
        return false;
    }

    /**
     * 値がマイナスで無い事を確認します.
     * @param value 確認対象の値（数値）.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkNotMinus(String value) {
        if (checkEmpty(value) || !checkNumeric(value)) {
            return true;
        }
        BigDecimal num = new BigDecimal(value.trim());
        if (num.compareTo(BigDecimal.ZERO) >= 0) {
            return true;
        }
        return false;
    }

    /**
     * 値が小数点ありの桁数の確認をします.
     * @param value 確認対象の値（数値）.
     * @param integerPart 整数部の桁数.
     * @param decimalPart 小数部の桁数.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkDecimalSize(String value, int integerPart, int decimalPart) {
        if (checkEmpty(value) || !checkNumeric(value)) {
            return true;
        }
        if (integerPart >= 0 && decimalPart >= 0) {
            if (value.indexOf(".") >= 0) {
                // 小数部あり.
                String[] num = value.split("\\.");
                if (0 < num[0].length() && num[0].length() <= integerPart
                        && 0 < num[1].length() && num[1].length() <= decimalPart) {
                    return true;
                }
            } else {
                // 小数部なし.
                if (0 < value.length() && value.length() <= integerPart) {
                    return true;
                }
            }
        }
        return false;
    }



    //---------------------------------------------- static [public] 日付.
    /**
     * 値が日付である事を確認します.
     * @param value 確認対象の値.
     * @param format フォーマット.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkDateFormat(String value, String format) {
        if (checkEmpty(value)) {
            return true;
        }
        if (checkNotEmpty(format) && checkLength(value, format.length())) {
            return ReDateUtil.check(value, format);
        }
        return false;
    }

    /**
     * 値が日付である事を確認します.
     * @param value 確認対象の値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkDateFormat(String value) {
        if (checkNotEmpty(value)) {
            value = value.replace('/', '-');
        }
        return checkDateFormat(value, ReDateUtil.DATE_FORMAT_H);
    }

    /**
     * 値が日時である事を確認します.
     * @param value 確認対象の値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkDateTimeFormat(String value) {
        if (checkNotEmpty(value)) {
            value = value.replace('/', '-');
        }
        return checkDateFormat(value, ReDateUtil.DATETIME_FORMAT_H);
    }

    /**
     * 値が時刻である事を確認します.
     * @param value 確認対象の値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkTimeFormat(String value) {
        return checkDateFormat(value, ReDateUtil.TIME_FORMAT_C);
    }

}
