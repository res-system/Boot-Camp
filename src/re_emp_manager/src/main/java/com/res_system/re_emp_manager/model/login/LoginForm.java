package com.res_system.re_emp_manager.model.login;

import com.res_system.commons.mvc.model.form.Param;

/**
 * ログイン画面 フォームクラス.
 * @author res.
 */
public class LoginForm {

    //---------------------------------------------- properies [private].
    /** グループ識別コード. */
    @Param
    private String code;
    /** ログインID. */
    @Param
    private String id;
    /** パスワード. */
    @Param
    private String key;
    /** ログイン状態保持. */
    @Param
    private String save;
    /** 遷移先. */
    @Param
    private String next;

    //-- setter / getter. --//
    /** グループ識別コード を取得します. */
    public String getCode() { return code; }
    /** グループ識別コード を設定します. */
    public void setCode(String code) { this.code = code; }
    /** ログインID を取得します. */
    public String getId() { return id; }
    /** ログインID を設定します. */
    public void setId(String id) { this.id = id; }
    /** パスワード を取得します. */
    public String getKey() { return key; }
    /** パスワード を設定します. */
    public void setKey(String key) { this.key = key; }
    /** ログイン状態保持 を取得します. */
    public String getSave() { return save; }
    /** ログイン状態保持 を設定します. */
    public void setSave(String save) { this.save = save; }
    /** 遷移先 を取得します. */
    public String getNext() { return next; }
    /** 遷移先 を設定します. */
    public void setNext(String next) { this.next = next; }

}