package com.res_system.re_emp_manager.commons.model.entities;

import com.res_system.commons.mvc.model.form.Param;


import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;

/**
 * グループマスタ Entityクラス.
 * @author res.
 *  generated at 2018/08/06. 
 */
@Table(name="m_group", as="GRP")
public class MGroup implements IEntity {

    //---------------------------------------------- properies [private].
    /** グループID. */
    @Param
    @Key
    @Column
    private String id;
    /** 識別コード. */
    @Param
    @Column
    private String code;
    /** グループ名. */
    @Param
    @Column
    private String name;
    /** ルートグループID. */
    @Param
    @Column
    private String root_group_id;
    /** オーナーアカウントID. */
    @Param
    @Column
    private String owner_account_id;
    /** グループ状態. */
    @Param
    @Column
    private String grp_status;
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
    /** グループID を取得します. */
    public String getId() { return id; }
    /** グループID を設定します. */
    public void setId(String id) { this.id = id; }
    /** 識別コード を取得します. */
    public String getCode() { return code; }
    /** 識別コード を設定します. */
    public void setCode(String code) { this.code = code; }
    /** グループ名 を取得します. */
    public String getName() { return name; }
    /** グループ名 を設定します. */
    public void setName(String name) { this.name = name; }
    /** ルートグループID を取得します. */
    public String getRoot_group_id() { return root_group_id; }
    /** ルートグループID を設定します. */
    public void setRoot_group_id(String root_group_id) { this.root_group_id = root_group_id; }
    /** オーナーアカウントID を取得します. */
    public String getOwner_account_id() { return owner_account_id; }
    /** オーナーアカウントID を設定します. */
    public void setOwner_account_id(String owner_account_id) { this.owner_account_id = owner_account_id; }
    /** グループ状態 を取得します. */
    public String getGrp_status() { return grp_status; }
    /** グループ状態 を設定します. */
    public void setGrp_status(String grp_status) { this.grp_status = grp_status; }
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
