package com.res_system.re_emp_manager.commons.model.entities;

import com.res_system.commons.mvc.model.form.Param;


import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;

/**
 * 社員マスタ Entityクラス.
 * @author res.
 *  generated at 2018/08/08. 
 */
@Table(name="m_employee", as="EMP")
public class MEmployee implements IEntity {

    //---------------------------------------------- properies [private].
    /** ユーザーID. */
    @Param
    @Key
    @Column
    private String user_id;
    /** 個人ID. */
    @Param
    @Column
    private String personal_id;
    /** 就業状況. */
    @Param
    @Column
    private String situation;
    /** 社員番号. */
    @Param
    @Column
    private String employee_no;
    /** 入社日. */
    @Param
    @Column
    private String hire_date;
    /** 退職日. */
    @Param
    @Column
    private String retirement_date;
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
    /** ユーザーID を取得します. */
    public String getUser_id() { return user_id; }
    /** ユーザーID を設定します. */
    public void setUser_id(String user_id) { this.user_id = user_id; }
    /** 個人ID を取得します. */
    public String getPersonal_id() { return personal_id; }
    /** 個人ID を設定します. */
    public void setPersonal_id(String personal_id) { this.personal_id = personal_id; }
    /** 就業状況 を取得します. */
    public String getSituation() { return situation; }
    /** 就業状況 を設定します. */
    public void setSituation(String situation) { this.situation = situation; }
    /** 社員番号 を取得します. */
    public String getEmployee_no() { return employee_no; }
    /** 社員番号 を設定します. */
    public void setEmployee_no(String employee_no) { this.employee_no = employee_no; }
    /** 入社日 を取得します. */
    public String getHire_date() { return hire_date; }
    /** 入社日 を設定します. */
    public void setHire_date(String hire_date) { this.hire_date = hire_date; }
    /** 退職日 を取得します. */
    public String getRetirement_date() { return retirement_date; }
    /** 退職日 を設定します. */
    public void setRetirement_date(String retirement_date) { this.retirement_date = retirement_date; }
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
