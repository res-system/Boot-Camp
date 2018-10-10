package com.res_system.re_emp_manager.model.change_account_name;

import com.res_system.commons.mvc.model.form.DataParam;

/**
 * アカウント名変更画面処理 フォームクラス.
 * @author res.
 */
public class ChangeAccountNameForm {

    //---------------------------------------------- properies [private].
    /** 入力データ. */
    @DataParam
    private ChangeAccountNamData data;
    /** メッセージ区分. */
    private String messageKbn;

    //-- setter / getter. --//
    /** 入力データ を取得します. */
    public ChangeAccountNamData getData() { return data; }
    /** 入力データ を設定します. */
    public void setData(ChangeAccountNamData data) { this.data = data; }
    /** メッセージ区分 を取得します. */
    public String getMessageKbn() { return messageKbn; }
    /** メッセージ区分 を設定します. */
    public void setMessageKbn(String messageKbn) { this.messageKbn = messageKbn; }


    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    public ChangeAccountNameForm() {
        super();
        data = new ChangeAccountNamData();
    }


}
