package com.res_system.re_emp_manager.model.select_member;

import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.mvc.model.form.Param;

/**
 * グループ選択処理用 グループマスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="find_list", sql = ""
            + "SELECT"
            + " `USR`.`id` AS `id`"
            + ",(CASE WHEN `PSN`.`family_name` IS NOT NULL THEN"
            +   " CONCAT(IFNULL(`PSN`.`family_name`,''),' ',IFNULL(`PSN`.`first_name`,''))"
            +   " ELSE `USR`.`name` END) AS `name`"
            + ",`USR`.`email_addr` AS `email_addr`"
            + ",DATE_FORMAT(`GRPAC`.`registration_date`, '%Y/%m/%d') AS `registration_date`"
            + ",`GRPAC`.`memo` AS `memo`"
            + ",`GRPAC`.`gpac_status` AS `gpac_status`"
            + ",`GAcStat`.`name` AS `gpac_status_name`"
            + ",`EMP`.`situation` AS `situation`"
            + ",`Sitch`.`name` AS `situation_name`"
            + ",`EMP`.`employee_no` AS `employee_no`"
            + ",`DGRP`.`id` AS `group_id`"
            + ",`DGRP`.`name` AS `group_name`"
            + ",`AUT`.`id` AS `authority_id`"
            + ",`AUT`.`name` AS `authority_name`"
            // -- ログインアカウント有無項目 start. --//
            + ",(CASE WHEN `GRPAC`.`login_id` IS NOT NULL THEN '1'"
            +       " ELSE NULL END) AS `is_login_acc`"
            // -- ログインアカウント有無項目 end  .--//
            // -- オーナーフラグ項目 start. --//
            + ",(CASE WHEN `RGRP`.`owner_account_id` = `GRPAC`.`account_id` THEN '1'"
            +       " ELSE NULL END) AS `is_owner`"
            // -- オーナーフラグ項目 end  .--//
            // -- キーワード検索項目 start. --//
            + ",CONCAT(IFNULL(`USR`.`name`,'')"
            +    ",' ',IFNULL(`PSN`.`family_name`,'')"
            +    ",' ',IFNULL(`PSN`.`first_name`,'')"
            +    ",' ',IFNULL(`PSN`.`family_name_kana`,'')"
            +    ",' ',IFNULL(`PSN`.`first_name_kana`,'')"
            +    ",' ',IFNULL(`PSN`.`maiden_name`,'')"
            +    ",' ',IFNULL(`PSN`.`maiden_name_kana`,'')"
            +    ",' ',IFNULL(`GAcStat`.`name`,'')"
            +    ",' ',IFNULL(`GRPAC`.`memo`,'')"
            +    ",' ',IFNULL(`Sitch`.`name`,'')"
            +    ",' ',IFNULL(`EMP`.`employee_no`,'')"
            +    ",' ',IFNULL(`DGRP`.`name`,'')"
            +    ",' ',IFNULL(`AUT`.`name`,'')"
            +    ") AS `keyword`"
            // -- キーワード検索項目 end  .--//
            + " FROM `s_grp_account` `GRPAC`"
            +   " INNER JOIN `m_user` `USR` ON `USR`.`id` = `GRPAC`.`user_id`"
            +   " INNER JOIN `m_group` `RGRP`"
            +       " ON `RGRP`.`id` = `GRPAC`.`root_group_id`"
            +   " INNER JOIN `m_group` `DGRP`"
            +       " ON `DGRP`.`id` = `GRPAC`.`default_group_id`"
            +   " INNER JOIN `r_grp_member` `GRPMB`"
            +       " ON `GRPMB`.`group_id` = `DGRP`.`id` AND `GRPMB`.`user_id` = `USR`.`id`"
            +   " LEFT OUTER JOIN `m_authority` `AUT` ON `AUT`.`id` = `GRPMB`.`authority_id`"
            +   " LEFT OUTER JOIN `g_kind` `GAcStat`"
            +       " ON `GAcStat`.`kbn` = 'GAcStat' AND `GAcStat`.`seq` <> 0 AND `GAcStat`.`code` = `GRPAC`.`gpac_status`"
            +   " LEFT OUTER JOIN `m_employee` `EMP` ON `EMP`.`user_id` = `USR`.`id`"
            +   " LEFT OUTER JOIN `g_personal` `PSN` ON `PSN`.`id` = `EMP`.`personal_id`"
            +   " LEFT OUTER JOIN `g_kind` `Sitch`"
            +       " ON `Sitch`.`kbn` = 'Sitch' AND `Sitch`.`seq` <> 0 AND `Sitch`.`code` = `EMP`.`situation`"
            + " WHERE `GRPAC`.`root_group_id` = ?"
            +   " AND `GRPAC`.`gpac_status` IN('0', '1')"
            )
    ,@Sql(name="find_list_order", sql = "ORDER BY `employee_no`,`authority_id` ")
    ,@Sql(name="find_list_order_1", sql = "ORDER BY `name` ASC ")
    ,@Sql(name="find_list_order_2", sql = "ORDER BY `name` DESC ")
    ,@Sql(name="find_list_order_3", sql = "ORDER BY `employee_no` ASC ")
    ,@Sql(name="find_list_order_4", sql = "ORDER BY `employee_no` DESC ")
    ,@Sql(name="find_list_order_5", sql = "ORDER BY `situation` ASC ")
    ,@Sql(name="find_list_order_6", sql = "ORDER BY `situation` DESC ")
    ,@Sql(name="find_list_order_7", sql = "ORDER BY `group_name` ASC ")
    ,@Sql(name="find_list_order_8", sql = "ORDER BY `group_name` DESC ")
    ,@Sql(name="find_list_order_9", sql = "ORDER BY `authority_id` ASC ")
    ,@Sql(name="find_list_order_10", sql = "ORDER BY `authority_id` DESC ")
    ,@Sql(name="find_list_order_11", sql = "ORDER BY `gpac_status` ASC ")
    ,@Sql(name="find_list_order_12", sql = "ORDER BY `gpac_status` DESC ")
    ,@Sql(name="find_list_order_13", sql = "ORDER BY `registration_date` ASC ")
    ,@Sql(name="find_list_order_14", sql = "ORDER BY `registration_date` DESC ")
    ,@Sql(name="find_list_order_15", sql = "ORDER BY `memo` ASC ")
    ,@Sql(name="find_list_order_16", sql = "ORDER BY `memo` DESC ")
    ,@Sql(name="find_status", sql = "`gpac_status` IN ('0','1')")
})
public class SelectMemberMUser implements IEntity {

    //---------------------------------------------- const [public].
    /** 並び順 [最小]. */
    public static final int SORT_MIN = 1;
    /** 並び順 [最大]. */
    public static final int SORT_MAX = 16;



    //---------------------------------------------- properies [private].
    /** ユーザーID. */
    @Param
    private String id;
    /** ユーザー名. */
    @Param
    private String name;
    /** 連絡用メールアドレス. */
    @Param
    private String email_addr;
    /** 登録日. */
    @Param
    private String registration_date;
    /** 備考. */
    @Param
    private String memo;

    /** グループアカウント状態. */
    @Param
    private String gpac_status;
    /** グループアカウント状態名. */
    @Param
    private String gpac_status_name;
    /** 就業状況. */
    @Param
    private String situation;
    /** 就業状況名. */
    @Param
    private String situation_name;
    /** 社員番号. */
    @Param
    private String employee_no;

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

    /** オーナーフラグ. */
    @Param
    private String is_owner;
    /** ログインアカウント有無. */
    @Param
    private String is_login_acc;


    //-- setter / getter. --//
    /** ユーザーID を取得します. */
    public String getId() { return id; }
    /** ユーザーID を設定します. */
    public void setId(String id) { this.id = id; }
    /** ユーザー名 を取得します. */
    public String getName() { return name; }
    /** ユーザー名 を設定します. */
    public void setName(String name) { this.name = name; }
    /** 連絡用メールアドレス を取得します. */
    public String getEmail_addr() { return email_addr; }
    /** 連絡用メールアドレス を設定します. */
    public void setEmail_addr(String email_addr) { this.email_addr = email_addr; }
    /** 登録日 を取得します. */
    public String getRegistration_date() { return registration_date; }
    /** 登録日 を設定します. */
    public void setRegistration_date(String registration_date) { this.registration_date = registration_date; }
    /** 備考 を取得します. */
    public String getMemo() { return memo; }
    /** 備考 を設定します. */
    public void setMemo(String memo) { this.memo = memo; }

    /** グループアカウント状態 を取得します. */
    public String getGpac_status() { return gpac_status; }
    /** グループアカウント状態 を設定します. */
    public void setGpac_status(String gpac_status) { this.gpac_status = gpac_status; }
    /** グループアカウント状態名 を取得します. */
    public String getGpac_status_name() { return gpac_status_name; }
    /** グループアカウント状態名 を設定します. */
    public void setGpac_status_name(String gpac_status_name) { this.gpac_status_name = gpac_status_name; }
    /** 就業状況 を取得します. */
    public String getSituation() { return situation; }
    /** 就業状況 を設定します. */
    public void setSituation(String situation) { this.situation = situation; }
    /** 就業状況名 を取得します. */
    public String getSituation_name() { return situation_name; }
    /** 就業状況名 を設定します. */
    public void setSituation_name(String situation_name) { this.situation_name = situation_name; }
    /** 社員番号 を取得します. */
    public String getEmployee_no() { return employee_no; }
    /** 社員番号 を取得します. */
    public void setEmployee_no(String employee_no) { this.employee_no = employee_no; }

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

    /** オーナーフラグ を取得します. */
    public String getIs_owner() { return is_owner; }
    /** オーナーフラグ を設定します. */
    public void setIs_owner(String is_owner) { this.is_owner = is_owner; }
    /** ログインアカウント有無 を取得します. */
    public String getIs_login_acc() { return is_login_acc; }
    /** ログインアカウント有無 を設定します. */
    public void setIs_login_acc(String is_login_acc) { this.is_login_acc = is_login_acc; }

}
