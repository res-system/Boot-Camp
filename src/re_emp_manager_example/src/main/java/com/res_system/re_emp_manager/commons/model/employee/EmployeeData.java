package com.res_system.re_emp_manager.commons.model.employee;

import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.commons.mvc.model.form.Param;

/**
 * 社員データクラス.
 * @author res.
 */
@Sqls({
    @Sql(name=SqlMaker.SQL_NAME_SELECT_BY_KEY, sql = ""
            + "SELECT"
            + " `EMP`.`user_id` AS `id`"
            + ",`EMP`.`employee_no` AS `employee_no`"
            + ",`Sitch`.`name` AS `situation_name`"
            + ",`EMP`.`personal_id` AS `personal_id`"
            + ",CONCAT(IFNULL(`PSN`.`family_name`,'')"
            +    ",' ',IFNULL(`PSN`.`first_name`,'')) AS `name`"
            + ",`Sex`.`name` AS `sex_name`"
            + ",DATE_FORMAT(`PSN`.`birthday`, '%Y/%m/%d') AS `birthday`"
            + ",`PSN`.`mynumber` AS `mynumber`"
            + ",DATE_FORMAT(`EMP`.`hire_date`, '%Y/%m/%d') AS `hire_date`"
            + ",DATE_FORMAT(`EMP`.`retirement_date`, '%Y/%m/%d') AS `retirement_date`"
            + ",`EMP`.`memo` AS `memo`"
            + " FROM `m_employee` `EMP`"
            +   " INNER JOIN `g_personal` `PSN` ON `PSN`.`id` = `EMP`.`personal_id`"
            +   " LEFT OUTER JOIN `g_kind` `Sitch`"
            +       " ON `Sitch`.`kbn` = 'Sitch' AND `Sitch`.`seq` <> 0 AND `Sitch`.`code` = `EMP`.`situation`"
            +   " LEFT OUTER JOIN `g_kind` `Sex`"
            +       " ON `Sex`.`kbn` = 'Sex' AND `Sex`.`seq` <> 0 AND `Sex`.`code` = `PSN`.`sex`"
            + " WHERE `EMP`.`user_id` = ?"
            )
})
public class EmployeeData implements IEntity {

    //---------------------------------------------- properies [private].
    /** ユーザーID. */
    @Param
    private String id;
    /** 社員番号. */
    @Param
    private String employee_no;
    /** 就業状況名. */
    @Param
    private String situation_name;
    /** 個人ID. */
    @Param
    private String personal_id;
    /** 社員名. */
    @Param
    private String name;
    /** 性別名. */
    @Param
    private String sex_name;
    /** 生年月日. */
    @Param
    private String birthday;
    /** マイナンバー. */
    @Param
    private String mynumber;
    /** 入社日. */
    @Param
    private String hire_date;
    /** 退職日. */
    @Param
    private String retirement_date;
    /** 備考. */
    @Param
    private String memo;

    //-- setter / getter. --//
    /** ユーザーID を取得します. */
    public String getId() { return id; }
    /** ユーザーID を設定します. */
    public void setId(String id) { this.id = id; }
    /** 社員番号 を取得します. */
    public String getEmployee_no() { return employee_no; }
    /** 社員番号 を設定します. */
    public void setEmployee_no(String employee_no) { this.employee_no = employee_no; }
    /** 就業状況名 を取得します. */
    public String getSituation_name() { return situation_name; }
    /** 就業状況名 を設定します. */
    public void setSituation_name(String situation_name) { this.situation_name = situation_name; }
    /** 個人ID を取得します. */
    public String getPersonal_id() { return personal_id; }
    /** 個人ID を設定します. */
    public void setPersonal_id(String personal_id) { this.personal_id = personal_id; }
    /** 社員名 を取得します. */
    public String getName() { return name; }
    /** 社員名 を設定します. */
    public void setName(String name) { this.name = name; }
    /** 性別名 を取得します. */
    public String getSex_name() { return sex_name; }
    /** 性別名 を設定します. */
    public void setSex_name(String sex_name) { this.sex_name = sex_name; }
    /** 生年月日 を取得します. */
    public String getBirthday() { return birthday; }
    /** 生年月日 を設定します. */
    public void setBirthday(String birthday) { this.birthday = birthday; }
    /** マイナンバー を取得します. */
    public String getMynumber() { return mynumber; }
    /** マイナンバー を設定します. */
    public void setMynumber(String mynumber) { this.mynumber = mynumber; }
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

}
