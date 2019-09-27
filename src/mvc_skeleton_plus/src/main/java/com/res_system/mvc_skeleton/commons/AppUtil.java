package com.res_system.mvc_skeleton.commons;

import com.res_system.commons.util.ReUtil;

/**
 * アプリケーション 共通処理クラス.
 * @author res.
 */
public class AppUtil {

    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    private AppUtil() {}



    //---------------------------------------------- static [public].
    /**
     * 先頭「/」のURLをフルのURLに変換します.
     * @param url 対象のURL.
     * @return 編集後のURL.
     */
    public static String url(String url) {
        if (!ReUtil.isEmpty(url) && url.startsWith("/")) {
            String result = ReUtil.getPropertyValue(ReUtil.PROPERTY_FILE_NAME, "com.res_system.ez_kinmu.url", "");
            if (!ReUtil.isEmpty(result)) {
                return result + url;
            }
        }
        return "";
    }



}
