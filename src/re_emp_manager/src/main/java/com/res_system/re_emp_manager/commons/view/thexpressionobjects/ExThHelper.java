package com.res_system.re_emp_manager.commons.view.thexpressionobjects;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ibm.icu.text.NumberFormat;
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
                , "I00001", "I00002", "I00003"
                , "E00001", "E00003", "E00014", "E00020", "E00021"};
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
     * <br />[ th:text="${#exh.sideMenu(~)}" (メニューデータリスト,ルートURL)]
     * @param list メニューデータリスト.
     * @param root_url ルートURL.
     * @return HTML文.
     */
    public String sideMenu(final List<AuthRMenu> list, final String root_url) {
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
                                .append(" href=\"").append(url(data.getUrl(), root_url)).append("\">").append("\n");
                        htmlText.append("<i class=\"fa fa-fw ").append(data.getIcon()).append("\"></i> ").append("\n");
                        htmlText.append("<span class=\"nav-link-text\">")
                                .append(data.getTitle()).append("</span>").append("\n");
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
                                    .append(" data-parent=\"#side_menu\">").append("\n");
                            htmlText.append("<i class=\"fa fa-fw ").append(data.getIcon()).append("\"></i> ").append("\n");
                            htmlText.append("<span class=\"nav-link-text\">").append(data.getTitle()).append("</span>").append("\n");
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
                                            .append(" href=\"").append(url(subData.getUrl(), root_url)).append("\">");
                                    htmlText.append("<i class=\"fa fa-fw ").append(subData.getIcon()).append("\"></i> ");
                                    htmlText.append("<span>").append(subData.getTitle()).append("</span>");
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

    /**
     * 年齢を算出します.
     * <br />使用例）
     * <br />[ th:text="${#exh.toAge(~)}" (生年月日)]
     * @param birthday 生年月日.
     * @return 年齢.
     */
    public String toAge(final String birthday) {
        if (!ReUtil.isEmpty(birthday)) {
            return "(" + String.valueOf(ReDateUtil.calcAge(birthday)) + "歳)";
        } else {
            return "";
        }
    }

    /**
     * 勤続年数を算出します.
     * <br />使用例）
     * <br />[ th:text="${#exh.toLengthOfService(~,~)}" (入社日,退職日)]
     * @param hire_date 入社日.
     * @param retirement_date 退職日.
     * @return 勤続年数.
     * @throws ParseException 
     */
    public String toLengthOfService(final String hire_date, final String retirement_date) throws ParseException {
        if (!ReUtil.isEmpty(hire_date)) {
            // 退職日(ない場合は現在日付).
            Date date1;
            Date today = ReDateUtil.nowDt();
            if (!ReUtil.isEmpty(retirement_date)) {
                Date retirementDate = DateFormat.getDateInstance().parse(retirement_date);
                if (retirementDate.compareTo(today) < 0) {
                    date1 = retirementDate;
                } else {
                    date1 = today; 
                }
            } else {
                date1 = today; 
            }
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);
            cal1.set(Calendar.DATE, 1);

            // 入社日.
            Date date2 = DateFormat.getDateInstance().parse(hire_date);
            Calendar cal2 = Calendar.getInstance(); 
            cal2.setTime(date2);
            cal2.set(Calendar.DATE, 1);
            // 差分計算.
            int count = 0;
            if (cal1.before(cal2)) {
                while (cal1.before(cal2)) {
                    cal1.add(Calendar.MONTH, 1);
                    count--;
                }
            } else {
                count--;
                while (!cal1.before(cal2)) {
                    cal1.add(Calendar.MONTH, -1);
                    count++;
                }
            }
            int year = count / 12;
            int month = count % 12;
            if (month != 0) {
                return "(" + year + "年" + month + "ヶ月)";
            } else {
                return "(" + year + "年)";
            }
        } else {
            return "";
        }
    }

    /**
     * 郵便番号を加工して表示します.
     * <br />使用例）
     * <br />[ th:text="${#exh.showPostalCode(~)}" (郵便番号)]
     * @param postalCode 郵便番号.
     * @return 郵便番号.
     */
    public String showPostalCode(final String postalCode) {
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
    public String showAddr(final String addr1, final String addr2) {
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
    public String showTelNo(final String tel_no) {
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
    public String showEmail(final String email) {
        StringBuilder result = new StringBuilder();
        if (!ReUtil.isEmpty(email)) {
            result.append("E-Mail: ");
            result.append(email);
        }
        return result.toString();
    }

    /**
     * URLを加工して表示します.
     * <br />使用例）
     * <br />[ th:text="${#exh.url(~,~)}" (対象のURL,ルートURL)]
     * @param url 対象のURL.
     * @param root_url ルートURL.
     * @return URL.
     */
    public String url(final String url, final String root_url) {
        String retUrl = url;
        if (!ReUtil.isEmpty(url)) {
            if (url.startsWith("/")) {
            // urlの先頭が'/'の場合.
                if (!ReUtil.isEmpty(root_url)
                        && (url.indexOf(root_url) == -1 ||
                            url.indexOf(root_url) > 0)) {
                // root_urlがあり、対象のURLの先頭がroot_urlではない場合.
                    if (root_url.endsWith("/")) {
                    // root_urlの終わりが'/'終わりの場合は、'/'を一つ削除する.
                        retUrl = root_url + url.substring(1);
                    } else {
                        retUrl = root_url + url;
                    }
                }
            }
        }
        return retUrl;
    };

    /**
     * 数値を加工して表示します.
     * <br />使用例）
     * <br />[ th:text="${#exh.showNum(~)}" (数値)]
     * @param num 数値.
     * @return 数値.
     */
    public String showNum(final String num) {
        StringBuilder result = new StringBuilder();
        if (!ReUtil.isEmpty(num)) {
            try {
                Number number = NumberFormat.getInstance().parse(num);
                result.append(String.format("%,d", number.intValue()));
            } catch (Exception e) {}
        } else {
            result.append("0");
        }
        return result.toString();
    }
}
