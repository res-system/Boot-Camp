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

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * 共通処理クラス.
 *
 * 共通処理を行います.
 * </pre>
 * @author res.
 */
public final class ReUtil {

    //---------------------------------------------- const [public].
    /** プロパティファイル名. */
    public static final String PROPERTY_FILE_NAME = "com-res_system-commons";
    /** プロパティファイル名. */
    public static final String PROPERTYKEY_CHARSET = "charset";
    /** 文字コード */
    public static final String CHARSET;
    //---------------------------------------------- initializer.
    static {
        CHARSET = getPropertyValue(PROPERTY_FILE_NAME, PROPERTYKEY_CHARSET, "UTF-8");
    }



    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    private ReUtil() {}


    
    
    

    //---------------------------------------------- static [public] プロパティファイル処理.
    /**
     * プロパティファイルより指定のキーの値を取得します.
     * @param fileName ファイル名.
     * @param key キー.
     * @return 値.
     */
    public static String getPropertyValue(String fileName, String key) {
        return getPropertyValue(fileName, key, "");
    }

    /**
     * プロパティファイルより指定のキーの値を取得します.
     * @param fileName ファイル名.
     * @param key キー.
     * @param defaultValue デフォルト値.
     * @return 値.
     */
    public static String getPropertyValue(String fileName, String key, String defaultValue) {
        String retValue;
        try {
            ResourceBundle rb = ResourceBundle.getBundle(fileName);
            retValue = getPropertyString(rb, key, defaultValue);
        } catch (MissingResourceException e) {
            // ファイルがない場合は、無視する.
            retValue = defaultValue;
        }
        return retValue;
    }

    /**
     * ResourceBundleより指定のキーの値を取得します.
     * @param rb ResourceBundle
     * @param key キー.
     * @return 値.
     */
    public static String getPropertyString(ResourceBundle rb, String key) {
        return getPropertyString(rb, key, "");
    }

    /**
     * ResourceBundleより指定のキーの値を取得します.
     * @param rb ResourceBundle
     * @param key キー.
     * @param defaultValue デフォルト値.
     * @return 値.
     */
    public static String getPropertyString(ResourceBundle rb, String key, String defaultValue) {
        if (rb.containsKey(key)) {
            String value = rb.getString(key);
            if (value != null && value.length() > 0) {
                return value;
            }
        }
        return defaultValue;
    }



