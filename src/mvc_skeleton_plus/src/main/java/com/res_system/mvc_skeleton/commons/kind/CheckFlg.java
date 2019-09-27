package com.res_system.mvc_skeleton.commons.kind;

import com.res_system.commons.mvc.model.IListItem;
import com.res_system.commons.mvc.model.kind.IKind;
import com.res_system.mvc_skeleton.commons.data.ListItem;

/**
 * <pre>
 * チェックフラグ.
 * </pre>
 * @author res.
 */
public enum CheckFlg implements IKind {
    /** OFF. */
    OFF("0"),
    /** ON. */
    ON("1");



    //---------------------------------------------- constructor.
    /** コンストラクタ. */
    private CheckFlg(final String value) {
        this.value = value;
        switch (this.value){
        case "0":
            this.text = "OFF";
            break;
        case "1":
            this.text = "ON";
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
