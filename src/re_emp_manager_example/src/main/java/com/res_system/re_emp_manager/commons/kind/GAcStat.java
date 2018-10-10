package com.res_system.re_emp_manager.commons.kind;

import com.res_system.commons.mvc.model.IListItem;
import com.res_system.commons.mvc.model.kind.IKind;
import com.res_system.re_emp_manager.commons.data.ListItem;

/**
 * <pre>
 * グループアカウント状態.
 * </pre>
 * @author res.
 */
public enum GAcStat implements IKind {
    /** 招待中. */
    INVITING("0"),
    /** 有効. */
    ENABLE("1"),
    /** 休止. */
    CESSATION("8"),
    /** 無効. */
    DISABLE("9");



    //---------------------------------------------- constructor.
    /** コンストラクタ. */
    private GAcStat(final String value) {
        this.value = value;
        switch (this.value){
        case "0":
            this.text = "招待中";
            break;
        case "1":
            this.text = "有効";
            break;
        case "8":
            this.text = "休止";
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
