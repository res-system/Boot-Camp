package com.res_system.re_emp_manager.commons.kind;

import com.res_system.commons.mvc.model.IListItem;
import com.res_system.commons.mvc.model.kind.IKind;
import com.res_system.re_emp_manager.commons.data.ListItem;

/**
 * <pre>
 * 就業状況.
 * </pre>
 * @author res.
 */
public enum Sitch implements IKind {
    /** 予定. */
    PLANS("0"),
    /** 在職. */
    IN_OFFICE("1"),
    /** 休職. */
    ON_LEAVE("8"),
    /** 退職. */
    RETIRING("9");



    //---------------------------------------------- constructor.
    /** コンストラクタ. */
    private Sitch(final String value) {
        this.value = value;
        switch (this.value){
        case "0":
            this.text = "予定";
            break;
        case "1":
            this.text = "在職";
            break;
        case "8":
            this.text = "退職";
            break;
        case "9":
            this.text = "無効";
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
