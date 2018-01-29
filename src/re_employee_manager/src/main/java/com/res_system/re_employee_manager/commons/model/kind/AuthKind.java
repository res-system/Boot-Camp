package com.res_system.re_employee_manager.commons.model.kind;

import com.res_system.commons.mvc.model.IListItem;
import com.res_system.commons.mvc.model.kind.IKind;
import com.res_system.re_employee_manager.commons.model.entities.ListItem;

/**
 * <pre>
 * 権限区分.
 * </pre>
 * @author res.
 */
public enum AuthKind implements IKind {
    /** システムユーザー. */
    SYSTEM("1"),
    /** ルートユーザー. */
    ROOT("2"),
    /** 管理者ユーザー. */
    ADMINISTRATOR("3"),
    /** 一般ユーザー. */
    REGULAR_EMPLOYEE("4"),
    /** ゲストユーザー. */
    GUESTS("5");



    //---------------------------------------------- constructor.
    /** コンストラクタ. */
    private AuthKind(final String value) {
        this.value = value;
        switch (this.value){
       case "1":
            this.text = "システムユーザー";
            break;
       case "2":
           this.text = "ルートユーザー";
           break;
       case "3":
           this.text = "管理者ユーザー";
           break;
        case "4":
            this.text = "一般ユーザー";
            break;
        case "5":
            this.text = "ゲストユーザー";
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
    /** 値のチェックします. */
    public boolean equals(Long value) { return equals(String.valueOf(value)); }
    /** リストオブジェクトに変換します. */
    @Override
    public IListItem toItem() { return new ListItem(getValue(), getText()); }

}
