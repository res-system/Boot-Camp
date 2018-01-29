package com.res_system.re_employee_manager.commons.model.entities.table;

import java.sql.Timestamp;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;
import com.res_system.re_employee_manager.commons.model.entities.IAcctSubEntity;

/**
 * アカウント連絡先サブマスタ Entityクラス.
 * @author res.
 *  generated at 2018/01/04.
 */
@Table(name="c_s_acct_contact", as="ACCT")
public class CSAcctContact implements IAcctSubEntity {

    /** アカウントID. */
    @Key
    @Column
    private Long account_id;
    /** 連番. */
    @Key
    @Column
    private Integer seq;
    /** 連絡先ID. */
    @Column
    private Long contact_id;
    /** 区分. */
    @Column
    private String kbn;
    /** 備考. */
    @Column
    private String memo;
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
    /** アカウントID を取得します. */
    public Long getAccount_id() { return account_id; }
    /** アカウントID を設定します. */
    public void setAccount_id(Long account_id) { this.account_id = account_id; }
    /** 連番 を取得します. */
    public Integer getSeq() { return seq; }
    /** 連番 を設定します. */
    public void setSeq(Integer seq) { this.seq = seq; }
    /** 連絡先ID を取得します. */
    public Long getContact_id() { return contact_id; }
    /** 連絡先ID を設定します. */
    public void setContact_id(Long contact_id) { this.contact_id = contact_id; }
    /** 区分 を取得します. */
    public String getKbn() { return kbn; }
    /** 区分 を設定します. */
    public void setKbn(String kbn) { this.kbn = kbn; }
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
    /** 作成者ID を取得します. */
    public Long getCreated_id() { return created_id; }
    /** 作成者ID を設定します. */
    public void setCreated_id(Long created_id) { this.created_id = created_id; }
    /** 作成日時 を取得します. */
    public Timestamp getCreated() { return created; }
    /** 作成日時 を設定します. */
    public void setCreated(Timestamp created) { this.created = created; }

}
