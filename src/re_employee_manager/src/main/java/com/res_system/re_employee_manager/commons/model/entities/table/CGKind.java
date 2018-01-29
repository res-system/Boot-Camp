package com.res_system.re_employee_manager.commons.model.entities.table;

import java.sql.Timestamp;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;

/**
 * 種別マスタ Entityクラス.
 * @author res.
 *  generated at 2018/01/04.
 */
@Table(name="c_g_kind", as="KND")
public class CGKind implements IEntity {

    /** 区分. */
    @Key
    @Column
    private String kbn;
    /** 連番. */
    @Key
    @Column
    private Integer seq;
    /** コード. */
    @Column
    private String code;
    /** 名称. */
    @Column
    private String name;
    /** 備考. */
    @Column
    private String memo;
    /** 更新者ID. */
    @Column
    private Long updated_id;
    /** 更新日時. */
    @Column(isSelect=false,isInsert=false,isUpdate=false)
    private Timestamp updated;

    //-- setter / getter. --//
    /** 区分 を取得します. */
    public String getKbn() { return kbn; }
    /** 区分 を設定します. */
    public void setKbn(String kbn) { this.kbn = kbn; }
    /** 連番 を取得します. */
    public Integer getSeq() { return seq; }
    /** 連番 を設定します. */
    public void setSeq(Integer seq) { this.seq = seq; }
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
    public Long getUpdated_id() { return updated_id; }
    /** 更新者ID を設定します. */
    public void setUpdated_id(Long updated_id) { this.updated_id = updated_id; }
    /** 更新日時 を取得します. */
    public Timestamp getUpdated() { return updated; }
    /** 更新日時 を設定します. */
    public void setUpdated(Timestamp updated) { this.updated = updated; }

}
