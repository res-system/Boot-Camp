package com.res_system.re_emp_manager.commons.kind;

import com.res_system.commons.mvc.model.IListItem;
import com.res_system.commons.mvc.model.kind.IKind;
import com.res_system.re_emp_manager.commons.data.ListItem;

/**
 * <pre>
 * 権限.
 * </pre>
 * @author res.
 */
public enum Authority implements IKind {
    /** システム. */
    SYSTEM("100"),
    /** サービス. */
    NORMAL("200"),
    /** グループ. */
    GROUP("300"),
    /** マネージャー. */
    MANAGER("301"),
    /** リーダー. */
    LEADER("302"),
    /** メンバー. */
    MEMBER("303");



    //---------------------------------------------- constructor.
    /** コンストラクタ. */
    private Authority(final String value) {
        this.value = value;
        switch (this.value){
        case "100":
            this.text = "システム";
            break;
        case "200":
            this.text = "サービス";
            break;
        case "300":
            this.text = "グループ";
            break;
        case "301":
            this.text = "マネージャー";
            break;
        case "302":
            this.text = "リーダー";
            break;
        case "303":
            this.text = "メンバー";
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
