package com.res_system.re_emp_manager.commons.kind;

import com.res_system.commons.mvc.model.IListItem;
import com.res_system.commons.mvc.model.kind.IKind;
import com.res_system.re_emp_manager.commons.data.ListItem;

/**
 * <pre>
 * 同居区分.
 * </pre>
 * @author res.
 */
public enum Living implements IKind {
    /** 同居. */
    TOGETHER("0"),
    /** 別居. */
    SEPARATELY("1");



    //---------------------------------------------- constructor.
    /** コンストラクタ. */
    private Living(final String value) {
        this.value = value;
        switch (this.value){
        case "0":
            this.text = "同居";
            break;
        case "1":
            this.text = "別居";
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
