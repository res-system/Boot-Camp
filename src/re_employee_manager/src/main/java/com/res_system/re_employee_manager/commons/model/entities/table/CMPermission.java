package com.res_system.re_employee_manager.commons.model.entities.table;

import java.sql.Timestamp;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;
import com.res_system.re_employee_manager.commons.model.entities.IIdMsEntity;

/**
 * 権限許可マスタ Entityクラス.
 * @author res.
 *  generated at 2018/01/04.
 */
@Table(name="c_m_permission", as="PER")
public class CMPermission implements IIdMsEntity {

    /** 許可ID. */
    @Key
    @Column
    private Long id;
    /** 機能. */
    @Column
    private String key;
    /** デフォルトURL. */
    @Column
    private String default_url;
    /** 対象フラグ. */
    @Column
    private Integer flg;
    /** 名称. */
    @Column
    private String name;
    /** 備考. */
    @Column
    private String memo;
    /** 状態. */
    @Column
    private String status;
    /** 更新者ID. */
    @Column
    private Long updated_id;
    /** 更新日時. */
    @Column(isSelect=false,isInsert=false,isUpdate=false)
    private Timestamp updated;
    /** 作成者ID. */
    @Column
    private Long created_id;
    /** 作成日時. */
    @Column(isSelect=false,isInsert=false,isUpdate=false)
    private Timestamp created;

    //-- setter / getter. --//
    /** 許可ID を取得します. */
    public Long getId() { return id; }
    /** 許可ID を設定します. */
    public void setId(Long id) { this.id = id; }
    /** 機能 を取得します. */
    public String getKey() { return key; }
    /** 機能 を設定します. */
    public void setKey(String key) { this.key = key; }
    /** デフォルトURL を取得します. */
    public String getDefault_url() { return default_url; }
    /** デフォルトURL を設定します. */
    public void setDefault_url(String default_url) { this.default_url = default_url; }
    /** 対象フラグ を取得します. */
    public Integer getFlg() { return flg; }
    /** 対象フラグ を設定します. */
    public void setFlg(Integer flg) { this.flg = flg; }
    /** 名称 を取得します. */
    public String getName() { return name; }
    /** 名称 を設定します. */
    public void setName(String name) { this.name = name; }
    /** 備考 を取得します. */
    public String getMemo() { return memo; }
    /** 備考 を設定します. */
    public void setMemo(String memo) { this.memo = memo; }
    /** 状態 を取得します. */
    public String getStatus() { return status; }
    /** 状態 を設定します. */
    public void setStatus(String status) { this.status = status; }
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
