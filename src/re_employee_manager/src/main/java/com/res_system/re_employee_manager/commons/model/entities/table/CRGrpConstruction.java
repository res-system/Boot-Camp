package com.res_system.re_employee_manager.commons.model.entities.table;

import java.sql.Timestamp;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;

/**
 * グループ構成関連マスタ Entityクラス.
 * @author res.
 *  generated at 2018/01/04.
 */
@Table(name="c_r_grp_construction", as="GPCN")
public class CRGrpConstruction implements IEntity {

    /** 祖先グループID. */
    @Key
    @Column
    private Long ancestor_id;
    /** 子孫グループID. */
    @Key
    @Column
    private Long descendant_id;
    /** 階層. */
    @Column
    private Integer path_length;
    /** 更新者ID. */
    @Column
    private Long updated_id;
    /** 更新日時. */
    @Column(isSelect=false,isInsert=false,isUpdate=false)
    private Timestamp updated;

    //-- setter / getter. --//
    /** 祖先グループID を取得します. */
    public Long getAncestor_id() { return ancestor_id; }
    /** 祖先グループID を設定します. */
    public void setAncestor_id(Long ancestor_id) { this.ancestor_id = ancestor_id; }
    /** 子孫グループID を取得します. */
    public Long getDescendant_id() { return descendant_id; }
    /** 子孫グループID を設定します. */
    public void setDescendant_id(Long descendant_id) { this.descendant_id = descendant_id; }
    /** 階層 を取得します. */
    public Integer getPath_length() { return path_length; }
    /** 階層 を設定します. */
    public void setPath_length(Integer path_length) { this.path_length = path_length; }
    /** 更新者ID を取得します. */
    public Long getUpdated_id() { return updated_id; }
    /** 更新者ID を設定します. */
    public void setUpdated_id(Long updated_id) { this.updated_id = updated_id; }
    /** 更新日時 を取得します. */
    public Timestamp getUpdated() { return updated; }
    /** 更新日時 を設定します. */
    public void setUpdated(Timestamp updated) { this.updated = updated; }

}
