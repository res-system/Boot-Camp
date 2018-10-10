package com.res_system.re_emp_manager.commons.model.auth;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.commons.mvc.model.form.Param;
import com.res_system.re_emp_manager.commons.model.entities.RAccCooperation;

/**
 * 認証処理用 アカウント連携関連マスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="find_list", sql = ""
            + "SELECT"
            + " `ACCOP`.`account_id` AS `account_id`"
            + ",`RGRP`.`id` AS `root_group_id`"
            + ",`RGRP`.`name` AS `root_group_name`"
            + ",`RGRP`.`code` AS `root_group_code`"
            + ",`GRP`.`id` AS `group_id`"
            + ",`GRP`.`name` AS `group_name`"
            + ",`AUT`.`id` AS `authority_id`"
            + ",`AUT`.`name` AS `authority_name`"
            + ",`USR`.`id` AS `user_id`"
            + ",`USR`.`name` AS `user_name`"
            + ",`ACCOP`.`save_flg` AS `save_flg`"
            + ",`SaveFlg`.`name` AS `save_flg_name`"
            + ",DATE_FORMAT(`ACCOP`.`updated`, '%Y/%m/%d') AS `updated`"
            + ",(CASE WHEN `USR`.`id` = ? THEN '1' ELSE '0' END) AS `is_current`"
            // -- キーワード検索項目 start. --//
            + ",CONCAT(IFNULL(`RGRP`.`name`,'')"
            +    ",' ',IFNULL(`GRP`.`name`,'')"
            +    ",' ',IFNULL(`AUT`.`name`,'')"
            +    ",' ',IFNULL(`USR`.`name`,'')"
            +    ",' ',IFNULL(`SaveFlg`.`name`,'')"
            +    ",' ',IFNULL(`ACCOP`.`updated`,'')"
            +    ") AS `keyword`"
            // -- キーワード検索項目 end  .--//
            + " --FROM"
            +   " INNER JOIN `m_group` `RGRP`"
            +       " ON `RGRP`.`id` = `ACCOP`.`root_group_id` AND `RGRP`.`grp_status` IN('0', '1')"
            +   " INNER JOIN `m_user` `USR`"
            +       " ON `USR`.`id` = `ACCOP`.`user_id`"
            +   " INNER JOIN `s_grp_account` `GRPAC`"
            +       " ON `GRPAC`.`root_group_id` = `ACCOP`.`root_group_id` AND `GRPAC`.`user_id` = `ACCOP`.`user_id`"
            +       " AND `GRPAC`.`gpac_status` IN('0', '1')"
            +   " INNER JOIN `r_grp_member` `GRPMB`"
            +       " ON `GRPMB`.`group_id` = `GRPAC`.`default_group_id` AND `GRPMB`.`user_id` = `GRPAC`.`user_id`"
            +   " INNER JOIN `m_group` `GRP`"
            +       " ON `GRP`.`id` = `GRPMB`.`group_id` AND `GRP`.`grp_status` IN('0', '1')"
            +   " INNER JOIN `m_authority` `AUT`"
            +       " ON `AUT`.`id` = `GRPMB`.`authority_id`"
            +   " LEFT OUTER JOIN `g_kind` `SaveFlg` "
            +       " ON `SaveFlg`.`kbn` = 'SaveFlg' AND `SaveFlg`.`seq` <> 0 AND `SaveFlg`.`code` = `ACCOP`.`save_flg`"
            + " WHERE `ACCOP`.`account_id` = ?"
            )
    ,@Sql(name="find_list_order", sql = "ORDER BY `is_current` DESC, `updated` DESC ")
    ,@Sql(name="find_list_order_1", sql = "ORDER BY `authority_id` ASC ")
    ,@Sql(name="find_list_order_2", sql = "ORDER BY `authority_id` DESC ")
    ,@Sql(name="find_list_order_3", sql = "ORDER BY `updated` ASC ")
    ,@Sql(name="find_list_order_4", sql = "ORDER BY `updated` DESC ")
    ,@Sql(name="find_list_order_5", sql = "ORDER BY `root_group_name` ASC, `group_name` ASC ")
    ,@Sql(name="find_list_order_6", sql = "ORDER BY `root_group_name` DESC, `group_name` DESC ")
    ,@Sql(name="find_list_order_7", sql = "ORDER BY `user_name` ASC ")
    ,@Sql(name="find_list_order_8", sql = "ORDER BY `user_name` DESC ")
    ,@Sql(name="find_list_order_9", sql = "ORDER BY `save_flg` ASC ")
    ,@Sql(name="find_list_order_10", sql = "ORDER BY `save_flg` DESC ")
    ,@Sql(name="find_target", sql = ""
            + "SELECT"
            + " `ACCOP`.`account_id` AS `account_id`"
            + ",`RGRP`.`id` AS `root_group_id`"
            + ",`RGRP`.`name` AS `root_group_name`"
            + ",`RGRP`.`code` AS `root_group_code`"
            + ",`GRP`.`id` AS `group_id`"
            + ",`GRP`.`name` AS `group_name`"
            + ",`AUT`.`id` AS `authority_id`"
            + ",`AUT`.`name` AS `authority_name`"
            + ",`USR`.`id` AS `user_id`"
            + ",`USR`.`name` AS `user_name`"
            + ",`ACCOP`.`save_flg` AS `save_flg`"
            + ",`SaveFlg`.`name` AS `save_flg_name`"
            + ",DATE_FORMAT(`ACCOP`.`updated`, '%Y/%m/%d') AS `updated`"
            + " --FROM"
            +   " INNER JOIN `m_group` `RGRP`"
            +       " ON `RGRP`.`id` = `ACCOP`.`root_group_id` AND `RGRP`.`grp_status` IN('0', '1')"
            +   " INNER JOIN `m_user` `USR`"
            +       " ON `USR`.`id` = `ACCOP`.`user_id`"
            +   " INNER JOIN `s_grp_account` `GRPAC`"
            +       " ON `GRPAC`.`root_group_id` = `ACCOP`.`root_group_id` AND `GRPAC`.`user_id` = `ACCOP`.`user_id`"
            +       " AND `GRPAC`.`gpac_status` IN('0', '1')"
            +   " INNER JOIN `r_grp_member` `GRPMB`"
            +       " ON `GRPMB`.`group_id` = `GRPAC`.`default_group_id` AND `GRPMB`.`user_id` = `GRPAC`.`user_id`"
            +   " INNER JOIN `m_group` `GRP`"
            +       " ON `GRP`.`id` = `GRPMB`.`group_id` AND `GRP`.`grp_status` IN('0', '1')"
            +   " INNER JOIN `m_authority` `AUT`"
            +       " ON `AUT`.`id` = `GRPMB`.`authority_id`"
            +   " LEFT OUTER JOIN `g_kind` `SaveFlg` "
            +       " ON `SaveFlg`.`kbn` = 'SaveFlg' AND `SaveFlg`.`seq` <> 0 AND `SaveFlg`.`code` = `ACCOP`.`save_flg`"
            + " WHERE `ACCOP`.`account_id` = ? AND `ACCOP`.`root_group_id` = ? AND `ACCOP`.`user_id` = ?"
            )
    ,@Sql(name=SqlMaker.SQL_NAME_UPDATE, sql = ""
            + "--UPDATE"
            + " `save_flg` = :save_flg"
            + ",`updated_id` = :updated_id"
            + ",`updated` = CURRENT_TIMESTAMP"
            + " --WHERE"
            )
    ,@Sql(name="check_count", sql = ""
            + "SELECT COUNT(*) AS `count` --FROM"
            + " WHERE `ACCOP`.`account_id` = ?"
            )
    ,@Sql(name="check_duplicate", sql = ""
            + "SELECT COUNT(*) AS `count` --FROM"
            + " WHERE `ACCOP`.`account_id` = ?"
            +   " AND `ACCOP`.`root_group_id` = ?"
            +   " AND `ACCOP`.`user_id` = ?"
            )
    ,@Sql(name="check_owner", sql = ""
            + "SELECT COUNT(`GRPAC`.`account_id`) AS `count` --FROM"
            +   " INNER JOIN `s_grp_account` `GRPAC`"
            +       " ON `GRPAC`.`root_group_id` = `ACCOP`.`root_group_id`"
            +       " AND `GRPAC`.`user_id` = `ACCOP`.`user_id`"
            +       " AND `GRPAC`.`account_id` = `ACCOP`.`account_id`"
            + " --WHERE_WT"
            )
})
public class AuthRAccCooperation extends RAccCooperation {

    //---------------------------------------------- const [public].
    /** 並び順 [最小]. */
    public static final int SORT_MIN = 1;
    /** 並び順 [最大]. */
    public static final int SORT_MAX = 10;



    //---------------------------------------------- properies [private].
    /** ルートグループ名. */
    @Param
    private String root_group_name;
    /** ルートグループコード. */
    @Param
    private String root_group_code;
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
    /** ユーザー名. */
    @Param
    private String user_name;
    /** 保存フラグ名. */
    @Param
    private String save_flg_name;

    /** カレントフラグ. */
    @Param
    private String is_current;


    //-- setter / getter. --//
    /** ルートグループ名 を取得します. */
    public String getRoot_group_name() { return root_group_name; }
    /** ルートグループ名 を設定します. */
    public void setRoot_group_name(String root_group_name) { this.root_group_name = root_group_name; }
    /** ルートグループコード を取得します. */
    public String getRoot_group_code() { return root_group_code; }
    /** ルートグループコード を設定します. */
    public void setRoot_group_code(String root_group_code) { this.root_group_code = root_group_code; }
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
    /** ユーザー名 を取得します. */
    public String getUser_name() { return user_name; }
    /** ユーザー名 を設定します. */
    public void setUser_name(String user_name) { this.user_name = user_name; }
    /** 保存フラグ名 を取得します. */
    public String getSave_flg_name() { return save_flg_name; }
    /** 保存フラグ名 を設定します. */
    public void setSave_flg_name(String save_flg_name) { this.save_flg_name = save_flg_name; }

    /** カレントフラグ を取得します. */
    public String getIs_current() { return is_current; }
    /** カレントフラグ を設定します. */
    public void setIs_current(String is_current) { this.is_current = is_current; }

}