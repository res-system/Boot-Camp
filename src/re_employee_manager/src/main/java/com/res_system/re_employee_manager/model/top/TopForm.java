package com.res_system.re_employee_manager.model.top;

import com.res_system.commons.mvc.model.form.Param;

/**
 * <pre>
 * TOP画面 情報クラス.
 * </pre>
 * @author res.
 */
public class TopForm {

    //---------------------------------------------- properies [private].
    /** ログインID. */
    @Param
    private String login_id;
    /** パスワード. */
    @Param
    private String password;
    /** 認証情報保存フラグ. */
    @Param
    private String save;
    /** 遷移先. */
    @Param
    private String next;

    //-- setter / getter. --//
    /** ログインID を取得します. */
    public String getLogin_id() { return login_id; }
    /** ログインID を設定します. */
    public void setLogin_id(String login_id) { this.login_id = login_id; }
    /** パスワード を取得します. */
    public String getPassword() { return password; }
    /** パスワード を設定します. */
    public void setPassword(String password) { this.password = password; }
    /** 認証情報保存フラグ を取得します. */
    public String getSave() { return save; }
    /** 認証情報保存フラグ を設定します. */
    public void setSave(String save) { this.save = save; }
    /** 遷移先 を取得します. */
    public String getNext() { return next; }
    /** 遷移先 を設定します. */
    public void setNext(String next) { this.next = next; }

}
