package com.res_system.re_emp_manager.commons.kind;

import com.res_system.commons.mvc.model.IListItem;
import com.res_system.commons.mvc.model.kind.IKind;
import com.res_system.re_emp_manager.commons.data.ListItem;

/**
 * <pre>
 * ユーザー区分.
 * </pre>
 * @author res.
 */
public enum UsrKbn implements IKind {
    /** システム. */
    SYSTEM("1"),
    /** 一般. */
    NORMAL("2"),
    /** グループ. */
    GROUP("3");



    //---------------------------------------------- constructor.
    /** コンストラクタ. */
    private UsrKbn(final String value) {
        this.value = value;
        switch (this.value){
        case "1":
            this.text = "システム";
            break;
        case "2":
            this.text = "一般";
            break;
        case "3":
            this.text = "グループ";
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
