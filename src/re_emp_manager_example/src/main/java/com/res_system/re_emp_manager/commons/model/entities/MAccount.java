package com.res_system.re_emp_manager.commons.model.entities;

import com.res_system.commons.mvc.model.form.Param;


import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;

/**
 * アカウントマスタ Entityクラス.
 * @author res.
 *  generated at 2018/08/08. 
 */
@Table(name="m_account", as="ACC")
public class MAccount implements IEntity {

    //---------------------------------------------- properies [private].
    /** アカウントID. */
    @Param
    @Key
    @Column
    private String id;
    /** ログインID. */
    @Param
    @Column
    private String login_id;
    /** 登録日. */
    @Param
    @Column(isInsert=false)
    private String registration_date;
    /** アカウント状態. */
    @Param
    @Column
    private String acc_status;
    /** ユーザーID. */
    @Param
    @Column
    private String user_id;
    /** 備考. */
    @Param
    @Column
    private String memo;
    /** 更新者ID. */
    @Param
    @Column
    private String updated_id;
    /** 更新日時. */
    @Param
    @Column(isInsert=false)
    private String updated;
    /** 作成者ID. */
    @Param
    @Column
    private String created_id;
    /** 作成日時. */
    @Param
    @Column(isInsert=false,isUpdate=false)
    private String created;

    //-- setter / getter. --//
    /** アカウントID を取得します. */
    public String getId() { return id; }
    /** アカウントID を設定します. */
    public void setId(String id) { this.id = id; }
    /** ログインID を取得します. */
    public String getLogin_id() { return login_id; }
    /** ログインID を設定します. */
    public void setLogin_id(String login_id) { this.login_id = login_id; }
    /** 登録日 を取得します. */
    public String getRegistration_date() { return registration_date; }
    /** 登録日 を設定します. */
    public void setRegistration_date(String registration_date) { this.registration_date = registration_date; }
    /** アカウント状態 を取得します. */
    public String getAcc_status() { return acc_status; }
    /** アカウント状態 を設定します. */
    public void setAcc_status(String acc_status) { this.acc_status = acc_status; }
    /** ユーザーID を取得します. */
    public String getUser_id() { return user_id; }
    /** ユーザーID を設定します. */
    public void setUser_id(String user_id) { this.user_id = user_id; }
    /** 備考 を取得します. */
    public String getMemo() { return memo; }
    /** 備考 を設定します. */
    public void setMemo(String memo) { this.memo = memo; }
    /** 更新者ID を取得します. */
    public String getUpdated_id() { return updated_id; }
    /** 更新者ID を設定します. */
    public void setUpdated_id(String updated_id) { this.updated_id = updated_id; }
    /** 更新日時 を取得します. */
    public String getUpdated() { return updated; }
    /** 更新日時 を設定します. */
    public void setUpdated(String updated) { this.updated = updated; }
    /** 作成者ID を取得します. */
    public String getCreated_id() { return created_id; }
    /** 作成者ID を設定します. */
    public void setCreated_id(String created_id) { this.created_id = created_id; }
    /** 作成日時 を取得します. */
    public String getCreated() { return created; }
    /** 作成日時 を設定します. */
    public void setCreated(String created) { this.created = created; }

}
