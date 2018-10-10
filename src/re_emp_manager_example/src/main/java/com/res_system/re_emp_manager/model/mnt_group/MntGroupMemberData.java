package com.res_system.re_emp_manager.model.mnt_group;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.commons.mvc.model.form.Param;
import com.res_system.re_emp_manager.commons.model.entities.RGrpMember;

/**
 * グループ情報メンテナンス処理用 グループメンバー関連マスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="find_list", sql = ""
            + "SELECT"
            + " `GRP`.`id` AS `group_id`"
            + ",`USR`.`id` AS `id`"
            + ",`USR`.`name` AS `name`"
            + ",`AUT`.`id` AS `authority_id`"
            + ",`AUT`.`name` AS `authority_name`"
            + ",`GRPAC`.`gpac_status` AS `gpac_status`"
            + ",`GAcStat`.`name` AS `gpac_status_name`"
            + ",DATE_FORMAT(`GRPMB`.`updated`, '%Y/%m/%d %H:%i:%s') AS `updated`"
            + ",(CASE WHEN `GRPAC`.`default_group_id` = `GRP`.`id` THEN '1'"
            +       " ELSE '0' END) AS `is_default`"
            + ",'1' AS `is_existing`"
            + ",(CASE WHEN `RGRP`.`owner_account_id` = `GRPAC`.`account_id` THEN '1'"
            +       " ELSE NULL END) AS `is_owner`"
            + ",`EMP`.`employee_no` AS `employee_no`"
            + " FROM `r_grp_member` `GRPMB`"
            +   " INNER JOIN `m_user` `USR` ON `USR`.`id` = `GRPMB`.`user_id`"
            +   " INNER JOIN `m_authority` `AUT` ON `AUT`.`id` = `GRPMB`.`authority_id`"
            +   " INNER JOIN `m_group` `GRP` ON `GRP`.`id` = `GRPMB`.`group_id`"
            +   " INNER JOIN `s_grp_account` `GRPAC`"
            +       " ON `GRPAC`.`root_group_id` = `GRP`.`root_group_id` AND `GRPAC`.`user_id` = `USR`.`id`"
            +   " LEFT OUTER JOIN `g_kind` `GAcStat`"
            +       " ON `GAcStat`.`kbn` = 'GAcStat' AND `GAcStat`.`seq` <> 0 AND `GAcStat`.`code` = `GRPAC`.`gpac_status`"
            +   " INNER JOIN `m_group` `RGRP` ON `RGRP`.`id` = `GRPAC`.`root_group_id`"
            +   " LEFT OUTER JOIN `m_employee` `EMP` ON `EMP`.`user_id` = `USR`.`id`"
            + " WHERE `GRPMB`.`group_id` = ?"
            + " ORDER BY `authority_id` ASC, `updated` ASC"
            )
    ,@Sql(name="check_count", sql = ""
            + "SELECT COUNT(*) AS `count` --FROM"
            + " WHERE `GRPMB`.`group_id` = ?"
            )
    ,@Sql(name="check_duplicate_mem", sql = ""
            + "SELECT COUNT(*) AS `count` --FROM"
            + " WHERE `GRPMB`.`group_id` = ? AND `GRPMB`.`user_id` = ?"
            )
    ,@Sql(name=SqlMaker.SQL_NAME_UPDATE, sql = ""
            + "--UPDATE"
            + " `authority_id` = :authority_id"
            + ",`updated_id` = :updated_id"
            + ",`updated` = CURRENT_TIMESTAMP"
            + " --WHERE"
            )
})
public class MntGroupMemberData extends RGrpMember {

    //---------------------------------------------- properies [private].
    /** ユーザーID. */
    @Param
    private String id;
    /** ユーザー名. */
    @Param
    private String name;
    /** 権限名. */
    @Param
    private String authority_name;
    /** グループアカウント状態. */
    @Param
    private String gpac_status;
    /** グループアカウント状態名. */
    @Param
    private String gpac_status_name;
    /** 社員番号. */
    @Param
    private String employee_no;

    /** デフォルトフラグ. */
    @Param
    private String is_default;
    /** 既存フラグ. */
    @Param
    private String is_existing;
    /** 削除フラグ. */
    @Param
    private String is_delete;
    /** オーナーフラグ. */
    @Param
    private String is_owner;


    //-- setter / getter. --//
    /** ユーザーID を取得します. */
    public String getId() { return id; }
    /** ユーザーID を設定します. */
    public void setId(String id) { this.id = id; }
    /** ユーザー名 を取得します. */
    public String getName() { return name; }
    /** ユーザー名 を設定します. */
    public void setName(String name) { this.name = name; }
    /** 権限名 を取得します. */
    public String getAuthority_name() { return authority_name; }
    /** 権限名 を設定します. */
    public void setAuthority_name(String authority_name) { this.authority_name = authority_name; }
    /** グループアカウント状態 を取得します. */
    public String getGpac_status() { return gpac_status; }
    /** グループアカウント状態 を設定します. */
    public void setGpac_status(String gpac_status) { this.gpac_status = gpac_status; }
    /** グループアカウント状態名 を取得します. */
    public String getGpac_status_name() { return gpac_status_name; }
    /** グループアカウント状態名 を設定します. */
    public void setGpac_status_name(String gpac_status_name) { this.gpac_status_name = gpac_status_name; }
    /** 社員番号 を取得します. */
    public String getEmployee_no() { return employee_no; }
    /** 社員番号 を取得します. */
    public void setEmployee_no(String employee_no) { this.employee_no = employee_no; }

    /** デフォルトフラグ を取得します. */
    public String getIs_default() { return is_default; }
    /** デフォルトフラグ を設定します. */
    public void setIs_default(String is_default) { this.is_default = is_default; }
    /** 既存フラグ を取得します. */
    public String getIs_existing() { return is_existing; }
    /** 既存フラグ を設定します. */
    public void setIs_existing(String is_existing) { this.is_existing = is_existing; }
    /** 削除フラグ を取得します. */
    public String getIs_delete() { return is_delete; }
    /** 削除フラグ を設定します. */
    public void setIs_delete(String is_delete) { this.is_delete = is_delete; }
    /** オーナーフラグ を取得します. */
    public String getIs_owner() { return is_owner; }
    /** オーナーフラグ を設定します. */
    public void setIs_owner(String is_owner) { this.is_owner = is_owner; }

}
