package com.res_system.re_employee_manager.commons.model.entities.table;

import java.sql.Date;
import java.sql.Timestamp;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;
import com.res_system.re_employee_manager.commons.model.entities.IIdMsEntity;

/**
 * アカウントマスタ Entityクラス.
 * @author res.
 *  generated at 2018/01/23.
 */
@Table(name="c_m_account", as="ACC")
public class CMAccount implements IIdMsEntity {

    /** アカウントID. */
    @Key
    @Column
    private Long id;
    /** ログインID. */
    @Column
    private String login_id;
    /** アカウント名. */
    @Column
    private String name;
    /** アカウント名(カナ). */
    @Column
    private String name_kana;
    /** デフォルト権限ID. */
    @Column
    private Long default_authority_id;
    /** 登録日. */
    @Column
    private Date registration_date;
    /** 抹消日. */
    @Column
    private Date deletion_date;
    /** ソルト. */
    @Column
    private String salt;
    /** 状態. */
    @Column
    private String acc_status;
    /** 更新者ID. */
    @Column
    private Long updated_id;
    /** 更新日時. */
    @Column(isInsert=false,isUpdate=false)
    private Timestamp updated;
    /** 作成者ID. */
    @Column
    private Long created_id;
    /** 作成日時. */
    @Column(isInsert=false,isUpdate=false)
    private Timestamp created;

    //-- setter / getter. --//
    /** アカウントID を取得します. */
    public Long getId() { return id; }
    /** アカウントID を設定します. */
    public void setId(Long id) { this.id = id; }
    /** ログインID を取得します. */
    public String getLogin_id() { return login_id; }
    /** ログインID を設定します. */
    public void setLogin_id(String login_id) { this.login_id = login_id; }
    /** アカウント名 を取得します. */
    public String getName() { return name; }
    /** アカウント名 を設定します. */
    public void setName(String name) { this.name = name; }
    /** アカウント名(カナ) を取得します. */
    public String getName_kana() { return name_kana; }
    /** アカウント名(カナ) を設定します. */
    public void setName_kana(String name_kana) { this.name_kana = name_kana; }
    /** デフォルト権限ID を取得します. */
    public Long getDefault_authority_id() { return default_authority_id; }
    /** デフォルト権限ID を設定します. */
    public void setDefault_authority_id(Long default_authority_id) { this.default_authority_id = default_authority_id; }
    /** 登録日 を取得します. */
    public Date getRegistration_date() { return registration_date; }
    /** 登録日 を設定します. */
    public void setRegistration_date(Date registration_date) { this.registration_date = registration_date; }
    /** 抹消日 を取得します. */
    public Date getDeletion_date() { return deletion_date; }
    /** 抹消日 を設定します. */
    public void setDeletion_date(Date deletion_date) { this.deletion_date = deletion_date; }
    /** ソルト を取得します. */
    public String getSalt() { return salt; }
    /** ソルト を設定します. */
    public void setSalt(String salt) { this.salt = salt; }
    /** 状態 を取得します. */
    public String getAcc_status() { return acc_status; }
    /** 状態 を設定します. */
    public void setAcc_status(String acc_status) { this.acc_status = acc_status; }
    /** 更新者ID を取得します. */
    public Long getUpdated_id() { return updated_id; }
    /** 更新者ID を設定します. */
    public void setUpdated_id(Long updated_id) { this.updated_id = updated_id; }
    /** 更新日時 を取得します. */
    public Timestamp getUpdated() { return updated; }
    /** 更新日時 を設定します. */
    public void setUpdated(Timestamp updated) { this.updated = updated; }
    /** 作成者ID を取得します. */
    public Long getCreated_id() { return created_id; }
    /** 作成者ID を設定します. */
    public void setCreated_id(Long created_id) { this.created_id = created_id; }
    /** 作成日時 を取得します. */
    public Timestamp getCreated() { return created; }
    /** 作成日時 を設定します. */
    public void setCreated(Timestamp created) { this.created = created; }

}
