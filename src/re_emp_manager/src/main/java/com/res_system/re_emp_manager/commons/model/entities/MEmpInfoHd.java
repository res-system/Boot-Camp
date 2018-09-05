package com.res_system.re_emp_manager.commons.model.entities;

import com.res_system.commons.mvc.model.form.Param;


import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;

/**
 * 社員情報ヘッダーマスタ Entityクラス.
 * @author res.
 *  generated at 2018/08/06. 
 */
@Table(name="m_emp_info_hd", as="EMPHD")
public class MEmpInfoHd implements IEntity {

    //---------------------------------------------- properies [private].
    /** 社員情報ヘッダーID. */
    @Param
    @Key
    @Column
    private String id;
    /** ルートグループID. */
    @Param
    @Column
    private String root_group_id;
    /** 連番. */
    @Param
    @Column
    private String seq;
    /** ヘッダラベル. */
    @Param
    @Column
    private String label;
    /** タイプ. */
    @Param
    @Column
    private String type;
    /** 必須フラグ. */
    @Param
    @Column
    private String required;
    /** 長さ. */
    @Param
    @Column
    private String maxlength;
    /** 状態. */
    @Param
    @Column
    private String status;
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
    /** 社員情報ヘッダーID を取得します. */
    public String getId() { return id; }
    /** 社員情報ヘッダーID を設定します. */
    public void setId(String id) { this.id = id; }
    /** ルートグループID を取得します. */
    public String getRoot_group_id() { return root_group_id; }
    /** ルートグループID を設定します. */
    public void setRoot_group_id(String root_group_id) { this.root_group_id = root_group_id; }
    /** 連番 を取得します. */
    public String getSeq() { return seq; }
    /** 連番 を設定します. */
    public void setSeq(String seq) { this.seq = seq; }
    /** ヘッダラベル を取得します. */
    public String getLabel() { return label; }
    /** ヘッダラベル を設定します. */
    public void setLabel(String label) { this.label = label; }
    /** タイプ を取得します. */
    public String getType() { return type; }
    /** タイプ を設定します. */
    public void setType(String type) { this.type = type; }
    /** 必須フラグ を取得します. */
    public String getRequired() { return required; }
    /** 必須フラグ を設定します. */
    public void setRequired(String required) { this.required = required; }
    /** 長さ を取得します. */
    public String getMaxlength() { return maxlength; }
    /** 長さ を設定します. */
    public void setMaxlength(String maxlength) { this.maxlength = maxlength; }
    /** 状態 を取得します. */
    public String getStatus() { return status; }
    /** 状態 を設定します. */
    public void setStatus(String status) { this.status = status; }
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
