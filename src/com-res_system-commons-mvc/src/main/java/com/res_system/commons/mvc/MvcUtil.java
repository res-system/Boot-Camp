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
package com.res_system.commons.mvc;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.IterationStatusVar;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <pre>
 * MVC用共通処理クラス.
 *
 * MVC用の共通処理を行います.
 * </pre>
 * @author res.
 */
public final class MvcUtil {

    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    private MvcUtil() {}



    //---------------------------------------------- static [public] Thymeleaf処理.
    /**
     * Thymeleaf上の属性値を取得します.
     * @param context ITemplateContext.
     * @param attrValues 設定値.
     * @return Thymeleaf上の属性値.
     */
    public static Object getValue(final ITemplateContext context, final String attrValues) {
        if (context != null && !isEmpty(attrValues)) {
            final IEngineConfiguration configuration = context.getConfiguration();
            // Thymeleaf標準式パーサーを入手する。
            final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);
            // 属性値をThymeleaf標準式として解析します。
            final IStandardExpression expression = parser.parseExpression(context, attrValues.trim());
            // 構文解析された式を実行します。
            return expression.execute(context);
        } else {
            return attrValues;
        }
    }

    /**
     * Thymeleaf上の属性値を取得します（文字列）.
     * @param context ITemplateContext.
     * @param attrValues 設定値.
     * @return Thymeleaf上の属性値.
     */
    public static String getValueString(final ITemplateContext context, final String attrValues) {
        Object value;
        try {
            value = getValue(context, attrValues);
        } catch (Exception e) {
            // 無視．
            value = null;
        }
        if (value != null) {
            if (value instanceof IterationStatusVar) {
                return String.valueOf(((IterationStatusVar)value).getIndex());
            } else {
                return String.valueOf(value);
            }
        } else {
            return "";
        }
    }

    /**
     * Thymeleaf上の設定値を編集します.
     * @param attrValues 属性値.
     * @return 編集後の属性値.
     */
    public static String editAttrValues(final String attrValues) {
      if (!isEmpty(attrValues)) {
          String editValue = attrValues.trim();
          // 変数式.
          if (editValue.startsWith("${") && editValue.endsWith("}")) {
              editValue = editValue.substring(2, editValue.length() - 1);
          }
          // 選択変数式.
          else if (editValue.startsWith("*{") && editValue.endsWith("}")) {
              editValue = editValue.substring(2, editValue.length() - 1);
          }
          // メッセージ式.
          else if (editValue.startsWith("#{") && editValue.endsWith("}")) {
              editValue = editValue.substring(2, editValue.length() - 1);
          }
          // リンクURL式.
          else if (editValue.startsWith("@{") && editValue.endsWith("}")) {
              editValue = editValue.substring(2, editValue.length() - 1);
          }
          // リテラル.
          else if (editValue.startsWith("'") && editValue.endsWith("'")) {
              editValue = editValue.substring(1, editValue.length() - 1);
          }
          return editValue;
      } else {
          return "";
      }
    }

    /**
     * AttributeValueを分割します(「,」区切り).
     * @param attrValues AttributeValue.
     * @return 分割後のAttributeValue.
     */
    public static String[] splitAttrValues(final String attrValues) {
        if (!isEmpty(attrValues) && attrValues.indexOf(",") > 0) {
            return attrValues.split(",");
        } else {
            if (attrValues == null) {
                return new String[]{""};
            } else {
                return new String[]{attrValues};
            }
        }
    }



    //---------------------------------------------- static [public] ファイル処理.
    /**
     * 指定されたファイルの更新日をバージョンとして返却します.
     * @param servletContext ServletContext.
     * @param fileUrl ファイルURL.
     * @return バージョン.
     */
    public static String getFileVer(final ServletContext servletContext, final String fileUrl) {
        String versionText = "0";
        if (servletContext != null && !isEmpty(fileUrl)) {
            try {
                Path path = Paths.get(servletContext.getRealPath(fileUrl));
                if (Files.exists(path)) {
                    FileTime fTime = Files.getLastModifiedTime(path);
                    DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                    versionText = df.format(fTime.toMillis());
               }
            } catch (Exception e) {
                // 無視.
            }
        }
        return versionText;
    }



    //---------------------------------------------- static [public] 項目名・ID編集処理.
    /**
     * 項目名を取得します.
     * @param fieldName フィールド名.
     * @return 項目名.
     */
    public static String getName(String fieldName) {
        return Trim(fieldName);
    }

    /**
     * 項目名を取得します.
     * @param dataName データ名.
     * @param fieldName フィールド名.
     * @return 項目名.
     */
    public static String getName(String dataName, String fieldName) {
        return String.format("%s.%s", Trim(dataName), Trim(fieldName));
    }

    /**
     * 項目名を取得します.
     * @param listName リスト名.
     * @param listIndex リストIndex.
     * @param fieldName フィールド名.
     * @return 項目名.
     */
    public static String getName(String listName, String listIndex, String fieldName) {
        return String.format("%s[%s].%s", Trim(listName), Trim(listIndex), Trim(fieldName));
    }


    /**
     * 項目IDを取得します.
     * @param fieldName フィールド名.
     * @return 項目ID.
     */
    public static String getId(String fieldName) {
        String id = Trim(fieldName);
        return id.replaceAll("\\.", "_");
    }

    /**
     * 項目IDを取得します.
     * @param dataName データ名.
     * @param fieldName フィールド名.
     * @return 項目ID.
     */
    public static String getId(String dataName, String fieldName) {
        String id = String.format("%s_%s", Trim(dataName), Trim(fieldName));
        return id.replaceAll("\\.", "_");
    }

    /**
     * 項目IDを取得します.
     * @param listName リスト名.
     * @param listIndex リストIndex.
     * @param fieldName フィールド名.
     * @return 項目ID.
     */
    public static String getId(String listName, String listIndex, String fieldName) {
        String id = String.format("%s_%s_%s", Trim(listName), Trim(listIndex), Trim(fieldName));
        return id.replaceAll("\\.", "_");
    }


    /**
     * 項目IDを取得します(値付き).
     * @param fieldName フィールド名.
     * @param value 値.
     * @return 項目ID.
     */
    public static String getIdWithValue(String fieldName, String value) {
        String id = String.format("%s_%s", Trim(fieldName), Trim(value));
        return id.replaceAll("\\.", "_");
    }

    /**
     * 項目IDを取得します(値付き).
     * @param dataName データ名.
     * @param fieldName フィールド名.
     * @param value 値.
     * @return 項目ID.
     */
    public static String getIdWithValue(String dataName, String fieldName, String value) {
        String id = String.format("%s_%s_%s", Trim(dataName), Trim(fieldName), Trim(value));
        return id.replaceAll("\\.", "_");
    }

    /**
     * 項目IDを取得します(値付き).
     * @param listName リスト名.
     * @param listIndex リストIndex.
     * @param fieldName フィールド名.
     * @param value 値.
     * @return 項目ID.
     */
    public static String getIdWithValue(String listName, String listIndex, String fieldName, String value) {
        String id = String.format("%s_%s_%s_%s", Trim(listName), Trim(listIndex), Trim(fieldName), Trim(value));
        return id.replaceAll("\\.", "_");
    }



    //---------------------------------------------- static [public] 文字列編集処理.
    /**
     * HTML文字列のエスケープ処理を行います.
     * @param htmlText HTML文字列.
     * @return 編集後のHTML文字列.
     */
    public static String escapeHtml(String htmlText) {
        if (htmlText == null) {
            return htmlText;
        }
        String madeHtml = htmlText;
        madeHtml = madeHtml.replaceAll("&" , "&amp;" );
        madeHtml = madeHtml.replaceAll("<" , "&lt;"  );
        madeHtml = madeHtml.replaceAll(">" , "&gt;"  );
        madeHtml = madeHtml.replaceAll("\"", "&quot;");
        madeHtml = madeHtml.replaceAll("'" , "&#39;" );
        return madeHtml;
    }

    /**
     * エスケープしたHTML文字を元に戻します.
     * @param htmlText エスケープ後のHTML文字列.
     * @return 編集後のHTML文字列.
     */
    public static String unsanitize(String htmlText) {
        if (htmlText == null) {
            return htmlText;
        }
        String madeHtml = htmlText;
        madeHtml = madeHtml.replaceAll("&#39;" , "'" );
        madeHtml = madeHtml.replaceAll("&quot;", "\"");
        madeHtml = madeHtml.replaceAll("&gt;"  , ">" );
        madeHtml = madeHtml.replaceAll("&lt;"  , "<" );
        madeHtml = madeHtml.replaceAll("&amp;" , "&" );
        return madeHtml;
     }

    /**
     * 改行を<br />に置換します.
     * @param htmlText HTML文字列.
     * @return 編集後のHTML文字列.
     */
    public static String replaceCLTag(String htmlText) {
        if (htmlText == null) {
            return htmlText;
        }
        String madeHtml = htmlText.replaceAll("\\r\\n|\\r|\\n|&lt;br /&gt;", "<br />");
        return madeHtml;
    }

    /**
     * JSON文字列に変換します.
     * @param target 対象オブジェクト.
     * @return JSON文字列.
     * @throws JsonProcessingException
     */
    public static String toJson(Object target) throws JsonProcessingException {
        if (target != null) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(target);
        }
        return "";
    }



    //---------------------------------------------- static [public] プロパティファイル処理.
    /** プロパティファイル名. */
    public static final String PROPERTY_FILE_NAME = "com-res_system-commons";

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



    //---------------------------------------------- [public] チェック共通処理.
    /**
     * 値がNULL又は空である事を確認します.
     * @param value 確認対象の値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean isEmpty(Integer value) {
        if (value == null || value == 0) {
            return true;
        }
        return false;
    }

    /**
     * 値がNULL又は空である事を確認します.
     * @param value 確認対象の値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean isEmpty(Long value) {
        if (value == null || value == 0L) {
            return true;
        }
        return false;
    }

    /**
     * 値がNULL又は空である事を確認します.
     * @param value 確認対象の値.
     * @return 確認結果(true:OK, false:NG).
     */
    public static boolean isEmpty(BigDecimal value) {
        if (value == null || value.compareTo(new BigDecimal(0)) == 0) {
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



    //---------------------------------------------- [public] 文字列編集共通処理.
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
     * 文字列が空の場合に空文字を返却します.
     * @param str 対象文字列.
     * @return 編集後の値.
     */
    public static String toEmpty(String str) {
        return toDefault(str, "");
    }

    /**
     * 文字列が空の場合にNullを返却します.
     * @param str 対象文字列.
     * @return 編集後の値.
     */
    public static String toNull(String str) {
        return toDefault(str, null);
    }

    /**
     * 
     * 文字列が空の場合にデフォルトの値を返却します.
     * @param str 対象文字列.
     * @param defaultValue デフォルトの値.
     * @return 編集後の値.
     */
    public static String toDefault(String str, String defaultValue) {
        if (isEmpty(str)) {
            return defaultValue;
        }
        return str;
    }



    //---------------------------------------------- static [public] 文字列型変換処理.
    /**
     * intを返却します.
     * @param str 対象文字列.
     * @return 変換後の値.
     */
    public static int toInt(String str) {
        return toInt(str, 0);
    }

    /**
     * intを返却します.
     * @param str 対象文字列.
     * @param defaultValue デフォルト値.
     * @return 変換後の値.
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
     * longを返却します.
     * @param str 対象文字列.
     * @return 変換後の値.
     */
    public static long toLong(String str) {
        return toLong(str, 0L);
    }

    /**
     * longを返却します.
     * @param str 対象文字列.
     * @param defaultValue デフォルト値.
     * @return 変換後の値.
     */
    public static long toLong(String str, long defaultValue) {
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
     * @return 変換後の値.
     */
    public static BigDecimal toBigDecimal(String str) {
        BigDecimal defaultValue = null;
        return toBigDecimal(str, defaultValue);
    }

    /**
     * BigDecimalを返却します.
     * @param str 対象文字列.
     * @param defaultValue デフォルト値.
     * @return 変換後の値.
     */
    public static BigDecimal toBigDecimal(String str, String defaultValue) {
        return toBigDecimal(str, new BigDecimal(defaultValue));
    }

    /**
     * BigDecimalを返却します.
     * @param str 対象文字列.
     * @param defaultValue デフォルト値.
     * @return 変換後の値.
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


    /**
     * booleanを返却します.
     * @param str 対象文字列.
     * @return 変換後の値.
     */
    public static boolean toBoolean(String str) {
        return toBoolean(str, false);
    }

    /**
     * booleanを返却します.
     * @param str 対象文字列.
     * @param defaultValue デフォルト値.
     * @return 変換後の値.
     */
    public static boolean toBoolean(String str, boolean defaultValue) {
        if (!isEmpty(str)) {
            String value = str.toLowerCase();
            if ("true".equals(value)) {
                return new Boolean(value);
            } else if ("false".equals(value)) {
                return new Boolean(value);
            }
        }
        return defaultValue;
    }



}
