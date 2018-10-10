package com.res_system.re_emp_manager.commons.model.entities;

import com.res_system.commons.mvc.model.form.Param;


import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;

/**
 * ログイン認証トラン Entityクラス.
 * @author res.
 *  generated at 2018/08/08. 
 */
@Table(name="t_login", as="LGN")
public class TLogin implements IEntity {

    //---------------------------------------------- properies [private].
    /** 認証トークン. */
    @Param
    @Key
    @Column
    private String token;
    /** ログイン日時. */
    @Param
    @Column(isInsert=false)
    private String login_dt;
    /** 有効期限. */
    @Param
    @Column
    private String expiration_time;
    /** 保存フラグ. */
    @Param
    @Column
    private String save_flg;
    /** アカウントID. */
    @Param
    @Column
    private String account_id;
    /** ユーザー区分. */
    @Param
    @Column
    private String user_kbn;
    /** ユーザーID. */
    @Param
    @Column
    private String user_id;
    /** グループID. */
    @Param
    @Column
    private String group_id;
    /** 権限ID. */
    @Param
    @Column
    private String authority_id;
    /** クライアント情報. */
    @Param
    @Column
    private String client_info;

    //-- setter / getter. --//
    /** 認証トークン を取得します. */
    public String getToken() { return token; }
    /** 認証トークン を設定します. */
    public void setToken(String token) { this.token = token; }
    /** ログイン日時 を取得します. */
    public String getLogin_dt() { return login_dt; }
    /** ログイン日時 を設定します. */
    public void setLogin_dt(String login_dt) { this.login_dt = login_dt; }
    /** 有効期限 を取得します. */
    public String getExpiration_time() { return expiration_time; }
    /** 有効期限 を設定します. */
    public void setExpiration_time(String expiration_time) { this.expiration_time = expiration_time; }
    /** 保存フラグ を取得します. */
    public String getSave_flg() { return save_flg; }
    /** 保存フラグ を設定します. */
    public void setSave_flg(String save_flg) { this.save_flg = save_flg; }
    /** アカウントID を取得します. */
    public String getAccount_id() { return account_id; }
    /** アカウントID を設定します. */
    public void setAccount_id(String account_id) { this.account_id = account_id; }
    /** ユーザー区分 を取得します. */
    public String getUser_kbn() { return user_kbn; }
    /** ユーザー区分 を設定します. */
    public void setUser_kbn(String user_kbn) { this.user_kbn = user_kbn; }
    /** ユーザーID を取得します. */
    public String getUser_id() { return user_id; }
    /** ユーザーID を設定します. */
    public void setUser_id(String user_id) { this.user_id = user_id; }
    /** グループID を取得します. */
    public String getGroup_id() { return group_id; }
    /** グループID を設定します. */
    public void setGroup_id(String group_id) { this.group_id = group_id; }
    /** 権限ID を取得します. */
    public String getAuthority_id() { return authority_id; }
    /** 権限ID を設定します. */
    public void setAuthority_id(String authority_id) { this.authority_id = authority_id; }
    /** クライアント情報 を取得します. */
    public String getClient_info() { return client_info; }
    /** クライアント情報 を設定します. */
    public void setClient_info(String client_info) { this.client_info = client_info; }

}
