package com.res_system.re_emp_manager.commons.kind;

import com.res_system.commons.mvc.model.IListItem;
import com.res_system.commons.mvc.model.kind.IKind;
import com.res_system.re_emp_manager.commons.data.ListItem;

/**
 * <pre>
 * 情報タイプ.
 * </pre>
 * @author res.
 */
public enum InfType implements IKind {
    /** ラベル. */
    LABEL("00"),
    /** 文字. */
    TEXT("01"),
    /** メモ. */
    MEMO("02"),
    /** 日付. */
    DATE("03"),
    /** 数値. */
    NUMERIC("04"),
    /** 数字. */
    NUMBER("05"),
    /** 英数字. */
    ALPHA_NUMBER("06"),
    /** カナ. */
    KANA("07"),
    /** 電話番号. */
    TEL_NO("08"),
    /** メールアドレス. */
    EMAIL_ADDR("09"),
    /** コード. */
    CODE("10"),
    /** コード(数値). */
    CODE_NUM("11"),
    /** 郵便番号. */
    POSTAL_CODE("12"),
    /** スペース. */
    SPACE("90");
    


    //---------------------------------------------- constructor.
    /** コンストラクタ. */
    private InfType(final String value) {
        this.value = value;
        switch (this.value){
        case "00":
            this.text = "ラベル";
            break;
        case "01":
            this.text = "文字";
            break;
        case "02":
            this.text = "メモ";
            break;
        case "03":
            this.text = "日付";
            break;
        case "04":
            this.text = "数値";
            break;
        case "05":
            this.text = "数字";
            break;
        case "06":
            this.text = "英数字";
            break;
        case "07":
            this.text = "カナ";
            break;
        case "08":
            this.text = "電話番号";
            break;
        case "09":
            this.text = "メールアドレス";
            break;
        case "10":
            this.text = "コード";
            break;
        case "11":
            this.text = "コード(数値)";
            break;
        case "12":
            this.text = "郵便番号";
            break;
        case "90":
            this.text = "スペース";
            break;
        default:
            this.text = "";
            break;
        }
    }



    //---------------------------------------------- properies [private].
    /** 値. */
    private final String value;
    /** 表示文字. */
    private final String text;

    //-- setter / getter. --//
    /** 値 を取得します. */
    @Override
    public String getValue() { return value; }
    /** 表示文字 を取得します. */
    @Override
    public String getText() { return this.text; }



    //---------------------------------------------- [public].
    /** 値のチェックします. */
    @Override
    public boolean equals(String value) { return this.value.equals(value); }
    /** リストオブジェクトに変換します. */
    @Override
    public IListItem toItem() { return new ListItem(getValue(), getText()); }

}
