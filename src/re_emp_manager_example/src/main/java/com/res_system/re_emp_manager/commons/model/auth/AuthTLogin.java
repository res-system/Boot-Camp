package com.res_system.re_emp_manager.commons.model.auth;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.commons.mvc.model.form.Param;
import com.res_system.re_emp_manager.commons.model.entities.TLogin;

/**
 * 認証処理用 ログイン認証トラン Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="find_by_token", sql = ""
            + "SELECT"
            + " `LGN`.`token` AS `token`"
            + ",`LGN`.`login_dt` AS `login_dt`"
            + ",`LGN`.`expiration_time` AS `expiration_time`"
            + ",`LGN`.`save_flg` AS `save_flg`"
            + ",`LGN`.`client_info` AS `client_info`"
            + ",`LGN`.`user_kbn` AS `user_kbn`"
            + ",`UsrKbn`.`name` AS `user_kbn_name`"
            + ",`USR`.`id` AS `user_id`"
            + ",`USR`.`name`  AS `user_name`"
            + ",`RGRP`.`id` AS `root_group_id`"
            + ",`RGRP`.`name` AS `root_group_name`"
            + ",`RGRP`.`code` AS `root_group_code`"
            + ",`GRP`.`id` AS `group_id`"
            + ",`GRP`.`name` AS `group_name`"
            + ",IFNULL(`AUT`.`id`, `USR`.`default_authority_id`) AS `authority_id`"
            + ",IFNULL(`AUT`.`name`, `DAUT`.`name`) AS `authority_name`"
            + ",`ACC`.`id` AS `account_id`"
            + ",IFNULL(`ACC`.`user_id`, `USR`.`id`) AS `account_user_id`"
            + ",IFNULL(`AUSR`.`name`, `USR`.`name`) AS `account_user_name`"
            + " FROM `t_login` `LGN`"
            +   " INNER JOIN `m_user` `USR`"
            +       " ON `USR`.`id` = `LGN`.`user_id`"
            +   " LEFT OUTER JOIN `g_kind` `UsrKbn` "
            +       " ON `UsrKbn`.`kbn` = 'UsrKbn' AND `UsrKbn`.`seq` <> 0 AND `UsrKbn`.`code` = `USR`.`user_kbn`"
            +   " LEFT OUTER JOIN `m_group` `GRP`"
            +       " ON `GRP`.`id` = `LGN`.`group_id` AND `GRP`.`grp_status` IN('0', '1')"
            +   " LEFT OUTER JOIN `r_grp_member` `GRPMB`"
            +       " ON `GRPMB`.`group_id` = `GRP`.`id` AND `GRPMB`.`user_id` = `USR`.`id`"
            +   " LEFT OUTER JOIN `m_group` `RGRP`"
            +       " ON `RGRP`.`id` = `GRP`.`root_group_id` AND `RGRP`.`grp_status` IN('0', '1')"
            +   " LEFT OUTER JOIN `s_grp_account` `GRPAC`"
            +       " ON `GRPAC`.`root_group_id` = `RGRP`.`id` AND `GRPAC`.`user_id` = `USR`.`id`"
            +       " AND `GRPAC`.`gpac_status` IN('0', '1')"
            +   " LEFT OUTER JOIN `m_authority` `AUT`"
            +       " ON `AUT`.`id` = `GRPMB`.`authority_id`"
            +   " LEFT OUTER JOIN `m_authority` `DAUT`"
            +       " ON `DAUT`.`id` = `USR`.`default_authority_id`"
            +   " LEFT OUTER JOIN `m_account` `ACC`"
            +       " ON `ACC`.`id` = `LGN`.`account_id` AND `ACC`.`acc_status` IN('0', '1')"
            +   " LEFT OUTER JOIN `m_user` `AUSR`"
            +       " ON `AUSR`.`id` = `ACC`.`user_id`"
            + " WHERE `LGN`.`token` = ?"
            +   " AND ( (`LGN`.`group_id` IS NULL AND `GRPAC`.`user_id` IS NULL)"
            +      " OR (`LGN`.`group_id` IS NOT NULL AND `GRPAC`.`user_id` IS NOT NULL AND `RGRP`.`id` IS NOT NULL) )"
            )
    ,@Sql(name="is_exixt", sql = ""
            + "SELECT COUNT(*) AS `count` --FROM"
            + " WHERE `LGN`.`expiration_time` >= CURRENT_TIMESTAMP"
            +   " AND `LGN`.`token` = ?"
            )
    ,@Sql(name="is_exixt_by_save", sql = ""
            + "SELECT COUNT(*) AS `count` --FROM"
            + " WHERE `LGN`.`expiration_time` >= CURRENT_TIMESTAMP"
            + "   AND `LGN`.`token` = ?"
            + "   AND `LGN`.`save_flg` = 1"
            )
    ,@Sql(name=SqlMaker.SQL_NAME_UPDATE, sql = ""
            + "--UPDATE"
            + " `login_dt` = CURRENT_TIMESTAMP"
            + ",`user_id` = :user_id"
            + ",`group_id` = :group_id"
            + ",`authority_id` = :authority_id"
            + " --WHERE"
            )
    ,@Sql(name="delete_by_user_id", sql = ""
            + "--DELETE WHERE `user_id` = ?"
            )
    ,@Sql(name="delete_by_another_token", sql = ""
            + "--DELETE WHERE `user_id` = ? AND `token` <> ?"
            )
})
public class AuthTLogin extends TLogin {

    //---------------------------------------------- properies [private].
    /** ユーザー名. */
    @Param
    private String user_name;
    /** グループ名. */
    @Param
    private String group_name;
    /** 権限名. */
    @Param
    private String authority_name;
    /** ユーザー区分名. */
    @Param
    private String user_kbn_name;
    /** ルートグループID. */
    @Param
    private String root_group_id;
    /** ルートグループ名. */
    @Param
    private String root_group_name;
    /** ルートグループコード. */
    @Param
    private String root_group_code;
    /** アカウントID. */
    @Param
    private String account_id;
    /** アカウントユーザーID. */
    @Param
    private String account_user_id;
    /** アカウントユーザー名. */
    @Param
    private String account_user_name;

    //-- setter / getter. --//
    /** ユーザー名 を取得します. */
    public String getUser_name() { return user_name; }
    /** ユーザー名 を設定します. */
    public void setUser_name(String user_name) { this.user_name = user_name; }
    /** グループ名 を取得します. */
    public String getGroup_name() { return group_name; }
    /** グループ名 を設定します. */
    public void setGroup_name(String group_name) { this.group_name = group_name; }
    /** 権限名 を取得します. */
    public String getAuthority_name() { return authority_name; }
    /** 権限名 を設定します. */
    public void setAuthority_name(String authority_name) { this.authority_name = authority_name; }
    /** ユーザー区分名 を取得します. */
    public String getUser_kbn_name() { return user_kbn_name; }
    /** ユーザー区分名 を設定します. */
    public void setUser_kbn_name(String user_kbn_name) { this.user_kbn_name = user_kbn_name; }
    /** ルートグループID を取得します. */
    public String getRoot_group_id() { return root_group_id; }
    /** ルートグループID を設定します. */
    public void setRoot_group_id(String root_group_id) { this.root_group_id = root_group_id; }
    /** ルートグループ名 を取得します. */
    public String getRoot_group_name() { return root_group_name; }
    /** ルートグループ名 を設定します. */
    public void setRoot_group_name(String root_group_name) { this.root_group_name = root_group_name; }
    /** ルートグループコード を取得します. */
    public String getRoot_group_code() { return root_group_code; }
    /** ルートグループコード を設定します. */
    public void setRoot_group_code(String root_group_code) { this.root_group_code = root_group_code; }
    /** アカウントID を取得します. */
    public String getAccount_id() { return account_id; }
    /** アカウントID を設定します. */
    public void setAccount_id(String account_id) { this.account_id = account_id; }
    /** アカウントユーザーID を取得します. */
    public String getAccount_user_id() { return account_user_id; }
    /** アカウントユーザーID を設定します. */
    public void setAccount_user_id(String account_user_id) { this.account_user_id = account_user_id; }
    /** アカウントユーザー名 を取得します. */
    public String getAccount_user_name() { return account_user_name; }
    /** アカウントユーザー名 を設定します. */
    public void setAccount_user_name(String account_user_name) { this.account_user_name = account_user_name; }

}
