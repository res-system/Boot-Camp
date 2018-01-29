package com.res_system.helloworldmvc.model.crud;

import com.res_system.commons.mvc.model.IListItem;

/**
 * <pre>
 * Crud用リストアイテムクラス(テスト用).
 * </pre>
 * @author res.
 */
public class CrudListItem implements IListItem {

    //---------------------------------------------- properies [private].
    /** 値. */
    private String value;
    /** 表示文字列. */
    private String text;
    /** 有効／無効. */
    private boolean disabled;

    //-- setter / getter. --//
    /** 値 を取得します. */
    @Override
    public String getText() { return text; }
    /** 表示文字列 を取得します. */
    @Override
    public String getValue() { return value; }
    /** 有効／無効 を取得します. */
    @Override
    public boolean getDisabled() { return disabled; }



    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     * @param value 値.
     * @param text 表示文字列.
     * @param disabled 有効／無効.
     */
    public CrudListItem(String value, String text, boolean disabled) {
        super();
        this.value = value;
        this.text = text;
        this.disabled = disabled;
    }

}
