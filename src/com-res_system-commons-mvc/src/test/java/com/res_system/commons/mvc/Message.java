package com.res_system.commons.mvc;

import java.io.Serializable;

/**
 * メッセージ情報.
 * @author res.
 */
@SuppressWarnings("serial")
public class Message implements Serializable {

    //-------------------------------------------------------------------------------------------------------------------------
    //-- 定数.
    /** メッセージ種別[警告]. */
    public static final String ERROR = "E";
    /** メッセージ種別[注意]. */
    public static final String WORN = "W";
    /** メッセージ種別[情報]. */
    public static final String INFO = "I";

    /** ボタン種別[制御なし]. */
    public static final String BTN_NONE = "0";
    /** ボタン種別[警告(OK)]. */
    public static final String BTN_ERROR = "1";
    /** ボタン種別[情報(OK)]. */
    public static final String BTN_INFO = "2";
    /** ボタン種別[確認(YES/NO)]. */
    public static final String BTN_YES_NO = "3";
    /** ボタン種別[確認(YES/NO/CANCEL)]. */
    public static final String BTN_YES_NO_CANCEL = "4";


    //-------------------------------------------------------------------------------------------------------------------------
    //-- メンバ変数.
    /** メッセージコード. */
    private String code;
    /** メッセージ種別. */
    private String kind;
    /** メッセージテキスト. */
    private String text;
    /** ボタン種別. */
    private String button;
    /** セレクター. */
    private String selector;

    //-- setter / getter. --//
    /** メッセージコード を取得します. */
    public String getCode() { return code; }
    /** メッセージコード を設定します. */
    public Message setCode(String code) { this.code = code; return this; }
    /** メッセージ種別 を取得します. */
    public String getKind() { return kind; }
    /** メッセージ種別 を設定します. */
    public Message setKind(String kind) {
        if (INFO.equals(kind) || WORN.equals(kind) || ERROR.equals(kind)) {
            this.kind = kind;
        } else {
            this.kind = ERROR;
        }
        return this;
    }
    /** メッセージテキスト を取得します. */
    public String getText() { return text; }
    /** メッセージテキスト を設定します. */
    public Message setText(String text) { this.text = text; return this; }
    /** ボタン種別 を取得します. */
    public String getButton() { return button; }
    /** ボタン種別 を設定します. */
    public Message setButton(String button) {
        if (BTN_ERROR.equals(button) || BTN_INFO.equals(button) || BTN_YES_NO.equals(button) || BTN_YES_NO_CANCEL.equals(button)) {
            this.button = button;
        } else {
            this.button = BTN_ERROR;
        }
        this.button = button; return this; }
    /** セレクター を取得します. */
    public String getSelector() { return selector; }
    /** セレクター を設定します. */
    public Message setSelector(String selector) { this.selector = selector; return this; }
    /** セレクター を追加します. */
    public Message addSelector(String selector) {
        if (selector != null && selector.length() > 0) {
            if (this.selector != null && this.selector.length() > 0
                    && selector != null && selector.length() > 0) {
                this.selector += "," + selector.trim();
            } else {
                this.selector = selector.trim();
            }
        }
        return this;
    }


    //-------------------------------------------------------------------------------------------------------------------------
    //-- コンストラクタ.
    /**
     * コンストラクタ.
     */
    public Message() {
        this(null);
    }

    /**
     * コンストラクタ.
     * @param text メッセージテキスト.
     */
    public Message(String text) {
        this(null, text);
    }

    /**
     * コンストラクタ.
     * @param kind メッセージ種別.
     * @param text メッセージテキスト.
     */
    public Message(String kind, String text) {
        setKind(kind);
        setButton(null);
        this.code = "";
        this.text = text;
        this.selector = "";
    }


}
