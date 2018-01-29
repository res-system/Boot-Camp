package com.res_system.re_employee_manager.commons.model.entities.table;

import java.sql.Timestamp;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;
import com.res_system.re_employee_manager.commons.model.entities.IAcctSubEntity;

/**
 * アカウントキーサブマスタ Entityクラス.
 * @author res.
 *  generated at 2018/01/04.
 */
@Table(name="c_s_acct_key", as="ACKY")
public class CSAcctKey implements IAcctSubEntity {

    /** アカウントID. */
    @Key
    @Column
    private Long account_id;
    /** 連番. */
    @Key
    @Column
    private Integer seq;
    /** キー. */
    @Column
    private String key;
    /** 有効期限. */
    @Column
    private Timestamp expiration_time;
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
    /** キー を取得します. */
    public String getKey() { return key; }
    /** キー を設定します. */
    public void setKey(String key) { this.key = key; }
    /** 有効期限 を取得します. */
    public Timestamp getExpiration_time() { return expiration_time; }
    /** 有効期限 を設定します. */
    public void setExpiration_time(Timestamp expiration_time) { this.expiration_time = expiration_time; }
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
