package com.res_system.re_employee_manager.commons.model.kind;

import com.res_system.commons.mvc.model.IListItem;
import com.res_system.commons.mvc.model.kind.IKind;
import com.res_system.re_employee_manager.commons.model.entities.ListItem;

/**
 * <pre>
 * 社員状態区分.
 * </pre>
 * @author res.
 */
public enum EmStatKind implements IKind {
    /** 仮登録. */
    TEMP_REGIST("00"),
    /** 在籍中. */
    IN_OFFICE("10"),
    /** 休職中. */
    ON_LEAVE("80"),
    /** 退社済. */
    RETIREMENT("90");



    //---------------------------------------------- constructor.
    /** コンストラクタ. */
    private EmStatKind(final String value) {
        this.value = value;
        switch (this.value){
        case "00":
            this.text = "仮登録";
            break;
        case "10":
            this.text = "在籍中";
            break;
        case "80":
            this.text = "休職中";
            break;
        case "90":
            this.text = "退社済";
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
