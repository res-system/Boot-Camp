package com.res_system.re_employee_manager.commons.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.res_system.commons.mvc.model.kind.IKind;
import com.res_system.commons.mvc.model.kind.KindUtil;
import com.res_system.commons.util.ReChecker;
import com.res_system.commons.util.ReDateUtil;
import com.res_system.re_employee_manager.commons.model.data.Message;

/**
 * <pre>
 * 入力チェック モデルクラス.
 * </pre>
 * @author res.
 */
@RequestScoped
public class CheckerModel {

    //---------------------------------------------- [private] モデルクラス.
    /** メッセージ モデルクラス. */
    @Inject
    private MessageModel msgModel;



    //---------------------------------------------- properies [private].
    /** メッセージリスト. */
    private List<Message> messageList;

    //-- setter / getter. --//
    /** メッセージリスト を取得します. */
    public List<Message> getMessageList() { return messageList; }
    /** メッセージリスト を設定します. */
    public void setMessageList(List<Message> messageList) { this.messageList = messageList; }
    /** メッセージリスト を追加します. */
    public CheckerModel addMessage(Message message) { this.messageList.add(message); return this; }
    /** セレクター を追加します. */
    public CheckerModel addSelector(String selector) {
        if (this.messageList != null && this.messageList.size() > 0) {
            return addSelector(this.messageList.size() - 1, selector);
        }
        return this;
    }
    /** セレクター を追加します. */
    public CheckerModel addSelector(Integer index, String selector) {
        if (this.messageList != null && this.messageList.size() > index) {
            this.messageList.get(index).addSelector(selector);
        }
        return this;
    }



    //---------------------------------------------- [public] インジェクション開始/終了.
    /** インジェクション完了後、コールバック. */
    @PostConstruct
    public void setup() {
        messageList = null;
    }

    /** インスタンスの破棄時、コールバック. */
    @PreDestroy
    public void destroy() {
    }



