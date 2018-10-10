package com.res_system.re_emp_manager.model.mnt_group_acc;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.re_emp_manager.commons.model.entities.SGrpAccount;

/**
 * グループアカウント情報メンテナンス処理用 グループアカウントサブマスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name=SqlMaker.SQL_NAME_UPDATE, sql = ""
            + "--UPDATE"
            + " `gpac_status` = :gpac_status "
            + ",`memo` = :memo "
            + ",`updated_id` = :updated_id "
            + ",`updated` = CURRENT_TIMESTAMP "
            + " --WHERE"
            )
    ,@Sql(name=SqlMaker.SQL_NAME_UPDATE + "_OWNER", sql = ""
            + "--UPDATE"
            + " `memo` = :memo "
            + ",`updated_id` = :updated_id "
            + ",`updated` = CURRENT_TIMESTAMP "
            + " --WHERE"
            )
    ,@Sql(name="update_login_id", sql = ""
            + "--UPDATE"
            + " `login_id` = :login_id "
            + ",`updated_id` = :updated_id "
            + ",`updated` = CURRENT_TIMESTAMP "
            + " --WHERE"
            )
    ,@Sql(name="check_login_id", sql = ""
            + "SELECT COUNT(*) AS `count` FROM `s_grp_account` WHERE `root_group_id` = ? AND `login_id` = ? AND `user_id` <> ?"
            )
})
public class MntGroupAccSGrpAccount extends SGrpAccount {}
