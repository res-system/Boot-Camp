package com.res_system.re_employee_manager.commons.model.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Ajaxアクション返却オブジェクト(JSON).
 * </pre>
 * @author res.
 */
@SuppressWarnings("serial")
public class AjaxResponse implements Serializable {

    //---------------------------------------------- const [public].
    /** 処理ステータス[OK]. */
    public static final String OK = "OK";
    /** 処理ステータス[NG]. */
    public static final String NG = "NG";



    //---------------------------------------------- properies [private].
    /** 処理ステータス(OK or NG). */
    private String status;
    /** 返却データ. */
    private Object data;
    /** メッセージ. */
    private List<Message> messageList;

    //-- setter / getter. --//
    /** 処理ステータス を取得します. */
    public String getStatus() { return status; }
    /** 処理ステータス を設定します. */
    public AjaxResponse setStatus(String status) {
        if (OK.equals(status) || NG.equals(status)) {
            this.status = status;
        } else {
            this.status = NG;
        }
        return this;
    }
    /** 返却データ を取得します. */
    public Object getData() { return data; }
    /** 返却データ を設定します. */
    public AjaxResponse setData(Object data) { this.data = data; return this; }
    /** メッセージ を取得します. */
    public List<Message> getMessageList() { return messageList; }
    /** メッセージ を設定します. */
    public AjaxResponse setMessageList(List<Message> messageList) { this.messageList = messageList; return this; }



    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    public AjaxResponse() {
        this(null, null);
    }

    /**
     * コンストラクタ.
     * @param status 処理ステータス.
     */
    public AjaxResponse(String status) {
        this(status, null);
    }

    /**
     * コンストラクタ.
     * @param status 処理ステータス.
     * @param data 返却データ.
     */
    public AjaxResponse(String status, Object data) {
        super();
        setStatus(status);
        this.data = data;
        messageList = new ArrayList<>();
    }



    //---------------------------------------------- [public] メッセージ処理.
    /**
     * メッセージのクリア.
     */
    public void clearMessage() {
        this.messageList.clear();
    }

    /**
     * メッセージの追加.
     * @param message メッセージ.
     */
    public void addMessage(Message message) {
        this.messageList.add(message);
    }

}
