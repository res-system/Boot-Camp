package com.res_system.re_emp_manager.commons.model.entities;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;
import com.res_system.commons.mvc.model.form.Param;

/**
 * 種別マスタ Entityクラス.
 * @author res.
 *  generated at 2018/08/06. 
 */
@Table(name="g_kind", as="KND")
public class GKind implements IEntity {

    //---------------------------------------------- properies [private].
    /** 区分. */
    @Param
    @Key
    @Column
    private String kbn;
    /** 連番. */
    @Param
    @Key
    @Column
    private String seq;
    /** コード. */
    @Param
    @Column
    private String code;
    /** 名称. */
    @Param
    @Column
    private String name;
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
    /** 区分 を取得します. */
    public String getKbn() { return kbn; }
    /** 区分 を設定します. */
    public void setKbn(String kbn) { this.kbn = kbn; }
    /** 連番 を取得します. */
    public String getSeq() { return seq; }
    /** 連番 を設定します. */
    public void setSeq(String seq) { this.seq = seq; }
    /** コード を取得します. */
    public String getCode() { return code; }
    /** コード を設定します. */
    public void setCode(String code) { this.code = code; }
    /** 名称 を取得します. */
    public String getName() { return name; }
    /** 名称 を設定します. */
    public void setName(String name) { this.name = name; }
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