    //---------------------------------------------- static [public] チェック処理.
    /**
     * 値がNULL又は空である事を確認します.
     * @param id 確認対象の値(ID).
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean isEmpty(Long id) {
        if (id == null || id <= 0L) {
            return true;
        }
        return false;
    }

    /**
     * 値がNULL又は空である事を確認します.
     * @param value 確認対象の値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean isEmpty(String value) {
        if (value == null || value.length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 値がNULL又は空である事を確認します.
     * @param value 確認対象の値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean isEmpty(StringBuilder value) {
        if (value == null || value.length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 値がNULL又は空である事を確認します.
     * @param value 確認対象の値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean isEmpty(String[] values) {
        if (values == null || values.length == 0) {
            return true;
        }
        return false;
    }

    /**
     * 値がNULL又は空である事を確認します.
     * @param value 確認対象の値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean isEmpty(List<?> values) {
        if (values == null || values.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 値がNULL又は空である事を確認します.
     * @param value 確認対象の値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean isEmpty(Map<?,?> values) {
        if (values == null || values.size() == 0) {
            return true;
        }
        return false;
    }


    /**
     * 正義表現のパターンチェックを行います.
     * @param value 確認対象の値.
     * @param regex パターン.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean isMatch(String value, String regex) {
        if (isEmpty(value)) {
            return false;
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);
        return m.find();
    }

    /**
     * 値が数値である事を確認します.
     * @param value 確認対象の値.
     * @param exclusion 除外文字列（正規表現）.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean isNumeric(String value) {
        if (isEmpty(value)) {
            return false;
        }
        return isMatch(value, "^[-+]?[0-9]+$")
                || isMatch(value, "^[-+]?[0-9]+[\\.][0-9]+$");
    }



    //---------------------------------------------- static [public] フィールド処理.
    /**
     * <pre>
     * 指定クラスの全フィールドを取得します.
     * (再帰してスーパークラスのフィールドも取得します)
     * (重複を無くす為、Mapで管理します)
     * (getDeclaredFields()が順番を保証しないので注意する！)
     * </pre>
     * @param clazz 対象のクラス.
     * @param fields 取得したフィールドを格納するMap<フィールド名,フィールド>.
     */
    public static void getAllFields(Class<?> clazz, Map<String, Field> fields) {
        if (clazz != null && fields != null) {
            Class<?> superclazz = clazz.getSuperclass();
            if (superclazz != null) {
                getAllFields(superclazz, fields);
            }
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                fields.put(field.getName(), field);
            }
        }
    }



    //---------------------------------------------- static [public] チェックデジット処理.
    /**
     * チェックデジットを取得します（モジュラス11）.
     * @param targetNum 対象数値.
     * @return チェックデジット.
     */
    public static String getCheckDigitModulus11(String targetNum) {
        if (!isEmpty(targetNum) && isNumeric(targetNum)) {
            int total = 0;
            int modulus = 2;
            for (int i = targetNum.length() - 1; i >= 0; i--) {
                total += (Character.getNumericValue(targetNum.charAt(i)) + modulus);
                modulus++;
                if (modulus > 7) {
                    modulus = 2;
                }
            }
            int checkDigit = 11 - (total%11);
            if (checkDigit > 9) {
                checkDigit = 0;
            }
            return String.valueOf(checkDigit);
        }
        return "";
    }

    /**
     * チェックデジットを設定します（モジュラス11）.
     * @param targetNum 対象数値.
     * @return チェックデジット設定後の対象数値.
     */
    public static String setCheckDigitModulus11(String targetNum) {
        if (!isEmpty(targetNum) && isNumeric(targetNum)) {
            targetNum += getCheckDigitModulus11(targetNum);
        }
        return targetNum;
    }

    /**
     * チェックデジットを確認します（モジュラス11）.
     * @param targetNum 対象数値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean checkCheckDigitModulus11(String targetNum) {
        if (!isEmpty(targetNum) && isNumeric(targetNum)) {
            String targetCheckDigit = targetNum.substring(targetNum.length() - 1);
            return targetCheckDigit.equals(getCheckDigitModulus11(targetNum.substring(0, targetNum.length() - 1)));
        }
        return false;
    }



    //---------------------------------------------- static [public] ハッシュ処理.
    /**
     * 指定の文字列のハッシュ化を行います.
     * @param str 対象の文字列.
     * @return 変換後文字列.
     */
    public static String toHashValueSha2(String str) {
        return toHashValue("SHA-256", str);
    }

    /**
     * <pre>
     * 指定の文字列のハッシュ化を行います.
     *
     * (使用可能アルゴリズム)
     * MD2
     * MD5
     * SHA-1
     * SHA-256
     * SHA-384
     * SHA-512
     *
     * </pre>
     * @param algorithm アルゴリズム名.
     * @param str 対象の文字列.
     * @return
     */
    public static String toHashValue(String algorithm, String str) {
        if (!isEmpty(algorithm) && str != null) {
            MessageDigest md;
            try {
                md = MessageDigest.getInstance(algorithm);
            } catch (NoSuchAlgorithmException e) {
                // uncheked.
                throw new RuntimeException(e);
            }
            md.update(str.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : md.digest()) {
                String hex = String.format("%02x", b);
                sb.append(hex);
            }
            return sb.toString();
        }
        return str;
    }



    //---------------------------------------------- static [public] トークン処理.
    /**
     * トークン文字列を作成します.
     * @param tokenLength トークンの長さ(2からの偶数バイト数).
     * @return トークン文字列.
     */
    public static String makeToken(int tokenLength) {
        StringBuilder sb = new StringBuilder();
        if (tokenLength > 1) {
            byte token[] = new byte[tokenLength / 2];
            SecureRandom random = null;
            try {
                random = SecureRandom.getInstance("SHA1PRNG");
                random.nextBytes(token);
                for (int i = 0; i < token.length; i++) {
                    sb.append(String.format("%02x", token[i]));
                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        return sb.toString();
    }



    //---------------------------------------------- static [public] フォーマット処理.
    /**
     * テキストのフォーマットを行います.
     * @param str 対象文字列.
     * @param length 表示文字数.
     * @return 変換後文字列.
     */
    public static String formatText(String str, int length) {
       if (!isEmpty(str)) {
           if (str.length() > length) {
               return str.substring(0, length) + "...";
           }
       }
       return str;
    }

    /**
     * 数値のフォーマットを行います.
     * @param str 対象文字列.
     * @param pattern フォーマットパターン.
     * @return 変換後文字列.
     */
    public static String formatDecimal(String str, String pattern) {
       if (!isEmpty(str) && isNumeric(str)) {
           DecimalFormat df = new DecimalFormat(pattern);
           return df.format(new BigDecimal(str));
       }
       return str;
    }



    //---------------------------------------------- static [public] Base64処理.
    /**
     * Base64文字列を通常の文字列へ変換します.
     * @param str64Url Base64文字列.
     * @return 変換後の文字列.
     */
    public static String fromBase64(String str64Url) {
        if (!isEmpty(str64Url)) {
            return new String(Base64.getDecoder().decode(str64Url));
        }
        return "";
    }

    /**
     * 文字列をBase64へ変換します.
     * @param str 対象の文字列.
     * @return 変換後の文字列.
     */
    public static String toBase64(String str) {
        if (!isEmpty(str)) {
            return Base64.getEncoder().encodeToString(str.getBytes());
        }
        return "";
    }

    /**
     * Base64文字列を通常の文字列へ変換します.
     * @param str64Url Base64文字列.
     * @return 変換後の文字列.
     */
    public static String fromBase64URL(String str64Url) {
        if (!isEmpty(str64Url)) {
            try {
                return fromBase64(URLDecoder.decode(str64Url, ReUtil.CHARSET));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return "";
    }

    /**
     * 文字列をBase64へ変換します.
     * @param str 対象の文字列.
     * @return 変換後の文字列.
     */
    public static String toBase64URL(String str) {
        if (!isEmpty(str)) {
            try {
                return URLEncoder.encode(toBase64(str), ReUtil.CHARSET);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return "";
    }


    //---------------------------------------------- static [public] 文字列処理.
    /**
     * 前後の空白を削除します.(nullの場合は、空文字を返却します)
     * @param value 対象の値.
     * @return 編集後の値.
     */
    public static final String Trim(String value) {
        if (!isEmpty(value)) {
            return value.trim();
        }
        return "";
    }

    /**
     * 空文字を返却します.
     * @param str 対象文字列.
     * @return 文字数.
     */
    public static String toEmpty(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return str;
    }

    /**
     * Nullを返却します.
     * @param str 対象文字列.
     * @return 文字数.
     */
    public static String toNull(String str) {
        if (isEmpty(str)) {
            return null;
        }
        return str;
    }

    /**
     * Stringの文字数を取得します.(サロゲートペア対応)
     * @param str 対象文字列.
     * @return 文字数.
     */
    public static int strLen(String str) {
        if (str != null) {
            return str.codePointCount(0, str.length());
        }
        return 0;
    }

    /**
     * Byte数を取得します.
     * @param str 対象文字列.
     * @param charset 文字コード.
     * @return Byte数.
     */
    public static int byteLen(String str, String charset) {
        if (str != null) {
            byte[] bytes;
            try {
                bytes = str.getBytes(charset);
                return bytes.length;
            } catch (UnsupportedEncodingException e) {
                // 無視.
            }
        }
        return 0;
    }

    /**
     * Byte数を取得します.
     * @param str 対象文字列.
     * @return Byte数.
     */
    public static int byteLen(String str) {
        return byteLen(str, ReUtil.CHARSET);
    }



    //---------------------------------------------- static [public] 文字列型変換処理.
    /**
     * intを返却します.
     * @param str 対象文字列.
     * @return 文字数.
     */
    public static int toInt(String str) {
        return toInt(str, 0);
    }

    /**
     * intを返却します.
     * @param str 対象文字列.
     * @param defaultValue デフォルト値.
     * @return 文字数.
     */
    public static int toInt(String str, int defaultValue) {
        if (isEmpty(str)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Integerを返却します.
     * @param str 対象文字列.
     * @return 文字数.
     */
    public static Integer toInteger(String str) {
        return toInteger(str, null);
    }

    /**
     * Integerを返却します.
     * @param str 対象文字列.
     * @param defaultValue デフォルト値.
     * @return 文字数.
     */
    public static Integer toInteger(String str, Integer defaultValue) {
        if (isEmpty(str)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Longを返却します.
     * @param str 対象文字列.
     * @return 文字数.
     */
    public static Long toLong(String str) {
        return toLong(str, null);
    }

    /**
     * Longを返却します.
     * @param str 対象文字列.
     * @param defaultValue デフォルト値.
     * @return 文字数.
     */
    public static Long toLong(String str, Long defaultValue) {
        if (isEmpty(str)) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * BigDecimalを返却します.
     * @param str 対象文字列.
     * @return 文字数.
     */
    public static BigDecimal toBigDecimal(String str) {
        return toBigDecimal(str, null);
    }

    /**
     * BigDecimalを返却します.
     * @param str 対象文字列.
     * @param defaultValue デフォルト値.
     * @return 文字数.
     */
    public static BigDecimal toBigDecimal(String str, BigDecimal defaultValue) {
        if (isEmpty(str)) {
            return defaultValue;
        }
        try {
            return new BigDecimal(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }



    //---------------------------------------------- static [public] 文字列変換処理.
    /**
     * 全角ひらがなを全角カタカナに変換します.
     * @param str 対象文字列.
     * @return 変換後文字列.
     */
    public static String toHiraToKana(String str) {
        if (!isEmpty(str)) {
            StringBuffer sb = new StringBuffer(str);
            for (int i = 0; i < sb.length(); i++) {
                char c = sb.charAt(i);
                if (c >= 'ぁ' && c <= 'ん') {
                    sb.setCharAt(i, (char)(c - 'ぁ' + 'ァ'));
                }
            }
            return sb.toString();
        }
        return str;
    }

    /**
     * 全角カタカナを全角ひらがなに変換します.
     * @param str 対象文字列.
     * @return 変換後文字列.
     */
    public static String toKanaToHira(String str) {
        if (!isEmpty(str)) {
            StringBuffer sb = new StringBuffer(str);
            for (int i = 0; i < sb.length(); i++) {
                char c = sb.charAt(i);
                if (c >= 'ァ' && c <= 'ン') {
                    sb.setCharAt(i, (char)(c - 'ァ' + 'ぁ'));
                } else if (c == 'ヵ') {
                    sb.setCharAt(i, 'か');
                } else if (c == 'ヶ') {
                    sb.setCharAt(i, 'け');
                } else if (c == 'ヴ') {
                    sb.setCharAt(i, 'う');
                    sb.insert(i + 1, '゛');
                    i++;
                }
            }
            return sb.toString();
        }
        return str;
    }

    /**
     * 全角英文字を半角に変換します.
     * @param str 対象文字列.
     * @return 変換後文字列.
     */
    public static String toHanAlpha(String str) {
        if (!isEmpty(str)) {
            StringBuffer sb = new StringBuffer(str);
            for (int i = 0; i < sb.length(); i++) {
                char c = sb.charAt(i);
                if (c >= 'ａ' && c <= 'ｚ') {
                    sb.setCharAt(i, (char) (c - 'ａ' + 'a'));
                } else if (c >= 'Ａ' && c <= 'Ｚ') {
                    sb.setCharAt(i, (char) (c - 'Ａ' + 'A'));
                } else if (c == '－') {
                    sb.setCharAt(i, '-');
                } else if (c == '＿') {
                    sb.setCharAt(i, '_');
                }
            }
            return sb.toString();
        }
        return str;
    }

    /**
     * 半角英文字を全角に変換します.
     * @param str 対象文字列.
     * @return 変換後文字列.
     */
    public static String toZenAlpha(String str) {
        if (!isEmpty(str)) {
            StringBuffer sb = new StringBuffer(str);
            for (int i = 0; i < str.length(); i++) {
                char c = sb.charAt(i);
                if (c >= 'a' && c <= 'z') {
                    sb.setCharAt(i, (char)(c - 'a' + 'ａ'));
                } else if (c >= 'A' && c <= 'Z') {
                    sb.setCharAt(i, (char)(c - 'A' + 'Ａ'));
                } else if (c == '-') {
                    sb.setCharAt(i, '－');
                } else if (c == '_') {
                    sb.setCharAt(i, '＿');
                }
            }
            return sb.toString();
        }
        return str;
    }

    /**
     * 全角数字を半角に変換します.
     * @param str 対象文字列.
     * @return 変換後文字列.
     */
    public static String toHanNum(String str) {
        if (!isEmpty(str)) {
            StringBuffer sb = new StringBuffer(str);
            for (int i = 0; i < sb.length(); i++) {
              char c = sb.charAt(i);
              if (c >= '０' && c <= '９') {
                  sb.setCharAt(i, (char)(c - '０' + '0'));
              } else if (c == '＋') {
                  sb.setCharAt(i, '+');
              } else if (c == '－') {
                  sb.setCharAt(i, '-');
              } else if (c == '＊') {
                  sb.setCharAt(i, '*');
              } else if (c == '／') {
                  sb.setCharAt(i, '/');
              } else if (c == '％') {
                  sb.setCharAt(i, '%');
              } else if (c == '＾') {
                  sb.setCharAt(i, '^');
              } else if (c == '＝') {
                  sb.setCharAt(i, '=');
              } else if (c == '＜') {
                  sb.setCharAt(i, '<');
              } else if (c == '＞') {
                  sb.setCharAt(i, '>');
              } else if (c == '：') {
                  sb.setCharAt(i, ':');
              } else if (c == '．') {
                  sb.setCharAt(i, '.');
              } else if (c == '，') {
                  sb.setCharAt(i, ',');
              } else if (c == '（') {
                  sb.setCharAt(i, '(');
              } else if (c == '）') {
                  sb.setCharAt(i, ')');
              } else if (c == '　') {
                  sb.setCharAt(i, ' ');
              }
            }
            return sb.toString();
        }
        return str;
      }

    /**
     * 半角数字を全角に変換します.
     * @param str 対象文字列.
     * @return 変換後文字列.
     */
    public static String toZenNum(String str) {
        if (!isEmpty(str)) {
            StringBuffer sb = new StringBuffer(str);
            for (int i = 0; i < sb.length(); i++) {
              char c = sb.charAt(i);
              if (c >= '0' && c <= '9') {
                  sb.setCharAt(i, (char) (c - '0' + '０'));
              } else if (c == '+') {
                  sb.setCharAt(i, '＋');
              } else if (c == '-') {
                  sb.setCharAt(i, '－');
              } else if (c == '*') {
                  sb.setCharAt(i, '＊');
              } else if (c == '/') {
                  sb.setCharAt(i, '／');
              } else if (c == '%') {
                  sb.setCharAt(i, '％');
              } else if (c == '^') {
                  sb.setCharAt(i, '＾');
              } else if (c == '=') {
                  sb.setCharAt(i, '＝');
              } else if (c == '<') {
                  sb.setCharAt(i, '＜');
              } else if (c == '>') {
                  sb.setCharAt(i, '＞');
              } else if (c == ':') {
                  sb.setCharAt(i, '：');
              } else if (c == '.') {
                  sb.setCharAt(i, '．');
              } else if (c == ',') {
                  sb.setCharAt(i, '，');
              } else if (c == '(') {
                  sb.setCharAt(i, '（');
              } else if (c == ')') {
                  sb.setCharAt(i, '）');
              } else if (c == ' ') {
                  sb.setCharAt(i, '　');
              }
            }
            return sb.toString();
        }
        return str;
    }

    /**
     * 全角英数字を半角に変換します.
     * @param str 対象文字列.
     * @return 変換後文字列.
     */
    public static String toHanAlphaNum(String str) {
        return toHanNum(toHanAlpha(str));
    }

    /**
     * 半角英数字を全角に変換します.
     * @param str 対象文字列.
     * @return 変換後文字列.
     */
    public static String toZenAlphaNum(String str) {
        return toZenNum(toZenAlpha(str));
    }

}
