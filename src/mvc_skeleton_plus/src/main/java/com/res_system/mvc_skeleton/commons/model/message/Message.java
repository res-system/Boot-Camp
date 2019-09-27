package com.res_system.mvc_skeleton.commons.model.message;

import java.io.Serializable;

/**
 * <pre>
 * メッセージ情報クラス.
 * </pre>
 * @author res.
 */
@SuppressWarnings("serial")
public class Message implements Serializable {

    //---------------------------------------------- const [public].
    /** メッセージ種別[警告]. */
    public static final String ERROR = "E";
    /** メッセージ種別[注意]. */
    public static final String WORN = "W";
    /** メッセージ種別[情報]. */
    public static final String INFO = "I";



    //---------------------------------------------- properies [private].
    /** メッセージコード. */
    private String code;
    /** メッセージ種別. */
    private String kind;
    /** メッセージテキスト. */
    private String text;
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



    //---------------------------------------------- constructor.
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
        this.code = "";
        this.text = text;
        this.selector = "";
    }

}
