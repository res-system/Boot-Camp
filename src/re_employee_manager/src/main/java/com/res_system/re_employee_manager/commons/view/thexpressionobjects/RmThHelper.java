package com.res_system.re_employee_manager.commons.view.thexpressionobjects;

import com.res_system.commons.util.ReDateUtil;
import com.res_system.commons.util.ReUtil;

/**
 * <pre>
 * 社員管理システム用Thymeleaf ヘルパークラス.
 * </pre>
 * @author res.
 */
public final class RmThHelper {

    //---------------------------------------------- [public].
    /**
     * 年齢を算出します.
     * <br />使用例）
     * <br />[ th:text="${#rmh.toAge(~)}" (生年月日)]
     * @param birthday 生年月日.
     * @return 年齢.
     */
    public String toAge(String birthday) {
        if (!ReUtil.isEmpty(birthday)) {
            return String.valueOf(ReDateUtil.calcAge(birthday));
        } else {
            return "?";
        }
    }

    /**
     * 郵便番号を表示します.
     * <br />使用例）
     * <br />[ th:text="${#rmh.showPostalCode(~)}" (郵便番号)]
     * @param postalCode 郵便番号.
     * @return 郵便番号.
     */
    public String showPostalCode(String postalCode) {
        if (!ReUtil.isEmpty(postalCode) && postalCode.length() > 3) {
            StringBuilder result = new StringBuilder();// 〒
            result.append("〒");
            result.append(postalCode.substring(0, 3));
            result.append("-");
            result.append(postalCode.substring(3));
            return result.toString();
        } else {
            return "";
        }
    }

}
