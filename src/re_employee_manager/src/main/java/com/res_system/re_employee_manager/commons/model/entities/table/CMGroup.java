package com.res_system.re_employee_manager.commons.model.entities.table;

import java.sql.Timestamp;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;
import com.res_system.re_employee_manager.commons.model.entities.IIdMsEntity;

/**
 * グループマスタ Entityクラス.
 * @author res.
 *  generated at 2018/01/04.
 */
@Table(name="c_m_group", as="GRP")
public class CMGroup implements IIdMsEntity {

    /** グループID. */
    @Key
    @Column
    private Long id;
    /** 識別コード. */
    @Column
    private String code;
    /** グループ名. */
    @Column
    private String name;
    /** グループ名(カナ). */
    @Column
    private String name_kana;
    /** 区分. */
    @Column
    private String kbn;
    /** 備考. */
    @Column
    private String memo;
    /** ルートグループID. */
    @Column
    private Long root_group_id;
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
    /** グループID を取得します. */
    public Long getId() { return id; }
    /** グループID を設定します. */
    public void setId(Long id) { this.id = id; }
    /** 識別コード を取得します. */
    public String getCode() { return code; }
    /** 識別コード を設定します. */
    public void setCode(String code) { this.code = code; }
    /** グループ名 を取得します. */
    public String getName() { return name; }
    /** グループ名 を設定します. */
    public void setName(String name) { this.name = name; }
    /** グループ名(カナ) を取得します. */
    public String getName_kana() { return name_kana; }
    /** グループ名(カナ) を設定します. */
    public void setName_kana(String name_kana) { this.name_kana = name_kana; }
    /** 区分 を取得します. */
    public String getKbn() { return kbn; }
    /** 区分 を設定します. */
    public void setKbn(String kbn) { this.kbn = kbn; }
    /** 備考 を取得します. */
    public String getMemo() { return memo; }
    /** 備考 を設定します. */
    public void setMemo(String memo) { this.memo = memo; }
    /** ルートグループID を取得します. */
    public Long getRoot_group_id() { return root_group_id; }
    /** ルートグループID を設定します. */
    public void setRoot_group_id(Long root_group_id) { this.root_group_id = root_group_id; }
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
