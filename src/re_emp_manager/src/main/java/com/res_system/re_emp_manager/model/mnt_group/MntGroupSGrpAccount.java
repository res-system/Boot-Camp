package com.res_system.re_emp_manager.model.mnt_group;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.re_emp_manager.commons.model.entities.SGrpAccount;

/**
 * グループ情報メンテナンス処理用 グループアカウントサブマスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name=SqlMaker.SQL_NAME_UPDATE, sql = ""
            + "--UPDATE"
            + " `default_group_id` = :default_group_id"
            + ",`updated_id` = :updated_id"
            + ",`updated` = CURRENT_TIMESTAMP"
            + " --WHERE"
            )
    ,@Sql(name="is_default", sql = ""
            + "SELECT COUNT(*) AS `count` --FROM"
            + " WHERE `GRPAC`.`root_group_id` = ? AND `GRPAC`.`user_id` = ? AND `GRPAC`.`default_group_id` = ?"
            )
})
public class MntGroupSGrpAccount extends SGrpAccount {}
