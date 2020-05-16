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
package com.res_system.commons.mvc.model.form;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MultivaluedMap;

import com.res_system.commons.mvc.MvcUtil;

/**
 * <pre>
 * フォームクラス用共通処理クラス.
 *
 * フォームクラス用の共通処理を行います.
 * </pre>
 * @author res.
 */
public final class FormUtil {

    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    private FormUtil() {}



    //---------------------------------------------- static [public] フォームクラス作成処理.
    /**
     * フォームクラスを作成します.
     * <pre>
     * ※
     * 「@Param」の項目を対象にparamsより取得データを設定します。
     * データクラスを対象とする場合は「@DataParam」を指定します。
     *  （データクラスで取得対象とする各項目に「@Param」が必要です）
     * リストを対象とする場合は「@ListParam」を指定します。
     *  （リストに設定されるクラスにも「@Param」が必要です）
     * </pre>
     * @param <T> 対象のフォームクラス.
     * @param clazz 対象フォームクラス型.
     * @return フォームクラス.
     * @throws Exception 例外.
     */
    public static <T> T make(Class<T> clazz) throws Exception {
        T form = null;
        try {
            form = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return form;
    }

    /**
     * フォームクラスを作成し、POSTデータを設定します.
     * <pre>
     * ※フォームクラスの階層は2~3層位にしてください。
     * ※
     * 「@Param」の項目を対象にparamsより取得データを設定します。
     * データクラスを対象とする場合は「@DataParam」を指定します。
     *  （データクラスで取得対象とする各項目に「@Param」が必要です）
     * リストを対象とする場合は「@ListParam」を指定します。
     *  （リストに設定されるクラスにも「@Param」が必要です）
     * </pre>
     * @param <T> 対象のフォームクラス.
     * @param clazz 対象フォームクラス型.
     * @param params POSTデータ.
     * @return フォームクラス.
     * @throws Exception 例外.
     */
    public static <T> T make(Class<T> clazz, MultivaluedMap<String, String> params) throws Exception {
        T form = null;
        try {
            form = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        getPostData(form, params);
        return form;
    }



    //---------------------------------------------- static [public] POSTデータ設定処理.
    /**
     * POSTデータを取得する.
     * <pre>
     * ※フォームクラスの階層は2~3層位にしてください。
     * ※
     * 「@Param」の項目を対象にparamsより取得データを設定します。
     * データクラスを対象とする場合は「@DataParam」を指定します。
     *  （データクラスで取得対象とする各項目に「@Param」が必要です）
     * リストを対象とする場合は「@ListParam」を指定します。
     *  （リストに設定されるクラスにも「@Param」が必要です）
     * </pre>
     * @param <T> 対象のフォームクラス.
     * @param form データを受け取るフォームクラス.
     * @param params POSTデータ.
     * @return フォームクラス.
     */
    public static <T> T getPostData(T form, MultivaluedMap<String, String> params) {
        setPostData(params, form, "");
        return form;
    }



    //---------------------------------------------- static [private] POSTデータ設定処理.
    /**
     * POSTデータを設定します(再帰).
     * @param <T> 設定対象のデータクラス.
     * @param params POSTデータ.
     * @param target 設定先のクラス.
     * @param prefix プレフィックス.
     */
    private static <T> void setPostData(MultivaluedMap<String, String> params, T target, String prefix) {
        if (params != null && target != null && prefix != null) {
            Map<String, Field> fieldMap = new LinkedHashMap<>();
            getAllFields(target.getClass(), fieldMap);
            for (Field field : fieldMap.values()) {
                try {
                    field.setAccessible(true);
                    String paramName = prefix + field.getName();
                    Param paramAnn = field.getAnnotation(Param.class);
                    DataParam dataParamAnn = field.getAnnotation(DataParam.class);
                    ListParam listParamAnn = field.getAnnotation(ListParam.class);
                    if (dataParamAnn != null) {
                        // オブジェクトの値設定.
                        if (field.get(target) != null) {
                            setPostData(params, field.get(target), paramName + ".");
                        }
                    } else if (listParamAnn != null) {
                        if (field.get(target) != null) {
                            if (params.get(paramName + "_size") != null) {
                                int listSize = MvcUtil.toInt(params.get(paramName + "_size").get(0), 0);
                                if (listSize > 0) {
                                    // リストの値設定.
                                    Class<?> dataType = listParamAnn.value();
                                    @SuppressWarnings("unchecked")
                                    List<Object> chiledDataList = (List<Object>) field.get(target);
                                    for (int i = 0; i < listSize; i++) {
                                        Object chiledData = dataType.newInstance();
                                        setPostData(params, chiledData, paramName + "[" + String.valueOf(i) + "].");
                                        chiledDataList.add(chiledData);
                                    }
                                }
                            }
                        }
                    } else if (paramAnn != null) {
                        // 値の設定.
                        if (field.getType().getSimpleName().equals("List")) {
                            if (params.containsKey(paramName)) {
                                field.set(target, params.get(paramName));
                            }
                        } else if (field.getType().getSimpleName().equals("String")) {
                            if (params.containsKey(paramName)) {
                                field.set(target, params.get(paramName).get(0));
                            }
                        }
                    }
                } catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
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
     * @param fields 取得したフィールドを格納するMap＜フィールド名,フィールド＞.
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



    //---------------------------------------------- static [public] セッションデータ処理.
    /**
     * 指定の名称のセッションデータを取得します.
     * @param <T> セッション保持する対象のデータクラス.
     * @param request HttpServletRequest.
     * @param name セッションデータ名.
     * @return セッションデータ.
     */
    @SuppressWarnings("unchecked")
    public static <T> T loadSessionData(HttpServletRequest request, String name) {
        if (request != null && name != null && name.length() > 0) {
            HttpSession session = request.getSession(true);
            return (T) session.getAttribute(name);
        }
        return null;
    }

    /**
     * 指定の名称でセッションデータを保存します.
     * @param <T> セッション保持する対象のデータクラス.
     * @param request HttpServletRequest.
     * @param name セッションデータ名.
     * @param target 保存するセッションデータ.
     * @return 処理結果.
     */
    public static <T> boolean saveSessionData(HttpServletRequest request, String name, T target) {
        if (request != null && name != null && name.length() > 0) {
            HttpSession session = request.getSession(true);
            session.setAttribute(name, target);
            return true;
        }
        return false;
    }

    /**
     * 指定の名称のセッションデータを削除します.
     * @param request HttpServletRequest.
     * @param name セッションデータ名.
     * @return 処理結果.
     */
    public static boolean removeSessionData(HttpServletRequest request, String name) {
        if (request != null && name != null && name.length() > 0) {
            HttpSession session = request.getSession(true);
            session.removeAttribute(name);
            return true;
        }
        return false;
    }



    //---------------------------------------------- static [public] Cookieデータ処理.
    /**
     * Cookieの値を取得します.
     * @param request HttpServletRequest.
     * @param name Cookieの名前.
     * @return Cookieの値.
     */
    public static String getCookie(HttpServletRequest request, String name) {
        String result = null;
        if (request != null && name != null && name.length() > 0) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (name.equals(cookie.getName())) {
                        result = cookie.getValue();
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Cookieの値を設定します.
     * @param request HttpServletRequest.
     * @param response HttpServletResponse.
     * @param path パス.
     * @param name Cookieの名前.
     * @param value Cookieの値.
     * @param maxAge 最長存続期間.
     * @return 処理結果.
     */
    public static boolean setCookie(
            HttpServletRequest request, HttpServletResponse response, String path, String name, String value, int maxAge) {
        if (request != null && response != null && name != null && name.length() > 0) {
            Cookie cookie = new Cookie(name, value);
            cookie.setMaxAge(maxAge);
            cookie.setPath(path);
            // httpsで稼働している環境であればCookieが暗号化されるようSecure属性をつける
            if ("https".equals(request.getScheme())) {
                cookie.setSecure(true);
            }
            response.addCookie(cookie);
            return true;
        }
        return false;
    }

    /**
     * Cookieの値を設定します.
     * <br>パスは"/"で設定します.
     * @param request HttpServletRequest.
     * @param response HttpServletResponse.
     * @param name Cookieの名前.
     * @param value Cookieの値.
     * @param maxAge 最長存続期間.
     * @return 処理結果.
     */
    public static boolean setCookie(
            HttpServletRequest request, HttpServletResponse response, String name, String value, int maxAge) {
        return setCookie(request, response, "/", name, value, maxAge);
    }

    /**
     * Cookieの値を設定します.
     * <br>最長存続期間は2週間.
     * @param request HttpServletRequest.
     * @param response HttpServletResponse.
     * @param path パス.
     * @param name Cookieの名前.
     * @param value Cookieの値.
     * @return 処理結果.
     */
    public static boolean setCookie(
            HttpServletRequest request, HttpServletResponse response, String path, String name, String value) {
        return setCookie(request, response, name, value, 60 * 60 * 24 * 14);
    }

    /**
     * Cookieの値を設定します.
     * <br>最長存続期間は2週間.パスは"/"で設定します.
     * @param request HttpServletRequest.
     * @param response HttpServletResponse.
     * @param name Cookieの名前.
     * @param value Cookieの値.
     * @return 処理結果.
     */
    public static boolean setCookie(
            HttpServletRequest request, HttpServletResponse response, String name, String value) {
        return setCookie(request, response, "/", name, value);
    }


    /**
     * Cookieの値を削除します.
     * @param request HttpServletRequest.
     * @param response HttpServletResponse.
     * @param name Cookieの名前.
     * @return 処理結果.
     */
    public static boolean removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        return setCookie(request, response, name, "", 0);
    }

    /**
     * Cookieの値を削除します.
     * @param request HttpServletRequest.
     * @param response HttpServletResponse.
     * @param path パス.
     * @param name Cookieの名前.
     * @param name Cookieの名前.
     * @return 処理結果.
     */
    public static boolean removeCookie(HttpServletRequest request, HttpServletResponse response, String path, String name) {
        return setCookie(request, response, path, name, "", 0);
    }

}
