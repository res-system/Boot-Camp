package com.res_system.re_employee_manager.commons.model.entities.table;

import java.sql.Timestamp;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;

/**
 * 権限付与関連マスタ Entityクラス.
 * @author res.
 *  generated at 2018/01/04.
 */
@Table(name="c_r_auth_grant", as="AUGT")
public class CRAuthGrant implements IEntity {

    /** 権限ID. */
    @Key
    @Column
    private Long authority_id;
    /** 許可ID. */
    @Key
    @Column
    private Long permission_id;
    /** 連番. */
    @Column
    private Integer seq;
    /** 区分. */
    @Column
    private String kbn;
    /** 更新者ID. */
    @Column
    private Long updated_id;
    /** 更新日時. */
    @Column(isSelect=false,isInsert=false,isUpdate=false)
    private Timestamp updated;

    //-- setter / getter. --//
    /** 権限ID を取得します. */
    public Long getAuthority_id() { return authority_id; }
    /** 権限ID を設定します. */
    public void setAuthority_id(Long authority_id) { this.authority_id = authority_id; }
    /** 許可ID を取得します. */
    public Long getPermission_id() { return permission_id; }
    /** 許可ID を設定します. */
    public void setPermission_id(Long permission_id) { this.permission_id = permission_id; }
    /** 連番 を取得します. */
    public Integer getSeq() { return seq; }
    /** 連番 を設定します. */
    public void setSeq(Integer seq) { this.seq = seq; }
    /** 区分 を取得します. */
    public String getKbn() { return kbn; }
    /** 区分 を設定します. */
    public void setKbn(String kbn) { this.kbn = kbn; }
    /** 更新者ID を取得します. */
    public Long getUpdated_id() { return updated_id; }
    /** 更新者ID を設定します. */
    public void setUpdated_id(Long updated_id) { this.updated_id = updated_id; }
    /** 更新日時 を取得します. */
    public Timestamp getUpdated() { return updated; }
    /** 更新日時 を設定します. */
    public void setUpdated(Timestamp updated) { this.updated = updated; }

}
