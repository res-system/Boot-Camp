package com.res_system.mvc_skeleton.commons.view.thexpressionobjects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.res_system.commons.mvc.MvcUtil;
import com.res_system.commons.util.ReUtil;
import com.res_system.mvc_skeleton.commons.model.message.MessageModel;

/**
 * Thymeleaf 拡張ヘルパークラス.
 * @author res.
 */
public final class ExThHelper {

    //---------------------------------------------- [public] メッセージ.
    /**
     * クライアント用共通メッセージを取得します.
     * <br />使用例）
     * <br />[ Commons.commonMessages = [[(${#exh.commonMessages('...')})]]; (メッセージ)]
     * @return 共通メッセージ.
     */
    public String commonMessages(final String codeString) {
        if (!ReUtil.isEmpty(codeString)) {
            String[] codes = codeString.split(",");
            return commonMessages(codes);
        }
        return "";
    }

    /**
     * クライアント用共通メッセージを取得します.
     * <br />使用例）
     * <br />[ Commons.commonMessages = [[(${#exh.commonMessages('...')})]]; (メッセージ)]
     * @return 共通メッセージ.
     */
    public String commonMessages(final String... codes) {
        if (!ReUtil.isEmpty(codes)) {
            StringBuilder result = new StringBuilder();
            for (String code : codes) {
                code = code.trim();
                String text = ReUtil.getPropertyValue(MessageModel.PROPERTY_FILE_NAME, code);
                text = text.replaceAll("\\n", "\\\\n");
                if (result.length() > 0) {
                    result.append(",'").append(code).append("':'").append(text).append("'");
                } else {
                    result.append(" '").append(code).append("':'").append(text).append("'");
                }
            }
            return result.toString();
        }
        return "";
    }



    //---------------------------------------------- [public] メニュー.



    //---------------------------------------------- [public] 文字列加工.
   /**
     * JSON文字列に変換します.
     * <br />使用例）
     * <br />[ th:text="${#exh.json(~)}" (対象オブジェクト)]
     *
     * @param target 対象オブジェクト.
     * @return JSON文字列.
     */
    public String jsonMsg(final Object target) {
        try {
            String json = MvcUtil.toJson(target);
            if (!ReUtil.isEmpty(json)) {
                json = json.replaceAll("\\n", "\\\\n");
            }
            return json;
        } catch (JsonProcessingException e) {
            // 無視.
        }
        return "";
    }



}