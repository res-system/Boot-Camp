package com.res_system.re_employee_manager.commons.model.entities.table;

import java.sql.Timestamp;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;

/**
 * グループ別アカウント関連マスタ Entityクラス.
 * @author res.
 *  generated at 2018/01/23.
 */
@Table(name="c_r_grp_account", as="GPAC")
public class CRGrpAccount implements IEntity {

    /** グループID. */
    @Key
    @Column
    private Long group_id;
    /** アカウントID. */
    @Key
    @Column
    private Long account_id;
    /** 連番. */
    @Column
    private Integer seq;
    /** アカウント内順番. */
    @Column
    private Integer account_seq;
    /** グループ内権限ID. */
    @Column
    private Long group_authority_id;
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
    @Column(isInsert=false,isUpdate=false)
    private Timestamp updated;

    //-- setter / getter. --//
    /** グループID を取得します. */
    public Long getGroup_id() { return group_id; }
    /** グループID を設定します. */
    public void setGroup_id(Long group_id) { this.group_id = group_id; }
    /** アカウントID を取得します. */
    public Long getAccount_id() { return account_id; }
    /** アカウントID を設定します. */
    public void setAccount_id(Long account_id) { this.account_id = account_id; }
    /** 連番 を取得します. */
    public Integer getSeq() { return seq; }
    /** 連番 を設定します. */
    public void setSeq(Integer seq) { this.seq = seq; }
    /** アカウント内順番 を取得します. */
    public Integer getAccount_seq() { return account_seq; }
    /** アカウント内順番 を設定します. */
    public void setAccount_seq(Integer account_seq) { this.account_seq = account_seq; }
    /** グループ内権限ID を取得します. */
    public Long getGroup_authority_id() { return group_authority_id; }
    /** グループ内権限ID を設定します. */
    public void setGroup_authority_id(Long group_authority_id) { this.group_authority_id = group_authority_id; }
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

}
