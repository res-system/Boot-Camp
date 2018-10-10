package com.res_system.re_emp_manager.model.mnt_group_acc;

import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.commons.mvc.model.form.Param;

/**
 * グループアカウント情報メンテナンス処理用 データクラス.
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
            + ",`GRPAC`.`login_id` AS `login_id`"
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
    ,@Sql(name="find_list_order_1", sql = "ORDER BY `employee_no` ASC ")
    ,@Sql(name="find_list_order_2", sql = "ORDER BY `employee_no` DESC ")
    ,@Sql(name="find_list_order_3", sql = "ORDER BY `name` ASC ")
    ,@Sql(name="find_list_order_4", sql = "ORDER BY `name` DESC ")
    ,@Sql(name="find_list_order_5", sql = "ORDER BY `group_id` ASC ")
    ,@Sql(name="find_list_order_6", sql = "ORDER BY `group_id` DESC ")
    ,@Sql(name="find_list_order_7", sql = "ORDER BY `gpac_status` ASC ")
    ,@Sql(name="find_list_order_8", sql = "ORDER BY `gpac_status` DESC ")
    ,@Sql(name="find_list_order_9", sql = "ORDER BY `situation` ASC ")
    ,@Sql(name="find_list_order_10", sql = "ORDER BY `situation` DESC ")
    ,@Sql(name="find_list_order_11", sql = "ORDER BY `authority_id` ASC ")
    ,@Sql(name="find_list_order_12", sql = "ORDER BY `authority_id` DESC ")
    ,@Sql(name="find_list_order_13", sql = "ORDER BY `registration_date` ASC ")
    ,@Sql(name="find_list_order_14", sql = "ORDER BY `registration_date` DESC ")
    ,@Sql(name="find_list_order_15", sql = "ORDER BY `memo` ASC ")
    ,@Sql(name="find_list_order_16", sql = "ORDER BY `memo` DESC ")
    ,@Sql(name="find_status", sql = "`gpac_status` IN ('0','1')")
    ,@Sql(name="check_count", sql = ""
            + "SELECT COUNT(`USR`.`id`) AS `count`"
            + " FROM `s_grp_account` `GRPAC`"
            +   " INNER JOIN `m_user` `USR` ON `USR`.`id` = `GRPAC`.`user_id`"
            + " WHERE `GRPAC`.`root_group_id` = ?"
            )
    ,@Sql(name=SqlMaker.SQL_NAME_SELECT_BY_KEY, sql = ""
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
            + ",`GRPAC`.`login_id` AS `login_id`"
            // -- ログインアカウント有無項目 start. --//
            + ",(CASE WHEN `GRPAC`.`login_id` IS NOT NULL THEN '1'"
            +       " ELSE NULL END) AS `is_login_acc`"
            // -- ログインアカウント有無項目 end  .--//
            // -- オーナーフラグ項目 start. --//
            + ",(CASE WHEN `RGRP`.`owner_account_id` = `GRPAC`.`account_id` THEN '1'"
            +       " ELSE NULL END) AS `is_owner`"
            // -- オーナーフラグ項目 end  .--//
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
            +   " AND `GRPAC`.`user_id` = ?"
            )
    ,@Sql(name="check_owner", sql = ""
            + "SELECT COUNT(`ACC`.`id`) AS `count`"
            + " FROM `s_grp_account` `GRPAC`"
            +   " INNER JOIN `m_group` `GRP`"
            +       " ON `GRP`.`id` = `GRPAC`.`root_group_id` AND `GRP`.`owner_account_id` = `GRPAC`.`account_id`"
            +   " INNER JOIN `m_account` `ACC`"
            +       " ON `ACC`.`id` = `GRP`.`owner_account_id`"
            + " WHERE `GRPAC`.`root_group_id` = ?"
            +   " AND `GRPAC`.`user_id` = ?"
            )
    ,@Sql(name="delete_user_key", sql = ""
            + "DELETE FROM `s_user_key` WHERE `user_id` = ?"
            )
    ,@Sql(name="delete_login", sql = ""
            + "DELETE FROM `t_login` WHERE `user_id` = ?"
            )
    ,@Sql(name="delete_employee", sql = ""
            + "DELETE FROM `m_employee` WHERE `user_id` = ?"
            )
    ,@Sql(name="delete_emp_info", sql = ""
            + "DELETE FROM `s_emp_info` WHERE `user_id` = ?"
            )
    ,@Sql(name="delete_emp_addr", sql = ""
            + "DELETE FROM `s_emp_addr` WHERE `user_id` = ?"
            )
    ,@Sql(name="delete_emp_tel", sql = ""
            + "DELETE FROM `s_emp_tel` WHERE `user_id` = ?"
            )
    ,@Sql(name="delete_emp_email", sql = ""
            + "DELETE FROM `s_emp_email` WHERE `user_id` = ?"
            )
    ,@Sql(name="delete_emp_family", sql = ""
            + "DELETE FROM `s_emp_family` WHERE `user_id` = ?"
            )
    ,@Sql(name="delete_grp_account", sql = ""
            + "DELETE FROM `s_grp_account` WHERE `user_id` = ?"
            )
    ,@Sql(name="delete_grp_member", sql = ""
            + "DELETE FROM `r_grp_member` WHERE `user_id` = ?"
            )
    ,@Sql(name="delete_user", sql = ""
            + "DELETE FROM `m_user` WHERE `id` = ? AND `user_kbn` = '3'"
            )
    ,@Sql(name="check_employee_no", sql = ""
            + "SELECT COUNT(*) AS `count`"
            + " FROM `m_employee` `EMP`"
            +   " INNER JOIN `s_grp_account` `GRPAC` ON `GRPAC`.`user_id` = `EMP`.`user_id`"
            + " WHERE `GRPAC`.`root_group_id` = ? AND `EMP`.`employee_no` = ? AND `EMP`.`user_id` <> ?"
            )
})
public class MntGroupAccData implements IEntity {

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
    /** 権限ID. */
    @Param
    private String authority_id;
    /** 権限名. */
    @Param
    private String authority_name;
    /** 連絡用メールアドレス. */
    @Param
    private String email_addr;
    /** グループアカウント状態. */
    @Param
    private String gpac_status;
    /** グループアカウント状態名. */
    @Param
    private String gpac_status_name;
    /** 登録日. */
    @Param
    private String registration_date;
    /** 備考. */
    @Param
    private String memo;
    /** ユーザー区分. */
    @Param
    private String user_kbn;
    /** ユーザー区分名. */
    @Param
    private String user_kbn_name;
    /** 就業状況. */
    @Param
    private String situation;
    /** 就業状況名. */
    @Param
    private String situation_name;
    /** 社員番号. */
    @Param
    private String employee_no;
    /** 氏名（姓）. */
    @Param
    private String family_name;
    /** 氏名（名）. */
    @Param
    private String first_name;
    /** 所有グループID. */
    @Param
    private String possess_group_id;
    /** グループID. */
    @Param
    private String group_id;
    /** グループ名. */
    @Param
    private String group_name;

    /** ログインアカウント有無. */
    @Param
    private String is_login_acc;

    /** 招待フラグ. */
    @Param
    private String is_invite;
    /** オーナーフラグ. */
    @Param
    private String is_owner;

    /** ログインID. */
    @Param
    private String login_id;
    /** パスワード. */
    @Param
    private String password;


    //-- setter / getter. --//
    /** ユーザーID を取得します. */
    public String getId() { return id; }
    /** ユーザーID を設定します. */
    public void setId(String id) { this.id = id; }
    /** ユーザー名 を取得します. */
    public String getName() { return name; }
    /** ユーザー名 を設定します. */
    public void setName(String name) { this.name = name; }
    /** 権限ID を取得します. */
    public String getAuthority_id() { return authority_id; }
    /** 権限ID を設定します. */
    public void setAuthority_id(String authority_id) { this.authority_id = authority_id; }
    /** 権限名 を取得します. */
    public String getAuthority_name() { return authority_name; }
    /** 権限名 を設定します. */
    public void setAuthority_name(String authority_name) { this.authority_name = authority_name; }
    /** 連絡用メールアドレス を取得します. */
    public String getEmail_addr() { return email_addr; }
    /** 連絡用メールアドレス を設定します. */
    public void setEmail_addr(String email_addr) { this.email_addr = email_addr; }
    /** グループアカウント状態 を取得します. */
    public String getGpac_status() { return gpac_status; }
    /** グループアカウント状態 を設定します. */
    public void setGpac_status(String gpac_status) { this.gpac_status = gpac_status; }
    /** グループアカウント状態名 を取得します. */
    public String getGpac_status_name() { return gpac_status_name; }
    /** グループアカウント状態名 を設定します. */
    public void setGpac_status_name(String gpac_status_name) { this.gpac_status_name = gpac_status_name; }
    /** 登録日 を取得します. */
    public String getRegistration_date() { return registration_date; }
    /** 登録日 を設定します. */
    public void setRegistration_date(String registration_date) { this.registration_date = registration_date; }
    /** 備考 を取得します. */
    public String getMemo() { return memo; }
    /** 備考 を設定します. */
    public void setMemo(String memo) { this.memo = memo; }
    /** ユーザー区分 を取得します. */
    public String getUser_kbn() { return user_kbn; }
    /** ユーザー区分 を設定します. */
    public void setUser_kbn(String user_kbn) { this.user_kbn = user_kbn; }
    /** ユーザー区分名 を取得します. */
    public String getUser_kbn_name() { return user_kbn_name; }
    /** ユーザー区分名 を設定します. */
    public void setUser_kbn_name(String user_kbn_name) { this.user_kbn_name = user_kbn_name; }
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
    /** 氏名（姓） を取得します. */
    public String getFamily_name() { return family_name; }
    /** 氏名（姓） を設定します. */
    public void setFamily_name(String family_name) { this.family_name = family_name; }
    /** 氏名（名） を取得します. */
    public String getFirst_name() { return first_name; }
    /** 氏名（名） を設定します. */
    public void setFirst_name(String first_name) { this.first_name = first_name; }
    /** 所有グループID を取得します. */
    public String getPossess_group_id() { return possess_group_id; }
    /** 所有グループID を設定します. */
    public void setPossess_group_id(String possess_group_id) { this.possess_group_id = possess_group_id; }
    /** グループID を取得します. */
    public String getGroup_id() { return group_id; }
    /** グループID を設定します. */
    public void setGroup_id(String group_id) { this.group_id = group_id; }
    /** グループ名 を取得します. */
    public String getGroup_name() { return group_name; }
    /** グループ名 を設定します. */
    public void setGroup_name(String group_name) { this.group_name = group_name; }

    /** ログインアカウント有無 を取得します. */
    public String getIs_login_acc() { return is_login_acc; }
    /** ログインアカウント有無 を設定します. */
    public void setIs_login_acc(String is_login_acc) { this.is_login_acc = is_login_acc; }

    /** 招待フラグ を取得します. */
    public String getIs_invite() { return is_invite; }
    /** 招待フラグ を設定します. */
    public void setIs_invite(String is_invite) { this.is_invite = is_invite; }
    /** オーナーフラグ を取得します. */
    public String getIs_owner() { return is_owner; }
    /** オーナーフラグ を設定します. */
    public void setIs_owner(String is_owner) { this.is_owner = is_owner; }

    /** ログインID を取得します. */
    public String getLogin_id() { return login_id; }
    /** ログインID を設定します. */
    public void setLogin_id(String login_id) { this.login_id = login_id; }
    /** パスワード を取得します. */
    public String getPassword() { return password; }
    /** パスワード を設定します. */
    public void setPassword(String password) { this.password = password; }

}
