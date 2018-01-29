package com.res_system.re_employee_manager.commons.model.kind;

import com.res_system.commons.mvc.model.IListItem;
import com.res_system.commons.mvc.model.kind.IKind;
import com.res_system.re_employee_manager.commons.model.entities.ListItem;

/**
 * <pre>
 * 権限付与区分.
 * </pre>
 * @author res.
 */
public enum PerKbnKind implements IKind {
    /** 通常. */
    FULL("0"),
    /** 参照のみ. */
    REFERENCE_ONLY("1");



    //---------------------------------------------- constructor.
    /** コンストラクタ. */
    private PerKbnKind(final String value) {
        this.value = value;
        switch (this.value){
        case "0":
            this.text = "通常";
            break;
        case "1":
            this.text = "参照のみ";
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
