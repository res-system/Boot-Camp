package com.res_system.re_emp_manager.commons.data;

import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.mvc.model.IListItem;

/**
 * <pre>
 * 汎用リストオブジェクト.
 * </pre>
 * @author res.
 */
public class ListItem implements IListItem, IEntity {

    //---------------------------------------------- properies [private].
    /** 値. */
    private String value;
    /** 表示文字列. */
    private String text;
    /** 無効(true)／有効(false). */
    private boolean disabled;

    //-- setter / getter. --//
    /** 値 を取得します. */
    @Override
    public String getText() { return text; }
    /** 表示文字列 を取得します. */
    @Override
    public String getValue() { return value; }
    /** 無効(true)／有効(false) を取得します. */
    @Override
    public boolean getDisabled() { return disabled; }



    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    public ListItem() {
        this(null, null , false);
    }

    /**
     * コンストラクタ.
     * @param value 値.
     * @param text 表示文字列.
     */
    public ListItem(String value, String text) {
        this(value, text , false);
    }

    /**
     * コンストラクタ.
     * @param value 値.
     * @param text 表示文字列.
     * @param disabled 無効(true)／有効(false).
     */
    public ListItem(String value, String text, boolean disabled) {
        super();
        this.value = value;
        this.text = text;
        this.disabled = disabled;
    }

}
