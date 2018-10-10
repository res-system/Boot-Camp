package com.res_system.re_emp_manager.commons.kind;

import com.res_system.commons.mvc.model.IListItem;
import com.res_system.commons.mvc.model.kind.IKind;
import com.res_system.re_emp_manager.commons.data.ListItem;

/**
 * <pre>
 * 保存フラグ.
 * </pre>
 * @author res.
 */
public enum SaveFlg implements IKind {
    /** 保存しない. */
    NO_SAVE("0"),
    /** 保存する. */
    SAVE("1");



    //---------------------------------------------- constructor.
    /** コンストラクタ. */
    private SaveFlg(final String value) {
        this.value = value;
        switch (this.value){
        case "0":
            this.text = "保存しない";
            break;
        case "1":
            this.text = "保存する";
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
