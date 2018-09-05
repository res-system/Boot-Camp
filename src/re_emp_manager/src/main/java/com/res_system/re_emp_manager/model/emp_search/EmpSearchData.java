package com.res_system.re_emp_manager.model.emp_search;

import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.mvc.model.form.Param;

/**
 * 社員情報検索処理用 データクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="find_list", sql = ""
            + "SELECT"
            + " `EMP`.`user_id` AS `id`"
            + ",`EMP`.`employee_no` AS `employee_no`"
            + ",CONCAT(IFNULL(`PSN`.`family_name`,'')"
            + ",' ',IFNULL(`PSN`.`first_name`,'')) AS `name`"
            + ",`EMP`.`situation` AS `situation`"
            + ",`Sitch`.`name` AS `situation_name`"
            + ",DATE_FORMAT(`EMP`.`hire_date`, '%Y/%m/%d') AS `hire_date`"
            + ",DATE_FORMAT(`EMP`.`retirement_date`, '%Y/%m/%d') AS `retirement_date`"
            + ",`EMP`.`memo` AS `memo`"
            + ",`GRP`.`id` AS `group_id`"
            + ",`GRP`.`name` AS `group_name`"
            + ",`AUT`.`id` AS `authority_id`"
            + ",`AUT`.`name` AS `authority_name`"
            // -- キーワード検索項目 start. --//
            + ",CONCAT(IFNULL(`EMP`.`employee_no`,'')"
            +    ",' ',IFNULL(`PSN`.`family_name`,'')"
            +    ",' ',IFNULL(`PSN`.`first_name`,'')"
            +    ",' ',IFNULL(`PSN`.`family_name_kana`,'')"
            +    ",' ',IFNULL(`PSN`.`first_name_kana`,'')"
            +    ",' ',IFNULL(`PSN`.`maiden_name`,'')"
            +    ",' ',IFNULL(`PSN`.`maiden_name_kana`,'')"
            +    ",' ',IFNULL(`Sitch`.`name`,'')"
            +    ",' ',IFNULL(`EMP`.`hire_date`,'')"
            +    ",' ',IFNULL(`EMP`.`retirement_date`,'')"
            +    ",' ',IFNULL(`EMP`.`memo`,'')"
            +    ",' ',IFNULL(`GRP`.`name`,'')"
            +    ",' ',IFNULL(`AUT`.`name`,'')"
            +    ") AS `keyword`"
            // -- キーワード検索項目 end  .--//
            + " FROM `m_group` `GRP`"
            +   " INNER JOIN `r_grp_member` `GRPMB` ON `GRPMB`.`group_id` = `GRP`.`id`"
            +   " INNER JOIN `m_employee` `EMP` ON `EMP`.`user_id` = `GRPMB`.`user_id`"
            +   " INNER JOIN `g_personal` `PSN` ON `PSN`.`id` = `EMP`.`personal_id`"
            +   " LEFT OUTER JOIN `g_kind` `Sitch`"
            +       " ON `Sitch`.`kbn` = 'Sitch' AND `Sitch`.`seq` <> 0 AND `Sitch`.`code` = `EMP`.`situation`"
            +   " LEFT OUTER JOIN `m_authority` `AUT` ON `AUT`.`id` = `GRPMB`.`authority_id`"
            + " WHERE (`GRP`.`id` = ? OR `GRP`.`root_group_id` = ?)"
            )
    ,@Sql(name="find_list_order", sql = "ORDER BY `employee_no`,`hire_date` ")
    ,@Sql(name="find_list_order_1", sql = "ORDER BY `employee_no` ASC ")
    ,@Sql(name="find_list_order_2", sql = "ORDER BY `employee_no` DESC ")
    ,@Sql(name="find_list_order_3", sql = "ORDER BY `name` ASC ")
    ,@Sql(name="find_list_order_4", sql = "ORDER BY `name` DESC ")
    ,@Sql(name="find_list_order_5", sql = "ORDER BY `situation` ASC ")
    ,@Sql(name="find_list_order_6", sql = "ORDER BY `situation` DESC ")
    ,@Sql(name="find_list_order_7", sql = "ORDER BY `hire_date` ASC ")
    ,@Sql(name="find_list_order_8", sql = "ORDER BY `hire_date` DESC ")
    ,@Sql(name="find_list_order_9", sql = "ORDER BY `retirement_date` ASC ")
    ,@Sql(name="find_list_order_10", sql = "ORDER BY `retirement_date` DESC ")
    ,@Sql(name="find_list_order_11", sql = "ORDER BY `group_id` ASC ")
    ,@Sql(name="find_list_order_12", sql = "ORDER BY `group_id` DESC ")
    ,@Sql(name="find_list_order_13", sql = "ORDER BY `memo` ASC ")
    ,@Sql(name="find_list_order_14", sql = "ORDER BY `memo` DESC ")
    ,@Sql(name="find_list_order_15", sql = "ORDER BY `authority_id` ASC ")
    ,@Sql(name="find_list_order_16", sql = "ORDER BY `authority_id` DESC ")
    ,@Sql(name="find_status", sql = "`situation` IN ('0','1')")
})
public class EmpSearchData implements IEntity {

    //---------------------------------------------- const [public].
    /** 並び順 [最小]. */
    public static final int SORT_MIN = 1;
    /** 並び順 [最大]. */
    public static final int SORT_MAX = 16;



    //---------------------------------------------- properies [private].
    /** ユーザーID. */
    @Param
    private String id;
    /** 社員番号. */
    @Param
    private String employee_no;
    /** 社員名. */
    @Param
    private String name;
    /** 就業状況. */
    @Param
    private String situation;
    /** 就業状況名. */
    @Param
    private String situation_name;
    /** 入社日. */
    @Param
    private String hire_date;
    /** 退職日. */
    @Param
    private String retirement_date;
    /** 備考. */
    @Param
    private String memo;
    /** グループID. */
    @Param
    private String group_id;
    /** グループ名. */
    @Param
    private String group_name;
    /** 権限ID. */
    @Param
    private String authority_id;
    /** 権限名. */
    @Param
    private String authority_name;

    //-- setter / getter. --//
    /** ユーザーID を取得します. */
    public String getId() { return id; }
    /** ユーザーID を設定します. */
    public void setId(String id) { this.id = id; }
    /** 社員番号 を取得します. */
    public String getEmployee_no() { return employee_no; }
    /** 社員番号 を設定します. */
    public void setEmployee_no(String employee_no) { this.employee_no = employee_no; }
    /** 社員名 を取得します. */
    public String getName() { return name; }
    /** 社員名 を設定します. */
    public void setName(String name) { this.name = name; }
    /** 就業状況 を取得します. */
    public String getSituation() { return situation; }
    /** 就業状況 を設定します. */
    public void setSituation(String situation) { this.situation = situation; }
    /** 就業状況名 を取得します. */
    public String getSituation_name() { return situation_name; }
    /** 就業状況名 を設定します. */
    public void setSituation_name(String situation_name) { this.situation_name = situation_name; }
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
    /** グループID を取得します. */
    public String getGroup_id() { return group_id; }
    /** グループID を設定します. */
    public void setGroup_id(String group_id) { this.group_id = group_id; }
    /** グループ名 を取得します. */
    public String getGroup_name() { return group_name; }
    /** グループ名 を設定します. */
    public void setGroup_name(String group_name) { this.group_name = group_name; }
    /** 権限ID を取得します. */
    public String getAuthority_id() { return authority_id; }
    /** 権限ID を設定します. */
    public void setAuthority_id(String authority_id) { this.authority_id = authority_id; }
    /** 権限名 を取得します. */
    public String getAuthority_name() { return authority_name; }
    /** 権限名 を設定します. */
    public void setAuthority_name(String authority_name) { this.authority_name = authority_name; }

}
