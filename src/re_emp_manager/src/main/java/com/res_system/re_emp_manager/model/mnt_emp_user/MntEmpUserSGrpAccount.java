package com.res_system.re_emp_manager.model.mnt_emp_user;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.re_emp_manager.commons.model.entities.SGrpAccount;

/**
 * 社員ユーザー情報メンテナンス処理用 グループアカウントサブマスタ Entityクラス.
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
    ,@Sql(name="check_login_id_ins", sql = ""
            + "SELECT COUNT(*) AS `count` FROM `s_grp_account` WHERE `root_group_id` = ? AND `login_id` = ?"
            )
    ,@Sql(name="check_login_id_upd", sql = ""
            + "SELECT COUNT(*) AS `count` FROM `s_grp_account` WHERE `root_group_id` = ? AND `login_id` = ? AND `user_id` <> ?"
            )
})
public class MntEmpUserSGrpAccount extends SGrpAccount {}
