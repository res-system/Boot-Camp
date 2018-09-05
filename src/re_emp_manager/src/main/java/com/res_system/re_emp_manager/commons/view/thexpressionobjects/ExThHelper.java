package com.res_system.re_emp_manager.commons.view.thexpressionobjects;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.res_system.commons.mvc.MvcUtil;
import com.res_system.commons.util.ReDateUtil;
import com.res_system.commons.util.ReUtil;
import com.res_system.re_emp_manager.commons.message.MessageModel;
import com.res_system.re_emp_manager.commons.model.auth.AuthRMenu;

/**
 * Thymeleaf 拡張ヘルパークラス.
 * @author res.
 */
public final class ExThHelper {

    //---------------------------------------------- [public] .
    /**
     * クライアント用共通メッセージを取得します.
     * <br />使用例）
     * <br />[ Commons.commonMessages = [[(${#exh.commonMessages()})]]; (メッセージ)]
     * @return 共通メッセージ.
     */
    public String commonMessages() {
        String[] codes = new String[]{
                  "W00001", "W00002", "W00003", "W00004", "W00013"
                , "I00001", "I00002"
                , "E00001", "E00020", "E00021", "E00003"};
        if (codes != null && codes.length > 0) {
            StringBuilder result = new StringBuilder();
            for (String code : codes) {
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

    /**
     * サイドメニューを表示します.
     * <br />使用例）
     * <br />[ th:text="${#exh.sideMenu(~)}" (メニューデータリスト)]
     * @param list メニューデータリスト.
     * @return HTML文.
     */
    public String sideMenu(List<AuthRMenu> list) {
        if (list != null && list.size() > 0) {
            StringBuilder htmlText = new StringBuilder();
            String keyId = "";
            for (int i = 0, imax = list.size(); i < imax; i++) {
                AuthRMenu data = list.get(i);

                if ("1".equals(data.getLevel())) {
                    if (!ReUtil.isEmpty(data.getUrl())) {
                        // 1階層.
                        htmlText.append("<li class=\"nav-item\"")
                                .append(" data-toggle=\"tooltip\" data-placement=\"right\"")
                                .append(" title=\"").append(data.getTitle()).append("\">").append("\n");
                        htmlText.append("<a class=\"nav-link link\"")
                                .append(" href=\"").append(data.getUrl()).append("\">");
                        htmlText.append("<i class=\"fa fa-fw ").append(data.getIcon()).append("\"></i> ");
                        htmlText.append("<span class=\"nav-link-text\">")
                                .append(data.getTitle()).append("</span>");
                        htmlText.append("</a>").append("\n");
                        htmlText.append("</li>").append("\n");

                    } else {
                        // 2階層.
                        // 子階層をチェック
                        int count = 0;
                        for (int j = i + 1, jmax = list.size(); j < jmax; j++) {
                            if ("2".equals(list.get(j).getLevel())) {
                                count++;
                            } else {
                                break;
                            }
                        }
                        if (count > 0) {
                            keyId = data.getNo() + "_" + data.getSeq() +"_sub";
                            htmlText.append("<li class=\"nav-item\"")
                                    .append(" data-toggle=\"tooltip\" data-placement=\"right\"")
                                    .append(" title=\"").append(data.getTitle()).append("\">").append("\n");
                            htmlText.append("<a class=\"nav-link nav-link-collapse collapsed\"")
                                    .append(" data-toggle=\"collapse\"")
                                    .append(" href=\"#").append(keyId).append("\"")
                                    .append(" data-parent=\"#side_menu\">");
                            htmlText.append("<i class=\"fa fa-fw ").append(data.getIcon()).append("\"></i> ");
                            htmlText.append("<span class=\"nav-link-text\">").append(data.getTitle()).append("</span>");
                            htmlText.append("</a>").append("\n");

                            htmlText.append("<ul class=\"sidenav-second-level collapse\"")
                                    .append(" id=\"").append(keyId).append("\">").append("\n");
                            for (int j = i + 1, jmax = list.size(); j < jmax; j++) {
                                AuthRMenu subData = list.get(j);
                                if (!"2".equals(subData.getLevel())) {
                                    i = j - 1;
                                    break;
                                } else {
                                    htmlText.append("<li>").append("\n");
                                    htmlText.append("<a class=\"nav-link link\"")
                                            .append(" href=\"").append(subData.getUrl()).append("\">");
                                    htmlText.append("<i class=\"fa fa-fw ")
                                            .append(subData.getIcon()).append("\"></i> ");
                                    htmlText.append("<span class=\"nav-link-text\">")
                                            .append(subData.getTitle()).append("</span>");
                                    htmlText.append("</a>").append("\n");
                                    htmlText.append("</li>").append("\n");
                                }
                            }
                            htmlText.append("</ul>").append("\n");
                            htmlText.append("</li>").append("\n");
                        }
                    }
                }

            } //for.
            return htmlText.toString();
        }
        return "";
    }


    /**
     * JSON文字列に変換します.
     * <br />使用例）
     * <br />[ th:text="${#exh.json(~)}" (対象オブジェクト)]
     *
     * @param target 対象オブジェクト.
     * @return JSON文字列.
     */
    public String jsonMsg(Object target) {
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

    /**
     * 年齢を算出します.
     * <br />使用例）
     * <br />[ th:text="${#exh.toAge(~)}" (生年月日)]
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
     * 郵便番号を加工して表示します.
     * <br />使用例）
     * <br />[ th:text="${#exh.showPostalCode(~)}" (郵便番号)]
     * @param postalCode 郵便番号.
     * @return 郵便番号.
     */
    public String showPostalCode(String postalCode) {
        StringBuilder result = new StringBuilder();
        if (!ReUtil.isEmpty(postalCode) && postalCode.length() == 7) {
            result.append("〒");
            result.append(postalCode.substring(0, 3));
            result.append("-");
            result.append(postalCode.substring(3));
        }
        return result.toString();
    }

    /**
     * 住所を加工して表示します.
     * <br />使用例）
     * <br />[ th:text="${#exh.showAddr(~,~)}" (住所1,住所2)]
     * @param addr1 住所1.
     * @param addr2 住所2.
     * @return 住所.
     */
    public String showAddr(String addr1, String addr2) {
        StringBuilder result = new StringBuilder();
        if (!ReUtil.isEmpty(addr1) && !ReUtil.isEmpty(addr2)) {
            result.append(addr1);
            result.append(addr2);
        } else if (!ReUtil.isEmpty(addr1) && ReUtil.isEmpty(addr2)) {
            result.append(addr1);
        } else if (ReUtil.isEmpty(addr1) && !ReUtil.isEmpty(addr2)) {
            result.append(addr2);
        }
        return result.toString();
    }

    /**
     * 電話番号を加工して表示します.
     * <br />使用例）
     * <br />[ th:text="${#exh.showTelNo(~)}" (電話番号)]
     * @param tel_no 電話番号.
     * @return 電話番号.
     */
    public String showTelNo(String tel_no) {
        StringBuilder result = new StringBuilder();
        if (!ReUtil.isEmpty(tel_no)) {
            result.append("Tel: ");
            result.append(tel_no);
        }
        return result.toString();
    }

    /**
     * メールアドレスを加工して表示します.
     * <br />使用例）
     * <br />[ th:text="${#exh.showEmail(~)}" (メールアドレス)]
     * @param email メールアドレス.
     * @return メールアドレス.
     */
    public String showEmail(String email) {
        StringBuilder result = new StringBuilder();
        if (!ReUtil.isEmpty(email)) {
            result.append("E-Mail: ");
            result.append(email);
        }
        return result.toString();
    }

}