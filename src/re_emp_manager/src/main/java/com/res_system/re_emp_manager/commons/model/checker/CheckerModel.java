package com.res_system.re_emp_manager.commons.model.checker;

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
import com.res_system.commons.util.ReUtil;
import com.res_system.re_emp_manager.commons.message.IMessage;
import com.res_system.re_emp_manager.commons.message.Message;
import com.res_system.re_emp_manager.commons.message.MessageModel;

/**
 * <pre>
 * 入力チェック モデルクラス.
 * </pre>
 * @author res.
 */
@RequestScoped
public class CheckerModel implements IMessage {

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
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkRequired(final String value, final String name) {
        return checkRequired(value, name, null);
    }

    /**
     * 必須チェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkRequired(final String value, final String name, final String selector) {
        if (!ReChecker.checkNotEmpty(value)) {
            addMessage(msgModel.getMessage("E00020", name).setSelector(selector));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
            return false;
        }
        return true;
    }

    /**
     * 選択チェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkSelected(final String value, final String name) {
        return checkSelected(value, name, null);
    }

    /**
     * 選択チェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkSelected(final String value, final String name, final String selector) {
        if (!ReChecker.checkNotEmpty(value)) {
            addMessage(msgModel.getMessage("E00021", name));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
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
    public boolean checkMatch(final String value, final String regex, final String name) {
        return checkMatch(value, regex, name, null);
    }

    /**
     * 正義表現のパターンチェックを行います.
     * @param value 確認対象の値.
     * @param regex パターン.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkMatch(final String value, final String regex, final String name, final String selector) {
        if (!ReChecker.checkMatch(value, regex)) {
            addMessage(msgModel.getMessage("E00031", name, regex));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
            return false;
        }
        return true;
    }



    //---------------------------------------------- [public] サイズチェック処理.
    /**
     * 文字列サイズチェックを行います.
     * @param value 確認対象の値.
     * @param max 最大文字数.
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkLength(final String value, final int max, final String name) {
        return checkLength(value, max, name, null);
    }

    /**
     * 文字列サイズチェックを行います.
     * @param value 確認対象の値.
     * @param max 最大文字数.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkLength(final String value, final int max, final String name, final String selector) {
        if (!ReChecker.checkLength(value, max)) {
            addMessage(msgModel.getMessage("E00035", name, String.valueOf(max)));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
            return false;
        }
        return true;
    }

    /**
     * 文字列サイズチェックを行います.
     * @param value 確認対象の値.
     * @param min 最小文字数.
     * @param max 最大文字数.
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkLength(final String value, final int min, final int max, final String name) {
        return checkLength(value, min, max, name, null);
    }

    /**
     * 文字列サイズチェックを行います.
     * @param value 確認対象の値.
     * @param min 最小文字数.
     * @param max 最大文字数.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkLength(String value, int min, int max, String name, final String selector) {
        if (!ReChecker.checkLength(value, min, max)) {
            if (min == max) {
                addMessage(msgModel.getMessage("E00036", name, String.valueOf(min), String.valueOf(max)));
            } else {
                addMessage(msgModel.getMessage("E00037", name, String.valueOf(min), String.valueOf(max)));
            }
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
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
    public boolean checkAscii(final String value, final String name) {
        return checkAscii(value, name, null);
    }

    /**
     * 半角チェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkAscii(final String value, final String name, final String selector) {
        if (!ReChecker.checkAscii(value)) {
            addMessage(msgModel.getMessage("E00032", name, "半角"));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
            return false;
        }
        return true;
    }

    /**
     * 半角数値チェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkNumeric(final String value, final String name) {
        return checkNumeric(value, name, null);
    }

    /**
     * 半角数値チェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkNumeric(final String value, final String name, final String selector) {
        if (!ReChecker.checkNumeric(value)) {
            addMessage(msgModel.getMessage("E00032", name, "数値"));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
            return false;
        }
        return true;
    }

    /**
     * 半角数字チェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkNumber(final String value, final String name) {
        return checkNumber(value, name, null);
    }

    /**
     * 半角数字チェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkNumber(final String value, final String name, final String selector) {
        if (!ReChecker.checkNumber(value)) {
            addMessage(msgModel.getMessage("E00032", name, "半角数字"));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
            return false;
        }
        return true;
    }

    /**
     * 半角数字チェックを行います.
     * @param value 確認対象の値.
     * @param exclusion 除外文字列（正規表現）.
     * @param name 対象の項目名.
     * @param description 説明.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkNumber(final String value, final String exclusion, final String name, final String description) {
        return checkNumber(value, exclusion, name, description, null);
    }

    /**
     * 半角数字チェックを行います.
     * @param value 確認対象の値.
     * @param exclusion 除外文字列（正規表現）.
     * @param name 対象の項目名.
     * @param description 説明.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkNumber(
            final String value, final String exclusion, final String name, final String description, final String selector) {
        if (!ReChecker.checkNumber(value, exclusion)) {
            addMessage(msgModel.getMessage("E00033", name, "半角数字", description));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
            return false;
        }
        return true;
    }

    /**
     * 半角アルファベットチェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkAlpha(final String value, final String name) {
        return checkAlpha(value, name, null);
    }

    /**
     * 半角アルファベットチェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkAlpha(final String value, final String name, final String selector) {
        if (!ReChecker.checkAlpha(value)) {
            addMessage(msgModel.getMessage("E00032", name, "半角アルファベット"));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
            return false;
        }
        return true;
    }

    /**
     * 半角アルファベットチェックを行います.
     * @param value 確認対象の値.
     * @param exclusion 除外文字列（正規表現）.
     * @param name 対象の項目名.
     * @param description 説明.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkAlpha(final String value, final String exclusion, final String name, final String description) {
        return checkAlpha(value, exclusion, name, description, null);
    }

    /**
     * 半角アルファベットチェックを行います.
     * @param value 確認対象の値.
     * @param exclusion 除外文字列（正規表現）.
     * @param name 対象の項目名.
     * @param description 説明.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkAlpha(
            final String value, final String exclusion, final String name, final String description, final String selector) {
        if (!ReChecker.checkAlpha(value, exclusion)) {
            addMessage(msgModel.getMessage("E00033", name, "半角アルファベット", description));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
            return false;
        }
        return true;
    }

    /**
     * 半角アルファベット又は数字チェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkAlphaNumber(final String value, final String name) {
        return checkAlphaNumber(value, name, null);
    }

    /**
     * 半角アルファベット又は数字チェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkAlphaNumber(final String value, final String name, final String selector) {
        if (!ReChecker.checkAlphaNumber(value)) {
            addMessage(msgModel.getMessage("E00032", name, "半角アルファベット又は数字"));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
            return false;
        }
        return true;
    }

    /**
     * 半角アルファベット又は数字チェックを行います.
     * @param value 確認対象の値.
     * @param exclusion 除外文字列（正規表現）.
     * @param name 対象の項目名.
     * @param description 説明.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkAlphaNumber(final String value, final String exclusion, final String name, final String description) {
        return checkAlphaNumber(value, exclusion, name, description, null);
    }

    /**
     * 半角アルファベット又は数字チェックを行います.
     * @param value 確認対象の値.
     * @param exclusion 除外文字列（正規表現）.
     * @param name 対象の項目名.
     * @param description 説明.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkAlphaNumber(
            final String value, final String exclusion, final String name, final String description, final String selector) {
        if (!ReChecker.checkAlphaNumber(value, exclusion)) {
            addMessage(msgModel.getMessage("E00033", name, "半角アルファベット又は数字", description));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
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
    public boolean checkKana(final String value, final String name) {
        return checkKana(value, name, null);
    }

    /**
     * 全角カナ文字チェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkKana(final String value, final String name, final String selector) {
        if (!ReChecker.checkKana(value)) {
            addMessage(msgModel.getMessage("E00032", name, "全角カナ文字"));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
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
    public boolean checkKana(final String value, final String exclusion, final String name, final String description) {
        return checkKana(value, exclusion, name, description, null);
    }

    /**
     * 全角カナ文字チェックを行います.
     * @param value 確認対象の値.
     * @param exclusion 除外文字列（正規表現）.
     * @param name 対象の項目名.
     * @param description 説明.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkKana(
            final String value, final String exclusion, final String name, final String description, final String selector) {
        if (!ReChecker.checkKana(value, exclusion)) {
            addMessage(msgModel.getMessage("E00033", name, "全角カナ文字", description));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
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
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkNumericSize(final String value, final String max, final String name, final String selector) {
        if (!ReChecker.checkNumericSize(value, max)) {
            addMessage(msgModel.getMessage("E00050", name, max));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
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
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkNumericSize(
            final String value, final String min, final String max, final String name, final String selector) {
        if (!ReChecker.checkNumericSize(value, min, max)) {
            addMessage(msgModel.getMessage("E00051", name, min, max));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
            return false;
        }
        return true;
    }

    /**
     * 値がNULL又は空又は「0」で無い事チェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkNotZero(final String value, final String name, final String selector) {
        if (!ReChecker.checkNotZero(value)) {
            addMessage(msgModel.getMessage("E00052", name));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
            return false;
        }
        return true;
    }

    /**
     * マイナスチェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkNotMinus(final String value, final String name, final String selector) {
        if (!ReChecker.checkNotMinus(value)) {
            addMessage(msgModel.getMessage("E00053", name));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
            return false;
        }
        return true;
    }

    /**
     * 小数点ありの桁数チェックを行います.
     * @param value 確認対象の値（数値）.
     * @param integerPart 整数部の桁数.
     * @param decimalPart 小数部の桁数.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkDecimalSize(
            final String value, final int integerPart, final int decimalPart, final String name, final String selector) {
        if (!ReChecker.checkDecimalSize(value, integerPart, decimalPart)) {
            addMessage(msgModel.getMessage("E00054", name, String.valueOf(integerPart), String.valueOf(decimalPart)));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
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
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkSmallerNum(
            final String value1, final String value2, final String name1, final String name2, final String selector) {
        if (ReChecker.checkNotEmpty(value1) && ReChecker.checkNotEmpty(value2)
                && ReChecker.checkNumeric(value1) && ReChecker.checkNumeric(value2)) {
            BigDecimal num1 = new BigDecimal(value1);
            BigDecimal num2 = new BigDecimal(value2);
            if (num1.compareTo(num2) >= 0) {
                addMessage(msgModel.getMessage("E00055", name1, name2));
                if (ReChecker.checkNotEmpty(selector)) {
                    addSelector(selector);
                }
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
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkGreaterNum(
            final String value1, final String value2, final String name1, final String name2, final String selector) {
        if (ReChecker.checkNotEmpty(value1) && ReChecker.checkNotEmpty(value2)
                && ReChecker.checkNumeric(value1) && ReChecker.checkNumeric(value2)) {
            BigDecimal num1 = new BigDecimal(value1);
            BigDecimal num2 = new BigDecimal(value2);
            if (num1.compareTo(num2) >= 0) {
                addMessage(msgModel.getMessage("E00056", name1, name2));
                if (ReChecker.checkNotEmpty(selector)) {
                    addSelector(selector);
                }
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
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkDateFormat(final String value, final String name, final String selector) {
        if (ReChecker.checkEmpty(value)) {
            return true;
        }
        if (value.length() != 10 || !ReChecker.checkDateFormat(value)) {
            addMessage(msgModel.getMessage("E00032", name, "日付"));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
            return false;
        }
        return true;
    }

    /**
     * 日付チェックを行います.
     * @param value 確認対象の値.
     * @param format フォーマット.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkDateFormat(final String value, final String format, final String name, final String selector) {
        if (!ReChecker.checkDateFormat(value, format)) {
            addMessage(msgModel.getMessage("E00032", name, "日付"));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
            return false;
        }
        return true;
    }

    /**
     * 日時チェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkDateTimeFormat(final String value, final String name, final String selector) {
        if (!ReChecker.checkDateTimeFormat(value)) {
            addMessage(msgModel.getMessage("E00032", name, "日時"));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
            return false;
        }
        return true;
    }

    /**
     * 時刻チェックを行います.
     * @param value 確認対象の値.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkTimeFormat(final String value, final String name, final String selector) {
        if (ReChecker.checkEmpty(value)) {
            return true;
        }
        if (value.length() != 8 || !ReChecker.checkTimeFormat(value)) {
            addMessage(msgModel.getMessage("E00032", name, "時刻"));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
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
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkPastDate(
            final String value1, final String value2, final String name1, final String name2, final String selector) {
        if (ReChecker.checkNotEmpty(value1) && ReChecker.checkNotEmpty(value2)
                && ReChecker.checkDateFormat(value1) && ReChecker.checkDateFormat(value2)) {
            LocalDate data1 = ReDateUtil.toLocalDate(value1);
            LocalDate data2 = ReDateUtil.toLocalDate(value2);
            if (!data2.isAfter(data1)) {
                addMessage(msgModel.getMessage("E00061", name1, name2));
                if (ReChecker.checkNotEmpty(selector)) {
                    addSelector(selector);
                }
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
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkFutureDate(
            final String value1, final String value2, final String name1, final String name2, final String selector) {
        if (ReChecker.checkNotEmpty(value1) && ReChecker.checkNotEmpty(value2)
                && ReChecker.checkDateFormat(value1) && ReChecker.checkDateFormat(value2)) {
            LocalDate data1 = ReDateUtil.toLocalDate(value1);
            LocalDate data2 = ReDateUtil.toLocalDate(value2);
            if (!data1.isAfter(data2)) {
                addMessage(msgModel.getMessage("E00062", name1, name2));
                if (ReChecker.checkNotEmpty(selector)) {
                    addSelector(selector);
                }
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
    public boolean checkItemList(final String value, final IKind[] values, final String name) {
        return checkItemList(value, values, name, null);
    }

    /**
     * 種別チェックを行います.
     * @param value 確認対象の値.
     * @param values 種別リスト.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkItemList(final String value, final IKind[] values, final String name, final String selector) {
        if (ReChecker.checkNotEmpty(value)) {
            if (!KindUtil.isExist(values, value)) {
                addMessage(msgModel.getMessage("E00040", name));
                if (ReChecker.checkNotEmpty(selector)) {
                    addSelector(selector);
                }
                return false;
            }
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
    public boolean checkHalfText(
            final String value, final Boolean isRequired, final int length, final String name, final String selector) {
        if (isRequired) {
            if (!checkRequired(value, name, selector)) {
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (!checkAscii(value, name, selector)) {
            return false;
        } else if (!checkLength(value, length, name, selector)) {
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
    public boolean checkFullText(
            final String value, final Boolean isRequired, final int length, final String name, final String selector) {
        if (isRequired) {
            if (!checkRequired(value, name, selector)) {
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (!ReChecker.checkMachineDepChar(value)) {
            addMessage(msgModel.getMessage("E00042", name).setSelector(selector));
            return false;
        }
        if (!checkLength(value, length, name, selector)) {
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
    public boolean checkFullKanaText(
            final String value, final Boolean isRequired, final int length, final String name, final String selector) {
        if (isRequired) {
            if (!checkRequired(value, name, selector)) {
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (!checkKana(value, "　（） ()", name, "(「(」「)」とスペースを含む)", selector)) {
            return false;
        } else if (!checkLength(value, length, name, selector)) {
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
    public boolean checkKind(
            final String value, final Boolean isRequired, final IKind[] values, final String name, final String selector) {
        if (isRequired) {
            if (!checkSelected(value, name, selector)) {
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (!checkItemList(value, values, name, selector)) {
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
    public boolean checkDate(final String value, final Boolean isRequired, final String name, final String selector) {
        if (isRequired) {
            if (!checkRequired(value, name, selector)) {
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (!checkDateFormat(value, name, selector)) {
            return false;
        }
        return true;
    }

    /**
     * 時刻項目チェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkTime(final String value, final Boolean isRequired, final String name, final String selector) {
        if (isRequired) {
            if (!checkRequired(value, name, selector)) {
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (value.length() == 4) {
            String timeVvalue = value.substring(0, 2) + ":" + value.substring(2) + ":00";
            if (!checkTimeFormat(timeVvalue, name, selector)) {
                return false;
            }
        } else if (value.length() == 5) {
            String timeVvalue = value + ":00";
            if (!checkTimeFormat(timeVvalue, name, selector)) {
                return false;
            }
        } else {
            addMessage(msgModel.getMessage("E00032", name, "時刻"));
            if (ReChecker.checkNotEmpty(selector)) {
                addSelector(selector);
            }
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
    public boolean checkHalfNum(
            final String value, final Boolean isRequired, final int length, final String name, final String selector) {
        return checkHalfNum(value, isRequired, length, null, name, null, selector);
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
    public boolean checkHalfNum(final String value, final Boolean isRequired, final int length, 
            final String exclusion, final String name, final String description, final String selector) {
        return checkHalfNum(value, isRequired, 0, length, exclusion, name, description, selector);
    }

    /**
     * 半角項目数字チェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param min 最小文字数.
     * @param max 最大文字数.
     * @param exclusion 除外文字列（正規表現）.
     * @param name 対象の項目名.
     * @param description 説明.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkHalfNum(final String value, final Boolean isRequired, final int min, final int max, 
            final String exclusion, final String name, final String description, final String selector) {
        if (isRequired) {
            if (!checkRequired(value, name, selector)) {
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (min > 0) {
            if (!checkLength(value, min, max, name, selector)) {
                return false;
            }
        } else {
            if (!checkLength(value, max, name, selector)) {
                return false;
            }
        }
        if (!ReUtil.isEmpty(exclusion)) {
            if (!checkNumber(value, exclusion, name, description, selector)) {
                return false;
            }
        } else {
            if (!checkNumber(value, name, selector)) {
                return false;
            }
        }
        return true;
    }


    /**
     * 半角項目英数字チェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param length 最大文字数.
     * @param exclusion 除外文字列（正規表現）.
     * @param name 対象の項目名.
     * @param description 説明.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkHalfAlpNum(
            final String value, final Boolean isRequired, final int length, final String name, final String selector) {
        return checkHalfAlpNum(value, isRequired, length, null, name, null, selector);
    }

    /**
     * 半角項目英数字チェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param length 最大文字数.
     * @param exclusion 除外文字列（正規表現）.
     * @param name 対象の項目名.
     * @param description 説明.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkHalfAlpNum(final String value, final Boolean isRequired, final int length, 
            final String exclusion, final String name, final String description, final String selector) {
        return checkHalfAlpNum(value, isRequired, 0, length, exclusion, name, description, selector);
    }

    /**
     * 半角項目英数字チェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param min 最小文字数.
     * @param max 最大文字数.
     * @param exclusion 除外文字列（正規表現）.
     * @param name 対象の項目名.
     * @param description 説明.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkHalfAlpNum(final String value, final Boolean isRequired, final int min, final int max, 
            final String exclusion, final String name, final String description, final String selector) {
        if (isRequired) {
            if (!checkRequired(value, name, selector)) {
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (min > 0) {
            if (!checkLength(value, min, max, name, selector)) {
                return false;
            }
        } else {
            if (!checkLength(value, max, name, selector)) {
                return false;
            }
        }
        if (!ReUtil.isEmpty(exclusion)) {
            if (!checkAlphaNumber(value, exclusion, name, description, selector)) {
                return false;
            }
        } else {
            if (!checkAlphaNumber(value, name, selector)) {
                return false;
            }
        }
        return true;
    }


    /**
     * 半角項目数値チェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param min 最小文字数.
     * @param max 最大文字数.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkHalfNumeric(final String value, final Boolean isRequired, 
            final int min, final int max, final String name, final String selector) {
        if (isRequired) {
            if (!checkRequired(value, name, selector)) {
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (!checkNumeric(value, name, selector)) {
            return false;
        }
        if (value.indexOf(".") >= 0) {
            addMessage(msgModel.getMessage("E00016", name + "に小数点").addSelector(selector));
            return false;
        }
        String minStr = String.valueOf(min);
        String maxStr = String.valueOf(max);
        if (!checkNumericSize(value, minStr, maxStr, name, selector)) {
            return false;
        }
        return true;
    }

    /**
     * 小数点ありの半角項目数値チェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param integerPart 整数部の桁数.
     * @param decimalPart 小数部の桁数.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkHalfDecimal(
            final String value, final Boolean isRequired, 
            final int integerPart, final int decimalPart, 
            final String name, final String selector) {
        if (isRequired) {
            if (!checkRequired(value, name, selector)) {
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (!checkNumeric(value, name, selector)) {
            return false;
        }
        if (!checkDecimalSize(value, integerPart, decimalPart, name, selector)) {
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
    public boolean checkPostalCode(
            final String value, final Boolean isRequired, final String name, final String selector) {
        if (isRequired) {
            if (!checkRequired(value, name, selector)) {
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (!checkLength(value, 7, 7, name, selector)) {
            return false;
        }
        if (!checkNumber(value, name, selector)) {
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
    public boolean checkTelNo(
            final String value, final Boolean isRequired, final String name, final String selector) {
        if (isRequired) {
            if (!checkRequired(value, name, selector)) {
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (!checkLength(value.replaceAll("-", ""), 11, name + "(「-」を含む)", selector)) {
            return false;
        }
        if (!checkNumber(value, "\\-", name, "(「-」を含む)", selector)) {
            return false;
        }
        if (value.indexOf("-") >= 0 && !ReChecker.checkMatch(value, "^0\\d+-\\d+-\\d{4}$")) {
            addMessage(msgModel.getMessage("E00003", name, selector));
            return false;
        }
        return true;
    }

    /**
     * メールアドレス項目チェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkEmail(final String value, final Boolean isRequired, final String name, final String selector) {
        if (isRequired) {
            if (!checkRequired(value, name, selector)) {
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (!checkAscii(value, name, selector)) {
            return false;
        } else if (!checkLength(value, 254, name, selector)) {
            return false;
        } else if (value.indexOf("@") < 0) {
            addMessage(msgModel.getMessage("E00003", name).addSelector(selector));
            return false;
        } else {
            String[] work = value.split("@");
            if (work.length != 2) {
                addMessage(msgModel.getMessage("E00003", name).addSelector(selector));
                return false;
            }
            if (work[0].length() <= 0 || work[0].length() > 64) {
                addMessage(msgModel.getMessage("E00003", name).addSelector(selector));
                return false;
            }
            if (!ReChecker.checkMatch(value, 
                      "^(([0-9a-zA-Z!#\\$%&'\\*\\+\\-/=\\?\\^_`\\{\\}\\|~]+"
                    + "(\\.[0-9a-zA-Z!#\\$%&'\\*\\+\\-/=\\?\\^_`\\{\\}\\|~]+)*)|(\"[^\"]*\"))"
                    + "@[0-9a-zA-Z!#\\$%&'\\*\\+\\-/=\\?\\^_`\\{\\}\\|~]+"
                    + "(\\.[0-9a-zA-Z!#\\$%&'\\*\\+\\-/=\\?\\^_`\\{\\}\\|~]+)*$")) {
                addMessage(msgModel.getMessage("E00003", name).addSelector(selector));
                return false;
            }
        }
        return true;
    }

    /**
     * 識別コード項目チェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param length 最大文字数.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkCode(final String value, final Boolean isRequired, final String name, final String selector) {
        return checkHalfAlpNum(value, isRequired, 191, "-_\\.", name, "(「-」「_」「.」を含む)", selector);
    }

    /**
     * ログインID項目チェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkLoginId(final String value, final Boolean isRequired, final String name, final String selector) {
        return checkHalfAlpNum(value, isRequired, 191, "-_@\\.", name, "(「-」「_」「@」「.」を含む)", selector);
    }

    /**
     * パスワード項目チェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkPassword(final String value, final Boolean isRequired, final String name, final String selector) {
        return checkHalfAlpNum(value, isRequired, 8, 191, "-_@\\.", name, "(「-」「_」「@」「.」を含む)", selector);
    }

    /**
     * スペースチェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkSpace(final String value, final Boolean isRequired, final String name, final String selector) {
        if (isRequired) {
            if (!checkRequired(value, name, selector)) {
                return false;
            }
        } else if (!ReChecker.checkNotEmpty(value)) {
            return true;
        }
        if (value.contains(" ") || value.contains("　") || value.contains("\t")) {
            addMessage(msgModel.getMessage("E00016", name + "にスペース(又はタブ)").setSelector(selector));
            return false;
        }
        return true;
    }

    /**
     * 汎用コード項目チェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param length 最大文字数.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkCode(final String value, final Boolean isRequired, final int length, final String name, final String selector) {
        return checkHalfAlpNum(value, isRequired, length, "-", name, "(「-」を含む)", selector);
    }

    /**
     * 数値コード項目チェックを行います.
     * @param value 確認対象の値.
     * @param isRequired 必須有無.
     * @param length 最大文字数.
     * @param name 対象の項目名.
     * @param selector セレクタ−.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean checkNumCode(final String value, final Boolean isRequired, final int length, final String name, final String selector) {
        return checkHalfNum(value, isRequired, length, "-", name, "(「-」を含む)", selector);
    }


}
