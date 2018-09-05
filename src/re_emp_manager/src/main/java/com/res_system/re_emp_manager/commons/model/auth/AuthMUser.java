package com.res_system.re_emp_manager.commons.model.auth;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.mvc.model.form.Param;
import com.res_system.re_emp_manager.commons.model.entities.MUser;

/**
 * 認証処理用 ユーザーマスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="find_by_account", sql = ""
            + "SELECT"
            + " `USRKY`.`key` AS `key`"
            + ",`USR`.`id` AS `id`"
            + ",`USR`.`user_kbn` AS `user_kbn`"
            + ",`USR`.`default_authority_id` AS `authority_id`"
            + ",NULL AS `group_id`"
            + ",`ACC`.`id` AS `account_id`"
            + " FROM `m_account` `ACC`"
            +   " INNER JOIN `m_user` `USR`"
            +       " ON `USR`.`id` = `ACC`.`user_id` AND `USR`.`user_kbn` = ?"
            +   " INNER JOIN `s_user_key` `USRKY`"
            +       " ON `USRKY`.`user_id` = `USR`.`id` AND `USRKY`.`seq` = 1"
            + " WHERE `ACC`.`acc_status` IN('0', '1')"
            +   " AND `ACC`.`login_id` = ?"
            )
    ,@Sql(name="find_by_group_has_account", sql = ""
            + "SELECT"
            + " `USRKY`.`key` AS `key`"
            + ",`GUSR`.`id` AS `id`"
            + ",`USR`.`user_kbn` AS `user_kbn`"
            + ",`GRPMB`.`authority_id` AS `authority_id`"
            + ",`GRP`.`id` AS `group_id`"
            + ",`ACC`.`id` AS `account_id`"
            + " FROM `m_account` `ACC`"
            +   " INNER JOIN `m_user` `USR`"
            +       " ON `USR`.`id` = `ACC`.`user_id` AND `USR`.`user_kbn` = '2'"
            +   " INNER JOIN `s_user_key` `USRKY`"
            +       " ON `USRKY`.`user_id` = `USR`.`id` AND `USRKY`.`seq` = 1"
            +   " INNER JOIN `m_group` `GRP`"
            +   " INNER JOIN `s_grp_account` `GRPAC`"
            +       " ON `GRPAC`.`root_group_id` = `GRP`.`id` AND `GRPAC`.`account_id` = `ACC`.`id`"
            +   " INNER JOIN `m_user` `GUSR`"
            +       " ON `GUSR`.`id` = `GRPAC`.`user_id` AND `GUSR`.`user_kbn` = '3'"
            +   " INNER JOIN `r_grp_member` `GRPMB`"
            +       " ON `GRPMB`.`group_id` = `GRP`.`id` AND `GRPMB`.`user_id` = `GUSR`.`id`"
            + " WHERE `GRP`.`grp_status` IN('0', '1')"
            +   " AND `ACC`.`acc_status` IN('0', '1')"
            +   " AND `GRPAC`.`gpac_status` IN('0', '1')"
            +   " AND `ACC`.`login_id` = ?"
            +   " AND `GRP`.`code` = ?"
            )
    ,@Sql(name="find_by_group", sql = ""
            + "SELECT"
            + " `USRKY`.`key` AS `key`"
            + ",`USR`.`id` AS `id`"
            + ",`USR`.`user_kbn` AS `user_kbn`"
            + ",`GRPMB`.`authority_id` AS `authority_id`"
            + ",`GRPMB`.`group_id` AS `group_id`"
            + ",NULL AS `account_id`"
            + " FROM `m_group` `GRP`"
            +   " INNER JOIN `s_grp_account` `GRPAC`"
            +       " ON `GRPAC`.`root_group_id` = `GRP`.`id`"
            +   " INNER JOIN `m_user` `USR`"
            +       " ON `USR`.`id` = `GRPAC`.`user_id` AND `USR`.`user_kbn` = '3'"
            +   " INNER JOIN `s_user_key` `USRKY`"
            +       " ON `USRKY`.`user_id` = `USR`.`id` AND `USRKY`.`seq` = 1"
            +   " INNER JOIN `r_grp_member` `GRPMB`"
            +       " ON `GRPMB`.`group_id` = `GRPAC`.`default_group_id` AND `GRPMB`.`user_id` = `USR`.`id`"
            + " WHERE `GRP`.`grp_status` IN('0', '1')"
            +   " AND `GRPAC`.`gpac_status` IN('0', '1')"
            +   " AND `GRPAC`.`login_id` = ?"
            +   " AND `GRP`.`code` = ?"
            )
    ,@Sql(name="update_key", sql = ""
            + "UPDATE `s_user_key` SET"
            + " `key` = ?"
            + ",`updated_id` = ?"
            + ",`updated` = CURRENT_TIMESTAMP"
            + " WHERE `seq` = 1"
            + "  AND `user_id` = ?"
            )
    ,@Sql(name="check_key", sql = ""
            + "SELECT"
            + " `USRKY`.`key` AS `key`"
            + " FROM `m_user` `USR`"
            +   " INNER JOIN `s_user_key` `USRKY`"
            +       " ON `USRKY`.`user_id` = `USR`.`id` AND `USRKY`.`seq` = 1"
            + " --WHERE_WT"
            )
})
public class AuthMUser extends MUser {

    //---------------------------------------------- properies [private].
    /** アカウントID. */
    @Param
    private String account_id;
    /** グループID. */
    @Param
    private String group_id;
    /** 権限ID. */
    @Param
    private String authority_id;
    /** キー. */
    @Param
    private String key;

    //-- setter / getter. --//
    /** アカウントID を取得します. */
    public String getAccount_id() { return account_id; }
    /** アカウントID を設定します. */
    public void setAccount_id(String account_id) { this.account_id = account_id; }
    /** グループID を取得します. */
    public String getGroup_id() { return group_id; }
    /** グループID を設定します. */
    public void setGroup_id(String group_id) { this.group_id = group_id; }
    /** 権限ID を取得します. */
    public String getAuthority_id() { return authority_id; }
    /** 権限ID を設定します. */
    public void setAuthority_id(String authority_id) { this.authority_id = authority_id; }
    /** キー を取得します. */
    public String getKey() { return key; }
    /** キー を設定します. */
    public void setKey(String key) { this.key = key; }

}
