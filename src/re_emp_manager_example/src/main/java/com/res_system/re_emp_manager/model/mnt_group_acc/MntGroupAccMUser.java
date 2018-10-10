package com.res_system.re_emp_manager.model.mnt_group_acc;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.re_emp_manager.commons.model.entities.MUser;

/**
 * グループアカウント情報メンテナンス処理用 ユーザーマスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name=SqlMaker.SQL_NAME_UPDATE, sql = ""
            + "--UPDATE"
            + " `email_addr` = :email_addr "
            + ",`updated_id` = :updated_id "
            + ",`updated` = CURRENT_TIMESTAMP "
            + " --WHERE"
            )
})
public class MntGroupAccMUser extends MUser {}
