package com.res_system.re_employee_manager.commons.model.entities.table;

import java.sql.Date;
import java.sql.Timestamp;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;
import com.res_system.re_employee_manager.commons.model.entities.IIdMsEntity;

/**
 * 社員マスタ Entityクラス.
 * @author res.
 *  generated at 2018/01/04.
 */
@Table(name="m_employee", as="EMP")
public class MEmployee implements IIdMsEntity {

    /** 社員ID. */
    @Key
    @Column
    private Long id;
    /** 社員番号. */
    @Column
    private String employee_no;
    /** 個人ID. */
    @Column
    private Long personal_id;
    /** 状態. */
    @Column
    private String emp_status;
    /** 役職. */
    @Column
    private String position;
    /** 入社日. */
    @Column
    private Date joining_date;
    /** 退社日. */
    @Column
    private Date leaving_date;
    /** 雇用保険資格取得日. */
    @Column
    private Date acquisition_date;
    /** 雇用保険被保険番号. */
    @Column
    private String insurance_no;
    /** 基礎年金番号. */
    @Column
    private String pension_no;
    /** デフォルトグループID. */
    @Column
    private Long default_group_id;
    /** アカウントID. */
    @Column
    private Long account_id;
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
    public Long getId() { return id; }
    /** 社員ID を設定します. */
    public void setId(Long id) { this.id = id; }
    /** 社員番号 を取得します. */
    public String getEmployee_no() { return employee_no; }
    /** 社員番号 を設定します. */
    public void setEmployee_no(String employee_no) { this.employee_no = employee_no; }
    /** 個人ID を取得します. */
    public Long getPersonal_id() { return personal_id; }
    /** 個人ID を設定します. */
    public void setPersonal_id(Long personal_id) { this.personal_id = personal_id; }
    /** 状態 を取得します. */
    public String getEmp_status() { return emp_status; }
    /** 状態 を設定します. */
    public void setEmp_status(String emp_status) { this.emp_status = emp_status; }
    /** 役職 を取得します. */
    public String getPosition() { return position; }
    /** 役職 を設定します. */
    public void setPosition(String position) { this.position = position; }
    /** 入社日 を取得します. */
    public Date getJoining_date() { return joining_date; }
    /** 入社日 を設定します. */
    public void setJoining_date(Date joining_date) { this.joining_date = joining_date; }
    /** 退社日 を取得します. */
    public Date getLeaving_date() { return leaving_date; }
    /** 退社日 を設定します. */
    public void setLeaving_date(Date leaving_date) { this.leaving_date = leaving_date; }
    /** 雇用保険資格取得日 を取得します. */
    public Date getAcquisition_date() { return acquisition_date; }
    /** 雇用保険資格取得日 を設定します. */
    public void setAcquisition_date(Date acquisition_date) { this.acquisition_date = acquisition_date; }
    /** 雇用保険被保険番号 を取得します. */
    public String getInsurance_no() { return insurance_no; }
    /** 雇用保険被保険番号 を設定します. */
    public void setInsurance_no(String insurance_no) { this.insurance_no = insurance_no; }
    /** 基礎年金番号 を取得します. */
    public String getPension_no() { return pension_no; }
    /** 基礎年金番号 を設定します. */
    public void setPension_no(String pension_no) { this.pension_no = pension_no; }
    /** デフォルトグループID を取得します. */
    public Long getDefault_group_id() { return default_group_id; }
    /** デフォルトグループID を設定します. */
    public void setDefault_group_id(Long default_group_id) { this.default_group_id = default_group_id; }
    /** アカウントID を取得します. */
    public Long getAccount_id() { return account_id; }
    /** アカウントID を設定します. */
    public void setAccount_id(Long account_id) { this.account_id = account_id; }
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