    //---------------------------------------------- [public] チェック処理.
    /**
     * 必須チェックを行います.
     * @param value 確認対象の値.
     * @param selector セレクタ.
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkRequired(String value, String name) {
        if (!ReChecker.checkNotEmpty(value)) {
            addMessage(msgModel.getMessage("E00011", name));
            return false;
        }
        return true;
    }

    /**
     * 正義表現のパターンチェックを行います.
     * @param value 確認対象の値.
     * @param regex パターン.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkMatch(String value, String regex, String name) {
        if (!ReChecker.checkMatch(value, regex)) {
            addMessage(msgModel.getMessage("E00018", name, regex));
            return false;
        }
        return true;
    }



    //---------------------------------------------- [public] サイズチェック処理.
    /**
     * 文字列サイズチェックを行います.
     * @param value 確認対象の値.
     * @param max 最大値.
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkLength(String value, int max, String name) {
        if (!ReChecker.checkLength(value, max)) {
            addMessage(msgModel.getMessage("E00015", name, String.valueOf(max)));
            return false;
        }
        return true;
    }

    /**
     * 文字列サイズチェックを行います.
     * @param value 確認対象の値.
     * @param min 最小値.
     * @param max 最大値.
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkLength(String value, int min, int max, String name) {
        if (!ReChecker.checkLength(value, min, max)) {
            if (min == max) {
                addMessage(msgModel.getMessage("E00017", name, String.valueOf(min), String.valueOf(max)));
            } else {
                addMessage(msgModel.getMessage("E00016", name, String.valueOf(min), String.valueOf(max)));
            }
            return false;
        }
        return true;
    }



    //---------------------------------------------- [public] 文字種チェック処理.
    /**
     * 半角チェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkAscii(String value, String name) {
        if (!ReChecker.checkAscii(value)) {
            addMessage(msgModel.getMessage("E00013", name, "半角"));
            return false;
        }
        return true;
    }

    /**
     * 数値チェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkNumeric(String value, String name) {
        if (!ReChecker.checkNumeric(value)) {
            addMessage(msgModel.getMessage("E00013", name, "数値"));
            return false;
        }
        return true;
    }

    /**
     * 数字チェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkNumber(String value, String name) {
        if (!ReChecker.checkNumber(value)) {
            addMessage(msgModel.getMessage("E00013", name, "数字"));
            return false;
        }
        return true;
    }

    /**
     * 数字チェックを行います.
     * @param value 確認対象の値.
     * @param exclusion 除外文字列（正規表現）.
     * @param name 対象の項目名.
     * @param description 説明.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkNumber(String value, String exclusion, String name, String description) {
        if (!ReChecker.checkNumber(value, exclusion)) {
            addMessage(msgModel.getMessage("E00019", name, "数字", description));
            return false;
        }
        return true;
    }

    /**
     * アルファベットチェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkAlpha(String value, String name) {
        if (!ReChecker.checkAlpha(value)) {
            addMessage(msgModel.getMessage("E00013", name, "アルファベット"));
            return false;
        }
        return true;
    }

    /**
     * アルファベットチェックを行います.
     * @param value 確認対象の値.
     * @param exclusion 除外文字列（正規表現）.
     * @param name 対象の項目名.
     * @param description 説明.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkAlpha(String value, String exclusion, String name, String description) {
        if (!ReChecker.checkAlpha(value, exclusion)) {
            addMessage(msgModel.getMessage("E00019", name, "アルファベット", description));
            return false;
        }
        return true;
    }

    /**
     * アルファベット又は数字チェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkAlphaNumber(String value, String name) {
        if (!ReChecker.checkAlphaNumber(value)) {
            addMessage(msgModel.getMessage("E00013", name, "アルファベット又は数字"));
            return false;
        }
        return true;
    }

    /**
     * アルファベット又は数字チェックを行います.
     * @param value 確認対象の値.
     * @param exclusion 除外文字列（正規表現）.
     * @param name 対象の項目名.
     * @param description 説明.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkAlphaNumber(String value, String exclusion, String name, String description) {
        if (!ReChecker.checkAlphaNumber(value, exclusion)) {
            addMessage(msgModel.getMessage("E00019", name, "アルファベット又は数字", description));
            return false;
        }
        return true;
    }

    /**
     * 全角カナ文字チェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkKana(String value, String name) {
        if (!ReChecker.checkKana(value)) {
            addMessage(msgModel.getMessage("E00013", name, "全角カナ文字"));
            return false;
        }
        return true;
    }

    /**
     * 全角カナ文字チェックを行います.
     * @param value 確認対象の値.
     * @param exclusion 除外文字列（正規表現）.
     * @param name 対象の項目名.
     * @param description 説明.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkKana(String value, String exclusion, String name, String description) {
        if (!ReChecker.checkKana(value, exclusion)) {
            addMessage(msgModel.getMessage("E00019", name, "全角カナ文字", description));
            return false;
        }
        return true;
    }



    //---------------------------------------------- [public] 数値チェック処理.
    /**
     * 数値のサイズチェックを行います.
     * @param value 確認対象の値.
     * @param max 最大値(BigDecimalで変換可能な数値の範囲で設定する).
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkNumericSize(String value, String max, String name) {
        if (!ReChecker.checkNumericSize(value, max)) {
            addMessage(msgModel.getMessage("E00030", name, max));
            return false;
        }
        return true;
    }

    /**
     * 数値のサイズチェックを行います.
     * @param value 確認対象の値.
     * @param min 最小値(BigDecimalで変換可能な数値の範囲で設定する).
     * @param max 最大値(BigDecimalで変換可能な数値の範囲で設定する).
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkNumericSize(String value, String min, String max, String name) {
        if (!ReChecker.checkNumericSize(value, min, max)) {
            addMessage(msgModel.getMessage("E00031", name, min, max));
            return false;
        }
        return true;
    }

    /**
     * 値がNULL又は空又は「0」で無い事チェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkNotZero(String value, String name) {
        if (!ReChecker.checkNotZero(value)) {
            addMessage(msgModel.getMessage("E00032", name));
            return false;
        }
        return true;
    }

    /**
     * マイナスチェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkNotMinus(String value, String name) {
        if (!ReChecker.checkNotMinus(value)) {
            addMessage(msgModel.getMessage("E00033", name));
            return false;
        }
        return true;
    }

    /**
     * 小数点ありの桁数チェックを行います.
     * @param value 確認対象の値（数値）.
     * @param integerPart 整数部の桁数.
     * @param decimalPart 小数部の桁数.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkDecimalSize(String value, int integerPart, int decimalPart, String name) {
        if (!ReChecker.checkDecimalSize(value, integerPart, decimalPart)) {
            addMessage(msgModel.getMessage("E00034", name, String.valueOf(integerPart), String.valueOf(decimalPart)));
            return false;
        }
        return true;
    }

    /**
     * 数値の大小チェックを行います.（小さい数値）
     * @param value1 確認対象1の値(小).
     * @param value2 確認対象2の値(大).
     * @param name1 対象の項目名1.
     * @param name2 対象の項目名2.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkSmallerNum(String value1, String value2, String name1, String name2) {
        if (ReChecker.checkNotEmpty(value1) && ReChecker.checkNotEmpty(value2)
                && ReChecker.checkNumeric(value1) && ReChecker.checkNumeric(value2)) {
            BigDecimal num1 = new BigDecimal(value1);
            BigDecimal num2 = new BigDecimal(value2);
            if (num1.compareTo(num2) >= 0) {
                addMessage(msgModel.getMessage("E00035", name1, name2));
                return false;
            }
        }
        return true;
    }

    /**
     * 数値の大小チェックを行います.（大きい数値）
     * @param value1 確認対象1の値(小).
     * @param value2 確認対象2の値(大).
     * @param name1 対象の項目名1.
     * @param name2 対象の項目名2.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkGreaterNum(String value1, String value2, String name1, String name2) {
        if (ReChecker.checkNotEmpty(value1) && ReChecker.checkNotEmpty(value2)
                && ReChecker.checkNumeric(value1) && ReChecker.checkNumeric(value2)) {
            BigDecimal num1 = new BigDecimal(value1);
            BigDecimal num2 = new BigDecimal(value2);
            if (num1.compareTo(num2) <= 0) {
                addMessage(msgModel.getMessage("E00036", name1, name2));
                return false;
            }
        }
        return true;
    }



    //---------------------------------------------- [public] 日時チェック処理.
    /**
     * 日付チェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkDateFormat(String value, String name) {
        if (!ReChecker.checkDateFormat(value)) {
            addMessage(msgModel.getMessage("E00013", name, "日付"));
            return false;
        }
        return true;
    }

    /**
     * 日付チェックを行います.
     * @param value 確認対象の値.
     * @param format フォーマット.
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkDateFormat(String value, String format, String name) {
        if (!ReChecker.checkDateFormat(value, format)) {
            addMessage(msgModel.getMessage("E00013", name, "日付"));
            return false;
        }
        return true;
    }

    /**
     * 日時チェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkDateTimeFormat(String value, String name) {
        if (!ReChecker.checkDateTimeFormat(value)) {
            addMessage(msgModel.getMessage("E00013", name, "日時"));
            return false;
        }
        return true;
    }

    /**
     * 時刻チェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkTimeFormat(String value, String name) {
        if (!ReChecker.checkTimeFormat(value)) {
            addMessage(msgModel.getMessage("E00013", name, "時刻"));
            return false;
        }
        return true;
    }

    /**
     * 過去日チェックを行います.
     * @param value1 確認対象1の値(小).
     * @param value2 確認対象2の値(大).
     * @param name1 対象の項目名1.
     * @param name2 対象の項目名2.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkPastDate(String value1, String value2, String name1, String name2) {
        if (ReChecker.checkNotEmpty(value1) && ReChecker.checkNotEmpty(value2)
                && ReChecker.checkDateFormat(value1) && ReChecker.checkDateFormat(value2)) {
            LocalDate data1 = ReDateUtil.toLocalDate(value1);
            LocalDate data2 = ReDateUtil.toLocalDate(value2);
            if (!data2.isAfter(data1)) {
                addMessage(msgModel.getMessage("E00051", name1, name2));
                return false;
            }
        }
        return true;
    }

    /**
     * 未来日チェックを行います.
     * @param value1 確認対象1の値(小).
     * @param value2 確認対象2の値(大).
     * @param name1 対象の項目名1.
     * @param name2 対象の項目名2.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkFutureDate(String value1, String value2, String name1, String name2) {
        if (ReChecker.checkNotEmpty(value1) && ReChecker.checkNotEmpty(value2)
                && ReChecker.checkDateFormat(value1) && ReChecker.checkDateFormat(value2)) {
            LocalDate data1 = ReDateUtil.toLocalDate(value1);
            LocalDate data2 = ReDateUtil.toLocalDate(value2);
            if (!data1.isAfter(data2)) {
                addMessage(msgModel.getMessage("E00052", name1, name2));
                return false;
            }
        }
        return true;
    }



    //---------------------------------------------- [public] 種別チェック処理.
    /**
     * 種別チェックを行います.
     * @param value 確認対象の値.
     * @param values 種別リスト.
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkItemList(String value, IKind[] values, String name) {
        if (!KindUtil.isExist(values, value)) {
            addMessage(msgModel.getMessage("E00020", name));
            return false;
        }
        return true;
    }



    //---------------------------------------------- [public] 複合チェック処理.
    /**
     * 半角項目チェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param length 最大文字数.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkHalfText(String value, Boolean isRequired, int length, String name, String selector) {
        if (isRequired) {
            if (!checkRequired(value, name)) {
                addSelector(selector);
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (!checkAscii(value, name)) {
            addSelector(selector);
            return false;
        } else if (!checkLength(value, length, name)) {
            addSelector(selector);
            return false;
        }
        return true;
    }

    /**
     * 全角項目チェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param length 最大文字数.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkFullText(String value, Boolean isRequired, int length, String name, String selector) {
        if (isRequired) {
            if (!checkRequired(value, name)) {
                addSelector(selector);
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (!checkLength(value, length, name)) {
            addSelector(selector);
            return false;
        }
        return true;
    }

    /**
     * 全角項目カナチェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param length 最大文字数.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkFullKanaText(String value, Boolean isRequired, int length, String name, String selector) {
        if (isRequired) {
            if (!checkRequired(value, name)) {
                addSelector(selector);
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (!checkKana(value, name)) {
            addSelector(selector);
            return false;
        } else if (!checkLength(value, 40, name)) {
            addSelector(selector);
            return false;
        }
        return true;
    }

    /**
     * 種別項目チェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param values 種別リスト.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkKind(String value, Boolean isRequired, IKind[] values, String name, String selector) {
        if (isRequired) {
            if (!checkRequired(value, name)) {
                addSelector(selector);
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (!checkItemList(value, values, name)) {
            addSelector(selector);
            return false;
        }
        return true;
    }

    /**
     * 日付項目チェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkDate(String value, Boolean isRequired, String name, String selector) {
        if (isRequired) {
            if (!checkRequired(value, name)) {
                addSelector(selector);
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (!checkDateFormat(value, name)) {
            addSelector(selector);
            return false;
        }
        return true;
    }

    /**
     * 半角項目数字チェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param length 最大文字数.
     * @param exclusion 除外文字列（正規表現）.
     * @param name 対象の項目名.
     * @param description 説明.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkHalfNum(String value, Boolean isRequired, int length, String name, String selector) {
        if (isRequired) {
            if (!checkRequired(value, name)) {
                addSelector(selector);
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (!checkLength(value, length, name)) {
            addSelector(selector);
            return false;
        }
        if (!checkNumber(value, name)) {
            addSelector(selector);
            return false;
        }
        return true;
    }

    /**
     * 半角項目数字チェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param length 最大文字数.
     * @param exclusion 除外文字列（正規表現）.
     * @param name 対象の項目名.
     * @param description 説明.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkHalfNum(String value, Boolean isRequired,
            int length, String exclusion, String name, String description, String selector) {
        if (isRequired) {
            if (!checkRequired(value, name)) {
                addSelector(selector);
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (!checkLength(value, length, name)) {
            addSelector(selector);
            return false;
        }
        if (!checkNumber(value, exclusion, name, description)) {
            addSelector(selector);
            return false;
        }
        return true;
    }

    /**
     * 郵便番号項目チェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkPostalCode(String value, Boolean isRequired, String name, String selector) {
        if (isRequired) {
            if (!checkRequired(value, name)) {
                addSelector(selector);
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (!checkLength(value, 7, 7, name)) {
            addSelector(selector);
            return false;
        }
        if (!checkNumber(value, name)) {
            addSelector(selector);
            return false;
        }
        return true;
    }

    /**
     * 電話番号項目チェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkTelNo(String value, Boolean isRequired, String name, String selector) {
        if (isRequired) {
            if (!checkRequired(value, name)) {
                addSelector(selector);
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (!checkLength(value, 20, name)) {
            addSelector(selector);
            return false;
        }
        if (!checkNumber(value, "-", name, "(「-」の入力も可です)")) {
            addSelector(selector);
            return false;
        }
        return true;
    }

    /**
     * E-mail項目チェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkEmail(String value, Boolean isRequired, String name, String selector) {
        if (isRequired) {
            if (!checkRequired(value, name)) {
                addSelector(selector);
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (!checkAscii(value, name)) {
            addSelector(selector);
            return false;
        } else if (!checkLength(value, 256, name)) {
            addSelector(selector);
            return false;
        }
        return true;
    }

}
