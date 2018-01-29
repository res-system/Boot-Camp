package com.res_system.re_employee_manager.commons.model.entities.table;

import java.sql.Timestamp;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;

/**
 * ログイン認証トラン Entityクラス.
 * @author res.
 *  generated at 2018/01/04.
 */
@Table(name="c_t_login", as="LGN")
public class CTLogin implements IEntity {

    /** 認証トークン. */
    @Key
    @Column
    private String token;
    /** 区分. */
    @Column
    private String kbn;
    /** アカウントID. */
    @Column
    private Long account_id;
    /** 選択グループID. */
    @Column
    private Long selected_group_id;
    /** ログイン日時. */
    @Column
    private Timestamp login_dt;
    /** 有効期限. */
    @Column
    private Timestamp expiration_time;
    /** 保存フラグ. */
    @Column
    private String save_flg;
    /** クライアント情報. */
    @Column
    private String client_info;

    //-- setter / getter. --//
    /** 認証トークン を取得します. */
    public String getToken() { return token; }
    /** 認証トークン を設定します. */
    public void setToken(String token) { this.token = token; }
    /** 区分 を取得します. */
    public String getKbn() { return kbn; }
    /** 区分 を設定します. */
    public void setKbn(String kbn) { this.kbn = kbn; }
    /** アカウントID を取得します. */
    public Long getAccount_id() { return account_id; }
    /** アカウントID を設定します. */
    public void setAccount_id(Long account_id) { this.account_id = account_id; }
    /** 選択グループID を取得します. */
    public Long getSelected_group_id() { return selected_group_id; }
    /** 選択グループID を設定します. */
    public void setSelected_group_id(Long selected_group_id) { this.selected_group_id = selected_group_id; }
    /** ログイン日時 を取得します. */
    public Timestamp getLogin_dt() { return login_dt; }
    /** ログイン日時 を設定します. */
    public void setLogin_dt(Timestamp login_dt) { this.login_dt = login_dt; }
    /** 有効期限 を取得します. */
    public Timestamp getExpiration_time() { return expiration_time; }
    /** 有効期限 を設定します. */
    public void setExpiration_time(Timestamp expiration_time) { this.expiration_time = expiration_time; }
    /** 保存フラグ を取得します. */
    public String getSave_flg() { return save_flg; }
    /** 保存フラグ を設定します. */
    public void setSave_flg(String save_flg) { this.save_flg = save_flg; }
    /** クライアント情報 を取得します. */
    public String getClient_info() { return client_info; }
    /** クライアント情報 を設定します. */
    public void setClient_info(String client_info) { this.client_info = client_info; }

}
