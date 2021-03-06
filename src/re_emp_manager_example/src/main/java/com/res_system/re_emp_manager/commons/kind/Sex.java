package com.res_system.re_emp_manager.commons.kind;

import com.res_system.commons.mvc.model.IListItem;
import com.res_system.commons.mvc.model.kind.IKind;
import com.res_system.re_emp_manager.commons.data.ListItem;

/**
 * <pre>
 * 性別.
 * </pre>
 * @author res.
 */
public enum Sex implements IKind {
    /** 男性. */
    MALE("0"),
    /** 女性. */
    FEMALE("1"),
    /** 不明. */
    UNKNOWN("2");



    //---------------------------------------------- constructor.
    /** コンストラクタ. */
    private Sex(final String value) {
        this.value = value;
        switch (this.value){
        case "0":
            this.text = "男性";
            break;
        case "1":
            this.text = "女性";
            break;
        case "2":
            this.text = "不明";
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
