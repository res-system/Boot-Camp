package com.res_system.re_employee_manager.commons.model.kind;

import com.res_system.commons.mvc.model.IListItem;
import com.res_system.commons.mvc.model.kind.IKind;
import com.res_system.re_employee_manager.commons.model.entities.ListItem;

/**
 * <pre>
 * アカウント状態区分.
 * </pre>
 * @author res.
 */
public enum AcStatKind implements IKind {
    /** 仮登録. */
    TEMP_REGIST("00"),
    /** 有効. */
    ENABLE("10"),
    /** 休止. */
    CESSATION("80"),
    /** 無効. */
    DISABLE("90");



    //---------------------------------------------- constructor.
    /** コンストラクタ. */
    private AcStatKind(final String value) {
        this.value = value;
        switch (this.value){
        case "00":
            this.text = "仮登録";
            break;
        case "10":
            this.text = "有効";
            break;
        case "80":
            this.text = "休止";
            break;
        case "90":
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
