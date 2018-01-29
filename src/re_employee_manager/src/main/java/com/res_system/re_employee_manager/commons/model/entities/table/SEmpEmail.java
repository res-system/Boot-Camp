package com.res_system.re_employee_manager.commons.model.entities.table;

import java.sql.Timestamp;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;
import com.res_system.re_employee_manager.commons.model.entities.IEmpSubEntity;

/**
 * 社員メールアドレスサブマスタ Entityクラス.
 * @author res.
 *  generated at 2018/01/04.
 */
@Table(name="s_emp_email", as="EMEL")
public class SEmpEmail implements IEmpSubEntity {

    /** 社員ID. */
    @Key
    @Column
    private Long employee_id;
    /** 連番. */
    @Key
    @Column
    private Integer seq;
    /** メールアドレスID. */
    @Column
    private Long email_id;
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
    /** 社員ID を取得します. */
    public Long getEmployee_id() { return employee_id; }
    /** 社員ID を設定します. */
    public void setEmployee_id(Long employee_id) { this.employee_id = employee_id; }
    /** 連番 を取得します. */
    public Integer getSeq() { return seq; }
    /** 連番 を設定します. */
    public void setSeq(Integer seq) { this.seq = seq; }
    /** メールアドレスID を取得します. */
    public Long getEmail_id() { return email_id; }
    /** メールアドレスID を設定します. */
    public void setEmail_id(Long email_id) { this.email_id = email_id; }
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